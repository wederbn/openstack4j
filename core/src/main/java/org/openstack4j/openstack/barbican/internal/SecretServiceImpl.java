package org.openstack4j.openstack.barbican.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.openstack4j.api.barbican.SecretService;
import org.openstack4j.model.barbican.Secret;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.openstack.barbican.domain.BarbicanSecret;
import org.openstack4j.openstack.barbican.domain.BarbicanSecret.Secrets;

/**
 * Created by reneschollmeyer on 02.08.17.
 * <p>
 * {@inheritDoc}
 */
public class SecretServiceImpl extends BaseBarbicanServices implements SecretService {

    private static final String RESOURCE_PATH = "/secrets";
    private static final String SPECIFIC_RESOURCE_PATH = RESOURCE_PATH + "/%s";

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Secret> list(Map<String, String> filteringParams) {
        Invocation<Secrets> req = get(Secrets.class, uri(RESOURCE_PATH));
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
    public List<? extends Secret> list(final String name) {
        return list(Collections.singletonMap("name", name));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Secret get(String secretId) {
        Objects.requireNonNull(secretId);
        return get(BarbicanSecret.class, uri(SPECIFIC_RESOURCE_PATH, secretId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String secretId) {
        Objects.requireNonNull(secretId);
        return deleteWithResponse(uri(SPECIFIC_RESOURCE_PATH, secretId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Secret create(Secret secret) {
        Objects.requireNonNull(secret);
        return post(BarbicanSecret.class, uri(RESOURCE_PATH)).entity(secret).execute();
    }
}
