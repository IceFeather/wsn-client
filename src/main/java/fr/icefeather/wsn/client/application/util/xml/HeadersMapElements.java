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

package fr.icefeather.wsn.client.application.util.xml;

import javax.xml.bind.annotation.XmlElement;

public class HeadersMapElements {
    @XmlElement public String key;
    @XmlElement public String value;

    private HeadersMapElements() {} //Required by JAXB

    public HeadersMapElements(String key, String value) {
        this.key   = key;
        this.value = value;
    }
}
