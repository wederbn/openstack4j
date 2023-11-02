package org.openstack4j.openstack.barbican.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.openstack4j.api.barbican.ContainerService;
import org.openstack4j.model.barbican.Container;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.openstack.barbican.domain.BarbicanContainer;
import org.openstack4j.openstack.barbican.domain.BarbicanContainer.Containers;

/**
 * {@inheritDoc}
 */
public class ContainerServiceImpl extends BaseBarbicanServices implements ContainerService {

    private static final String RESOURCE_PATH = "/containers";
    private static final String SPECIFIC_RESOURCE_PATH = RESOURCE_PATH + "/%s";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Container> list(Map<String, String> filteringParams) {
        Invocation<Containers> req = get(Containers.class, uri(RESOURCE_PATH));
        if (filteringParams != null) {
            for (Map.Entry<String, String> entry : filteringParams.entrySet()) {
                req = req.param(entry.getKey(), entry.getValue());
            }
        }
        return req.execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Container> list(final String name) {
        return list(Collections.singletonMap("name", name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Container get(final String containerId) {
        Objects.requireNonNull(containerId);
        return get(BarbicanContainer.class, uri(SPECIFIC_RESOURCE_PATH, containerId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(final String containerId) {
        Objects.requireNonNull(containerId);
        return deleteWithResponse(uri(SPECIFIC_RESOURCE_PATH, containerId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Container create(final Container container) {
        Objects.requireNonNull(container);
        return post(BarbicanContainer.class, uri(RESOURCE_PATH)).entity(container).execute();
    }
}
