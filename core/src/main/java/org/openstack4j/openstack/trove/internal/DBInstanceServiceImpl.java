package org.openstack4j.openstack.trove.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.trove.InstanceService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.trove.Instance;
import org.openstack4j.model.trove.InstanceCreate;
import org.openstack4j.openstack.trove.domain.TroveInstance;
import org.openstack4j.openstack.trove.domain.TroveInstance.DBInstances;

/**
 * InstanceService API Implementation
 *
 * @author Shital Patil
 */
public class DBInstanceServiceImpl extends BaseTroveServices implements InstanceService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Instance> list() {
        return get(DBInstances.class, uri("/instances")).execute().getList();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instance get(String instanceId) {
        Objects.requireNonNull(instanceId);
        TroveInstance instance = get(TroveInstance.class, uri("/instances/%s", instanceId)).execute();
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Instance create(InstanceCreate instanceCreate) {
        return post(TroveInstance.class, uri("/instances")).entity(instanceCreate).execute();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String id) {
        Objects.requireNonNull(id);
        return deleteWithResponse(uri("/instances/%s", id)).execute();
    }
}
