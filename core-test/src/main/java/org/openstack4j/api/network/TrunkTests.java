package org.openstack4j.api.network;

import java.util.ArrayList;
import java.util.List;

import org.openstack4j.api.AbstractTest;
import org.openstack4j.api.Builders;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.Port;
import org.openstack4j.model.network.Trunk;
import org.openstack4j.model.network.TrunkSubport;
import org.openstack4j.openstack.networking.domain.NeutronTrunkSubport;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Rewrote the entire API and so had to re-write tests
 *
 * @author Kashyap Jha
 */
@Test(suiteName = "Network")
public class TrunkTests extends AbstractTest {

    private static final String JSON_CREATE_TRUNK_RESPONSE = "/network/createTrunkResponse.json";
    private static final String JSON_ADD_SUBPORT_RESPONSE = "/network/addSubPortResponse.json";
    private static final String JSON_REMOVE_SUBPORT_RESPONSE = "/network/removeSubPortResponse.json";
    private static final String JSON_GET_TRUNK_RESPONSE = "/network/getTrunkResponse.json";
    private static final String JSON_UPDATE_TRUNK_RESPONSE = "/network/updateTrunkResponse.json";
    private static final String JSON_LIST_SUBPORTS_RESPONSE = "/network/listSubPortsResponse.json";
    private static final String JSON_LIST_TRUNKS_RESPONSE = "/network/listTrunksResponse.json";
    private static final String JSON_PORT_CREATE_RESPONSE = "/network/portCreateResponse.json";

    @Override
    protected Service service() {
        return Service.NETWORK;
    }

    public void createTrunk() throws Exception {
        respondWith(JSON_PORT_CREATE_RESPONSE);

        String network1Id = "466e612-73a3-45df-96b6-4c084dfe1fc7";
        String port1Name = "port1";
        String trunk1Name = "trunk1";

        Port toBuild = Builders.port().networkId(network1Id).name(port1Name).build();
        Port builtPort = osv3().networking().port().create(toBuild);

        respondWith(JSON_CREATE_TRUNK_RESPONSE);
        Trunk builtTrunk = osv3.networking().trunk()
                .create(Builders.neutron().trunk().name(trunk1Name).parentPort(builtPort.getId()).build());

        assertNotNull(builtTrunk);
        assertEquals(builtTrunk.getParentPort(), builtPort.getId());
        assertEquals(builtTrunk.getName(), trunk1Name);
    }

    public void deleteTrunk() throws Exception {
        respondWith(204);
        String trunkId = "8a2ea42d-06b5-42c2-a54d-97105420f2bb";
        ActionResponse delete = osv3().networking().trunk().delete(trunkId);
        assertTrue(delete.isSuccess());
    }

    public void listTrunks() throws Exception {
        respondWith(JSON_LIST_TRUNKS_RESPONSE);

        String trunk1Id = "cf15956d-4391-4ebf-a9cb-0f7e27b24073";
        String trunk2Id = "f98559e9-8e92-4100-96ac-a805e0340abd";
        List<String> trunkIds = new ArrayList<>();
        for (Trunk t : osv3().networking().trunk().list()) {
            assertNotNull(t);
            trunkIds.add(t.getId());
        }
        assertTrue(trunkIds.contains(trunk1Id));
        assertTrue(trunkIds.contains(trunk2Id));
    }

    public void updateTrunk() throws Exception {
        respondWith(JSON_UPDATE_TRUNK_RESPONSE);

        String trunkId = "f98559e9-8e92-4100-96ac-a805e0340abd";
        String updatedName = "changedName";
        Trunk updatedTrunk = osv3().networking().trunk().update(Builders.neutron().trunk().trunkId(trunkId).name(updatedName).build());
        assertNotNull(updatedTrunk);
        assertEquals(updatedTrunk.getName(), updatedName);
        assertEquals(updatedTrunk.getId(), trunkId);
    }

    public void addTrunkSubport() throws Exception {
        respondWith(JSON_ADD_SUBPORT_RESPONSE);

        String trunkId = "f98559e9-8e92-4100-96ac-a805e0340abd";
        String subPortId = "9d30c4d8-3bb6-4b59-99ab-5eaa22e55037";
        int segmentationId = 101;
        String segmentationType = "vlan";
        TrunkSubport subPort = NeutronTrunkSubport.builder().portId(subPortId).segmentationId(segmentationId)
                .segmentationType("vlan").build();
        Trunk withSubPort = osv3().networking().trunk().addTrunkSubport(trunkId, subPort);
        assertNotNull(withSubPort);
        assertEquals(subPortId, withSubPort.getTrunkSubports().get(0).getPortId());
        assertEquals(segmentationId, withSubPort.getTrunkSubports().get(0).getSegmentationId());
        assertEquals(segmentationType, withSubPort.getTrunkSubports().get(0).getSegmentationType());
    }

    public void removeTrunkSubport() throws Exception {
        respondWith(JSON_REMOVE_SUBPORT_RESPONSE);

        String trunkId = "f98559e9-8e92-4100-96ac-a805e0340abd";
        String subPortId = "9d30c4d8-3bb6-4b59-99ab-5eaa22e55037";
        Trunk withoutSubport = osv3().networking().trunk().removeTrunkSubport(trunkId, subPortId);
        assertNotNull(withoutSubport);
        assertTrue(withoutSubport.getTrunkSubports().isEmpty());
    }

    public void listTrunkSubports() throws Exception {
        respondWith(JSON_LIST_SUBPORTS_RESPONSE);

        String trunkId = "f98559e9-8e92-4100-96ac-a805e0340abd";
        List<String> ids = new ArrayList<>();
        List<NeutronTrunkSubport> subPorts = osv3().networking().trunk().listTrunkSubports(trunkId);
        assertNotNull(subPorts);
        for (TrunkSubport subPort : subPorts) {
            assertNotNull(subPort);
            ids.add(subPort.getId());
        }
        assertEquals(ids.size(), 2);
    }

    public void getTrunk() throws Exception {
        respondWith(JSON_GET_TRUNK_RESPONSE);

        String trunkId = "cf15956d-4391-4ebf-a9cb-0f7e27b24073";
        String portId = "e2d70799-b1e3-4737-9298-23cfb5c94416";
        Trunk trunk = osv3().networking().trunk().get(trunkId);
        assertNotNull(trunk);
        assertEquals(trunkId, trunk.getId());
        assertEquals(portId, trunk.getParentPort());
    }
}
