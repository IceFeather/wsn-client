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

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class HeadersMapAdapter extends XmlAdapter<HeadersMapElements[], Map<String, String>> {
    public HeadersMapElements[] marshal(Map<String, String> arg0) throws Exception {
        HeadersMapElements[] mapElements = new HeadersMapElements[arg0.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : arg0.entrySet())
            mapElements[i++] = new HeadersMapElements(entry.getKey(), entry.getValue());

        return mapElements;
    }

    public Map<String, String> unmarshal(HeadersMapElements[] arg0) throws Exception {
        Map<String, String> r = new HashMap<String, String>();
        for (HeadersMapElements mapelement : arg0)
            r.put(mapelement.key, mapelement.value);
        return r;
    }
}