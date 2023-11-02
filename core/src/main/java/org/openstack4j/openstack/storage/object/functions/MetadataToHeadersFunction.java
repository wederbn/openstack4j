package org.openstack4j.openstack.storage.object.functions;

import java.util.HashMap;
import java.util.Map;

import java.util.function.Function;

/**
 * Transforms a MetaHeaderRequestWrapper which applies headers based on a prefix to the outbound
 * HttpRequest
 *
 * @author Jeremy Unruh
 */
public class MetadataToHeadersFunction implements Function<Map<String, String>, Map<String, String>> {

    private String prefix;

    private MetadataToHeadersFunction(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Creates a new Metadata to Headers function
     *
     * @param prefix the prefix used for headers
     * @return MetadataToHeadersFunction
     */
    public static <R> MetadataToHeadersFunction create(String prefix) {
        return new MetadataToHeadersFunction(prefix);
    }

    /**
     * Transforms metadata raw values into header form values
     *
     * @param metadata the map of metadata
     * @return map of metadata in header format
     */
    @Override
    public Map<String, String> apply(Map<String, String> metadata) {

        Map<String, String> headers = new HashMap<>();

        for (String key : metadata.keySet()) {
            String keyLower = key.toLowerCase();
            String value = metadata.get(key);
            if (keyLower.startsWith(prefix.toLowerCase()))
                headers.put(keyLower, value);
            else
                headers.put(String.format("%s%s", prefix, keyLower), value);
        }
        return headers;
    }

}
