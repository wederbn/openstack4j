package org.openstack4j.openstack.senlin.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.senlin.SenlinClusterService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.senlin.ActionID;
import org.openstack4j.model.senlin.Cluster;
import org.openstack4j.model.senlin.ClusterActionCreate;
import org.openstack4j.model.senlin.ClusterCreate;
import org.openstack4j.openstack.senlin.domain.SenlinActionID;
import org.openstack4j.openstack.senlin.domain.SenlinCluster;

/**
 * This class contains getters for all implementation of the available cluster services
 *
 * @author lion
 */
public class SenlinClusterServiceImpl extends BaseSenlinServices implements SenlinClusterService {

    @Override
    public List<? extends Cluster> list() {
        return get(SenlinCluster.Cluster.class, uri("/clusters")).execute().getList();
    }

    @Override
    public Cluster get(String clusterID) {
        Objects.requireNonNull(clusterID);
        return get(SenlinCluster.class, uri("/clusters/%s", clusterID)).execute();
    }

    @Override
    public Cluster create(ClusterCreate newCluster) {
        Objects.requireNonNull(newCluster);
        return post(SenlinCluster.class, uri("/clusters")).entity(newCluster).execute();
    }

    @Override
    public ActionResponse delete(String clusterID) {
        Objects.requireNonNull(clusterID);
        return deleteWithResponse(uri("/clusters/%s", clusterID)).execute();
    }

    @Override
    public Cluster update(String clusterID, ClusterCreate newCluster) {
        Objects.requireNonNull(clusterID);
        Objects.requireNonNull(newCluster);
        return patch(SenlinCluster.class, uri("/clusters/%s", clusterID)).entity(newCluster).execute();
    }

    @Override
    public ActionID action(String clusterID, ClusterActionCreate newClusterAction) {
        Objects.requireNonNull(clusterID);
        Objects.requireNonNull(newClusterAction);
        return post(SenlinActionID.class, uri("/clusters/%s/actions", clusterID)).entity(newClusterAction).execute();
    }

}
