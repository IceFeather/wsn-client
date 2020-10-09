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

package fr.icefeather.wsn.client.application.soap;

import javax.xml.bind.annotation.*;
import javax.xml.namespace.QName;
import java.util.List;
import java.util.Map;

@XmlRootElement(name="SubscriptionReference", namespace = "http://docs.oasis-open.org/wsn/b-2")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubscriptionReference {

    protected SubscriptionReference() {
    }

    // private but necessary properties for databinding
    @XmlElement(name="Address", namespace = "http://www.w3.org/2005/08/addressing")
    protected String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
