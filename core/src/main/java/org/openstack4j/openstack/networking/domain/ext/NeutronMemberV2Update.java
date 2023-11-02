package org.openstack4j.openstack.networking.domain.ext;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.util.ToStringHelper;
import org.openstack4j.model.network.ext.MemberV2Update;
import org.openstack4j.model.network.ext.builder.MemberV2UpdateBuilder;

/**
 * Entity for updating lbaas v2 members
 *
 * @author emjburns
 */
@JsonRootName("member")
@JsonIgnoreProperties(ignoreUnknown = true)
public class NeutronMemberV2Update implements MemberV2Update {

    private static final long serialVersionUID = 1L;

    /**
     * 1~100
     */
    @JsonProperty("weight")
    private Integer weight;

    @JsonProperty("admin_state_up")
    private boolean adminStateUp = true;

    public static MemberV2UpdateBuilder builder() {
        return new MemberV2UpdateConcreteBuilder();
    }

    @Override
    public boolean isAdminStateUp() {
        return adminStateUp;
    }

    @Override
    public Integer getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return new ToStringHelper(this)
                .add("weight", weight)
                .add("adminStateUp", adminStateUp)
                .toString();
    }

    @Override
    public MemberV2UpdateBuilder toBuilder() {
        return new MemberV2UpdateConcreteBuilder(this);
    }

    public static class MemberV2UpdateConcreteBuilder implements MemberV2UpdateBuilder {
        private NeutronMemberV2Update m;

        public MemberV2UpdateConcreteBuilder() {
            this(new NeutronMemberV2Update());
        }

        public MemberV2UpdateConcreteBuilder(NeutronMemberV2Update m) {
            this.m = m;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MemberV2Update build() {
            return m;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MemberV2UpdateBuilder from(MemberV2Update in) {
            m = (NeutronMemberV2Update) in;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MemberV2UpdateBuilder adminStateUp(boolean adminStateUp) {
            m.adminStateUp = adminStateUp;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public MemberV2UpdateBuilder weight(Integer weight) {
            m.weight = weight;
            return this;
        }
    }
}
