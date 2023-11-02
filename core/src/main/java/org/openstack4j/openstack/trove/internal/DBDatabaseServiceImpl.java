package org.openstack4j.openstack.trove.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.trove.DatabaseService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.trove.Database;
import org.openstack4j.openstack.trove.domain.TroveDatabase.Databases;

/**
 * Database API Implementation
 *
 * @author sumit gandhi
 */
public class DBDatabaseServiceImpl extends BaseTroveServices implements DatabaseService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Database> list(String instanceId) {
        return get(Databases.class, uri("/instances/%s/databases", instanceId)).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse create(String instanceId, Databases databases) {
        Objects.requireNonNull(instanceId);
        Objects.requireNonNull(databases);
        return post(ActionResponse.class, uri("/instances/%s/databases", instanceId)).entity(databases).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String instanceId, String dbName) {
        Objects.requireNonNull(instanceId);
        Objects.requireNonNull(dbName);
        return deleteWithResponse(uri("/instances/%s/databases/%s", instanceId, dbName)).execute();
    }

}
