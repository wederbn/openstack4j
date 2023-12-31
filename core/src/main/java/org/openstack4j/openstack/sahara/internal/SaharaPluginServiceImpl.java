package org.openstack4j.openstack.sahara.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.sahara.SaharaPluginService;
import org.openstack4j.core.transport.ExecutionOptions;
import org.openstack4j.core.transport.propagation.PropagateOnStatus;
import org.openstack4j.model.common.Payload;
import org.openstack4j.model.sahara.ClusterTemplate;
import org.openstack4j.model.sahara.Plugin;
import org.openstack4j.openstack.sahara.domain.SaharaClusterTemplate;
import org.openstack4j.openstack.sahara.domain.SaharaPlugin;
import org.openstack4j.openstack.sahara.domain.SaharaPlugin.SaharaPlugins;

/**
 * Sahara Data Processing Operations
 *
 * @author ekasit.kijsipongse@nectec.or.th
 */
public class SaharaPluginServiceImpl extends BaseSaharaServices implements SaharaPluginService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Plugin> list() {
        return get(SaharaPlugins.class, uri("/plugins")).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plugin get(String name) {
        Objects.requireNonNull(name);
        return get(SaharaPlugin.class, uri("/plugins/%s", name)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Plugin get(String name, String version) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(version);
        return get(SaharaPlugin.class, uri("/plugins/%s/%s", name, version)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClusterTemplate convertConfig(String name, String version, String templateName, Payload<?> payload) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(version);
        Objects.requireNonNull(templateName);
        return post(SaharaClusterTemplate.class, uri("/plugins/%s/%s/convert-config/%s", name, version, templateName)).entity(payload)  // setup request
                .execute(ExecutionOptions.<SaharaClusterTemplate>create(PropagateOnStatus.on(404))); // Use respongse progagation for "Not found" status to throw exception instead of return null


    }

}
