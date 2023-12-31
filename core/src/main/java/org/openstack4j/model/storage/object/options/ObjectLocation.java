package org.openstack4j.model.storage.object.options;

import java.util.Objects;

/**
 * Holds location information for an Object (Container and Object name including path)
 *
 * @author Jeremy Unruh
 */
public final class ObjectLocation {

    private String containerName;
    private String objectName;


    private ObjectLocation(String containerName, String objectName) {
        super();
        Objects.requireNonNull(containerName, "ContainerName cannot be null");
        Objects.requireNonNull(objectName, "ObjectName cannot be null");

        this.containerName = containerName;
        this.objectName = objectName;
    }

    public static ObjectLocation create(String containerName, String objectName) {
        return new ObjectLocation(containerName, objectName);
    }

    public String getContainerName() {
        return containerName;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getURI() {
        return String.format("/%s/%s", containerName, objectName);
    }
}
