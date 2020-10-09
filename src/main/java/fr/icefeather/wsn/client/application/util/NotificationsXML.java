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

import fr.icefeather.wsn.client.application.Notifications;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class NotificationsXML {

    public static void toXmlFile(Notifications notifications, String filePath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Notifications.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        File file = new File(filePath);
        marshaller.marshal(notifications, file);
    }

    public static Notifications fromXmlFile(String filepath) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Notifications.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        File file = new File(filepath);
        Notifications notifications = (Notifications) unmarshaller.unmarshal(file);
        return notifications;
    }

}
