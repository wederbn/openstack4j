package org.openstack4j.openstack.storage.object.functions;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import java.util.function.Function;

/**
 * Transforms a Swift Metadata Map into a Map without the meta prefix as key values
 *
 * @author Jeremy Unruh
 */
public class MapWithoutMetaPrefixFunction implements Function<Map<String, String>, Map<String, String>> {

    public static final MapWithoutMetaPrefixFunction INSTANCE = new MapWithoutMetaPrefixFunction();

    @Override
    public Map<String, String> apply(Map<String, String> input) {
        Map<String, String> metadata = new HashMap<>();
        for (String key : input.keySet()) {
            if (key == null) {
                continue;
            }
            int idx = key.toLowerCase().indexOf("-meta-");
            if (idx > -1) {
                metadata.put(key.substring(idx + 6), input.get(key));
            }
            if (key.toLowerCase().indexOf("x-") > -1) {
                metadata.put(key, input.get(key));
            }
        }
        return Collections.unmodifiableMap(metadata);
    }


}
