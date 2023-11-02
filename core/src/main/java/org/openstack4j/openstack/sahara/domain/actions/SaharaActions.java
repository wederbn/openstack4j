package org.openstack4j.openstack.sahara.domain.actions;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.openstack4j.api.Builders;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.sahara.NodeGroup;

/**
 * Simple Actions Classes used for Sahara Action
 *
 * @author ekasit.kijsipongse@nectec.or.th
 */
//@SuppressWarnings("serial")
public final class SaharaActions {

    public static class ResizeNodeGroupAction implements ModelEntity {

        private static final long serialVersionUID = 1L;

        @JsonProperty("resize_node_groups")
        List<NodeGroup> nodeGroups;

        public ResizeNodeGroupAction(String groupName, int count) {
            NodeGroup nodeGroup = Builders.nodeGroup().name(groupName)
                    .count(count).build();
            nodeGroups = new ArrayList<>();
            nodeGroups.add(nodeGroup);
        }
    }

    public static class AddNodeGroupAction implements ModelEntity {

        private static final long serialVersionUID = 1L;

        @JsonProperty("add_node_groups")
        List<NodeGroup> nodeGroups;

        public AddNodeGroupAction(NodeGroup nodeGroup) {
            nodeGroups = new ArrayList<>();
            nodeGroups.add(nodeGroup);
        }
    }
}
