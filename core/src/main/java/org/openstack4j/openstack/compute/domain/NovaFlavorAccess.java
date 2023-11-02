package org.openstack4j.openstack.compute.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.util.ToStringHelper;
import org.openstack4j.model.compute.FlavorAccess;
import org.openstack4j.openstack.common.ListResult;

/**
 * An OpenStack Flavor Access for tenants implement
 *
 * @author Moodpo
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NovaFlavorAccess implements FlavorAccess {

    private static final long serialVersionUID = 1L;

    @JsonProperty("flavor_id")
    private String flavorId;

    @JsonProperty("tenant_id")
    private String tenantId;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getFlavorId() {
        return flavorId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTenantId() {
        return tenantId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return new ToStringHelper(this)
                .add("flavorId", flavorId).add("tenantId", tenantId)
                .addValue("\n").toString();
    }

    @JsonRootName("addTenantAccess")
    public static class AddTenantAccess extends NovaFlavorAccess {

        private static final long serialVersionUID = 1L;

        @JsonProperty("tenant")
        private String tenantId;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

    }

    @JsonRootName("removeTenantAccess")
    public static class RemoveTenantAccess extends NovaFlavorAccess {

        private static final long serialVersionUID = 1L;

        @JsonProperty("tenant")
        private String tenantId;

        public String getTenantId() {
            return tenantId;
        }

        public void setTenantId(String tenantId) {
            this.tenantId = tenantId;
        }

    }

    public static class FlavorAccesses extends ListResult<NovaFlavorAccess> {

        private static final long serialVersionUID = 1L;

        @JsonProperty("flavor_access")
        List<NovaFlavorAccess> flavorAccesses;

        @Override
        protected List<NovaFlavorAccess> value() {
            return flavorAccesses;
        }

    }

}
