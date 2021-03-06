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

package fr.icefeather.wsn.client.application.util;

import fr.icefeather.wsn.client.application.Notification;
import fr.icefeather.wsn.client.application.Notifications;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NotificationsCSV {

    public static void toCsvFile(Notifications notifications, String filePath) throws IOException {
        if (notifications.notifications.size() > 0) {
            BufferedWriter csvWriter = Files.newBufferedWriter(Paths.get(filePath), Charset.defaultCharset());
            CSVPrinter csvPrinter = new CSVPrinter(csvWriter, CSVFormat.DEFAULT.withHeader(createHeaders(notifications.notifications.get(0)).toArray(new String[0])));
            for (Notification notification : notifications.notifications) {
                csvPrinter.printRecord(createLine(notification));
            }
            csvPrinter.flush();
            csvWriter.close();
        }
    }

    private static List<String> createHeaders(Notification notification){
        List<String> csvHeaders = new ArrayList<>();
        csvHeaders.add("Date");
        if (notification.headers != null) {
            if (notification.headers.size() > 0) {
                Iterator headersIter = notification.getHeaders().entrySet().iterator();
                while (headersIter.hasNext()) {
                    Map.Entry entry = (Map.Entry) headersIter.next();
                    String headerKey = (String) entry.getKey();
                    csvHeaders.add("header."+headerKey);
                }
            }
        }
        csvHeaders.add("Message");
        return csvHeaders;
    }

    private static List<String> createLine(Notification notification){
        List<String> csvLine = new ArrayList<>();
        csvLine.add(notification.getDate());
        if (notification.headers != null) {
            if (notification.headers.size() > 0) {
                Iterator headersIter = notification.getHeaders().entrySet().iterator();
                while (headersIter.hasNext()) {
                    Map.Entry entry = (Map.Entry) headersIter.next();
                    csvLine.add(entry.getValue().toString());
                }
            }
        }
        csvLine.add(notification.message);
        return csvLine;
    }

}
