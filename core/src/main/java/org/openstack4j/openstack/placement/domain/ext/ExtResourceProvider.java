package org.openstack4j.openstack.placement.domain.ext;

import java.util.List;

import org.openstack4j.model.placement.ext.ResourceProvider;
import org.openstack4j.openstack.common.ListResult;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openstack4j.util.ToStringHelper;

/**
 * The resource provider instance
 *
 * @author Jyothi Saroja
 */
public class ExtResourceProvider implements ResourceProvider {

    private static final long serialVersionUID = 1L;

    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("name")
    private String name;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringHelper(this).add("id", uuid).add("name", name).toString();
    }

    public static class ResourceProviders extends ListResult<ExtResourceProvider> {

        private static final long serialVersionUID = 1L;

        @JsonProperty("resource_providers")
        List<ExtResourceProvider> resourceProviders;

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<ExtResourceProvider> value() {
            return resourceProviders;
        }
    }
}
