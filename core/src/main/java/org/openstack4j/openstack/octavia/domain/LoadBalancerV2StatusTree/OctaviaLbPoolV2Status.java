package org.openstack4j.openstack.octavia.domain.LoadBalancerV2StatusTree;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.openstack4j.util.ToStringHelper;
import org.openstack4j.model.octavia.status.HealthMonitorV2Status;
import org.openstack4j.model.octavia.status.LbPoolV2Status;
import org.openstack4j.model.octavia.status.MemberV2Status;

/**
 * An object to hold status of lbaas v2 loadbalancer pool
 *
 * @author wei
 */
@JsonRootName("pools")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OctaviaLbPoolV2Status extends Status implements LbPoolV2Status {

    @JsonProperty("name")
    private String name;

    @JsonProperty("members")
    private List<MemberV2Status> memberStatuses;

    @JsonProperty("healthmonitor")
    private HealthMonitorV2Status healthMonitorStatus;

    @Override
    public List<MemberV2Status> getMemberStatuses() {
        return memberStatuses;
    }

    @Override
    public HealthMonitorV2Status getHeathMonitorStatus() {
        return healthMonitorStatus;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("memberStatuses", memberStatuses)
                .add("healthMonitorStatus", healthMonitorStatus)
                .add("operatingStatus", operatingStatus)
                .add("provisioningStatus", provisioningStatus)
                .toString();
    }
}
