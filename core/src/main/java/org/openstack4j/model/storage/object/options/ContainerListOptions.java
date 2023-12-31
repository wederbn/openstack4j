package org.openstack4j.model.storage.object.options;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * List options used for Container and Object based queries
 *
 * @author Jeremy Unruh
 */
public final class ContainerListOptions {

    private Map<String, String> queryParams = new HashMap<>();

    private ContainerListOptions() {
    }

    public static ContainerListOptions create() {
        return new ContainerListOptions();
    }

    /**
     * Prefix value. Named items in the response begin with this value.
     *
     * @param prefix the prefix (starts with)
     * @return ContainerListOptions
     */
    public ContainerListOptions startsWith(String prefix) {
        return add("prefix", Objects.requireNonNull(prefix));
    }

    /**
     * For an integer value n, limits the number of results to n.
     */
    public ContainerListOptions limit(int limit) {
        if (limit < 0) throw new IllegalStateException("limit must be >= 0");
        if (limit > 10000) throw new IllegalStateException("limit must be <= 10000");
        add("limit", Integer.toString(limit));
        return this;
    }

    /**
     * For a string value x, returns container names that are greater in value than the specified marker.
     *
     * @param marker the marker value
     * @return ContainerListOptions
     */
    public ContainerListOptions marker(String marker) {
        add("marker", Objects.requireNonNull(marker, "marker"));
        return this;
    }

    /**
     * For a string value x, returns container names that are less in value than the specified marker.
     *
     * @param endMarker the endMarker value
     * @return ContainerListOptions
     */
    public ContainerListOptions endMarker(String endMarker) {
        add("end_marker", Objects.requireNonNull(endMarker, "endMarker"));
        return this;
    }

    /**
     * Delimiter value, which returns the object names that are nested in the container.
     *
     * @param delimiter the delimiter value
     * @return ContainerListOptions
     */
    public ContainerListOptions delimiter(char delimiter) {
        return add("delimiter", Character.toString(delimiter));
    }

    /**
     * For a string value, returns the object names that are nested in the pseudo path.
     * Equivalent to setting delimiter to / and prefix to the path with a / at the end.
     *
     * @param path the path value
     * @return ContainerListOptions
     */
    public ContainerListOptions path(String path) {
        return add("path", Objects.requireNonNull(path));
    }

    private ContainerListOptions add(String param, String value) {
        this.queryParams.put(param, value);
        return this;
    }

    public Map<String, String> getOptions() {
        return queryParams;
    }
}
