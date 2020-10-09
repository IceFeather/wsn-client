/*
 * Copyright 2020 MARLIER Benjamin, aliases : IceFeather, Lupogryph, mail : benjamin.marlier@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.icefeather.wsn.client.application;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.helpers.IOUtils;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Serveur {

    private int port;
    private NotificationListener notificationListener;
    private Server server;
    private Boolean actif = false;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NotificationListener getNotificationListener() {
        return notificationListener;
    }

    public void setNotificationListener(NotificationListener notificationListener) {
        this.notificationListener = notificationListener;
    }

    public Boolean isActif() {
        return actif;
    }

    public void start() throws Exception {
        server = new Server(port);
        server.setHandler(new NHandler(notificationListener));
        server.start();
        actif = true;
    }

    public void stop() throws Exception {
        server.stop();
        actif = false;
    }

    static class NHandler extends AbstractHandler {

        private NotificationListener notificationListener;

        public NHandler(NotificationListener notificationListener) {
            this.notificationListener = notificationListener;
        }

        @Override
        public void handle( String target,
                Request baseRequest,
                HttpServletRequest request,
                HttpServletResponse response ) throws IOException, ServletException
        {
            response.setContentType("text/html; charset=utf-8");
            response.setStatus(HttpServletResponse.SC_OK);

            PrintWriter out = response.getWriter();

            out.println("Hello");

            if ("POST".equalsIgnoreCase(request.getMethod())){
                String message = IOUtils.toString(request.getReader());
                notification(message, getHeaders(baseRequest));
            }

            baseRequest.setHandled(true);
        }

        public Map<String, String> getHeaders(Request baseRequest) {
            Map<String, String> headersList = new HashMap<>();
            Enumeration<String> headerNames = baseRequest.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = StringUtils.join(Collections.list(baseRequest.getHeaders(headerName)), ";");
                headersList.put(headerName, headerValue);
            }
            return headersList;
        }

        private void notification(String message, Map<String, String> headers) {
            notificationListener.nouvelleNotification(new Notification(message, headers));
        }

    }

}