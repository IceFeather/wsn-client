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

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "",
        propOrder = {"subscriptionReference", "currentTime", "terminationTime", "any"}
)
@XmlRootElement(
        name = "SubscribeResponse", namespace = "http://docs.oasis-open.org/wsn/b-2"
)
public class SubscribeResponse {
    @XmlElement(
            name = "SubscriptionReference",
            namespace = "http://docs.oasis-open.org/wsn/b-2",
            required = true
    )
    protected SubscriptionReference subscriptionReference;
    @XmlElement(
            name = "CurrentTime"
    )
    @XmlSchemaType(
            name = "dateTime"
    )
    protected XMLGregorianCalendar currentTime;
    @XmlElement(
            name = "TerminationTime",
            nillable = true
    )
    @XmlSchemaType(
            name = "dateTime"
    )
    protected XMLGregorianCalendar terminationTime;
    @XmlAnyElement(
            lax = true
    )
    protected List<Object> any;

    public SubscribeResponse() {
    }

    public SubscriptionReference getSubscriptionReference() {
        return this.subscriptionReference;
    }

    public void setSubscriptionReference(SubscriptionReference value) {
        this.subscriptionReference = value;
    }

    public XMLGregorianCalendar getCurrentTime() {
        return this.currentTime;
    }

    public void setCurrentTime(XMLGregorianCalendar value) {
        this.currentTime = value;
    }

    public XMLGregorianCalendar getTerminationTime() {
        return this.terminationTime;
    }

    public void setTerminationTime(XMLGregorianCalendar value) {
        this.terminationTime = value;
    }

    public List<Object> getAny() {
        if (this.any == null) {
            this.any = new ArrayList();
        }

        return this.any;
    }
}
