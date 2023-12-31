package org.openstack4j.openstack.murano.v1.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.murano.v1.MuranoEnvironmentService;
import org.openstack4j.model.ModelEntity;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.murano.v1.domain.Environment;
import org.openstack4j.openstack.murano.v1.domain.MuranoEnvironment;

/**
 * This class implements all methods for manipulation of {@link MuranoEnvironment} objects.
 *
 * @author Nikolay Mahotkin
 */
public class MuranoEnvironmentServiceImpl extends BaseMuranoServices implements MuranoEnvironmentService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends MuranoEnvironment> list() {
        return get(MuranoEnvironment.MuranoEnvironments.class, uri("/environments")).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Environment get(String id) {
        Objects.requireNonNull(id);
        return get(MuranoEnvironment.class, uri("/environments/%s", id)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Environment create(Environment env) {
        Objects.requireNonNull(env);
        return post(MuranoEnvironment.class, uri("/environments"))
                .entity(env)  // setup request
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String id) {
        Objects.requireNonNull(id);
        return deleteWithResponse(uri("/environments/%s", id)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Environment rename(String id, String name) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        return put(MuranoEnvironment.class, uri("/environments/%s", id))
                .entity(new RenameEnvironmentRequest(name))
                .execute();
    }

    private class RenameEnvironmentRequest implements ModelEntity {
        public static final long serialVersionUID = 1L;

        private String name;

        RenameEnvironmentRequest(String name) {
            this.name = name;
        }

        /**
         * @return name
         */
        public String getName() {
            return this.name;
        }
    }
}
