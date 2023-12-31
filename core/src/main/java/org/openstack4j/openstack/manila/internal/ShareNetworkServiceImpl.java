package org.openstack4j.openstack.manila.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.manila.ShareNetworkService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.manila.ShareNetwork;
import org.openstack4j.model.manila.ShareNetworkCreate;
import org.openstack4j.model.manila.ShareNetworkUpdateOptions;
import org.openstack4j.model.manila.builder.ShareNetworkCreateBuilder;
import org.openstack4j.openstack.compute.functions.ToActionResponseFunction;
import org.openstack4j.openstack.manila.domain.ManilaShareNetwork;
import org.openstack4j.openstack.manila.domain.ManilaShareNetworkCreate;
import org.openstack4j.openstack.manila.domain.ManilaShareNetworkUpdate;
import org.openstack4j.openstack.manila.domain.actions.SecurityServiceAction;

public class ShareNetworkServiceImpl extends BaseShareServices implements ShareNetworkService {
    /**
     * {@inheritDoc}
     */
    @Override
    public ShareNetwork create(ShareNetworkCreate shareNetworkCreate) {
        Objects.requireNonNull(shareNetworkCreate);
        return post(ManilaShareNetwork.class, uri("/share-networks"))
                .entity(shareNetworkCreate)
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ShareNetwork> list() {
        return list(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends ShareNetwork> listDetails() {
        return list(true);
    }

    private List<? extends ShareNetwork> list(boolean detail) {
        return get(ManilaShareNetwork.ShareNetworks.class, uri("/share-networks" + (detail ? "/detail": "")))
                .execute()
                .getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShareNetwork get(String shareNetworkId) {
        Objects.requireNonNull(shareNetworkId);
        return get(ManilaShareNetwork.class, uri("/share-networks/%s", shareNetworkId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShareNetwork update(String shareNetworkId, ShareNetworkUpdateOptions shareNetworkUpdateOptions) {
        Objects.requireNonNull(shareNetworkId);
        Objects.requireNonNull(shareNetworkUpdateOptions);

        return put(ManilaShareNetwork.class, uri("/share-networks/%s", shareNetworkId))
                .entity(ManilaShareNetworkUpdate.fromOptions(shareNetworkUpdateOptions))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String shareNetworkId) {
        Objects.requireNonNull(shareNetworkId);
        return ToActionResponseFunction.INSTANCE.apply(
                delete(Void.class, uri("/share-networks/%s", shareNetworkId)).executeWithResponse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShareNetwork addSecurityService(String shareNetworkId, String securityServiceId) {
        Objects.requireNonNull(shareNetworkId);
        Objects.requireNonNull(securityServiceId);

        return invokeSecurityServiceAction(shareNetworkId, SecurityServiceAction.add(securityServiceId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShareNetwork removeSecurityService(String shareNetworkId, String securityServiceId) {
        Objects.requireNonNull(shareNetworkId);
        Objects.requireNonNull(securityServiceId);

        return invokeSecurityServiceAction(shareNetworkId, SecurityServiceAction.remove(securityServiceId));
    }

    private ShareNetwork invokeSecurityServiceAction(String shareNetworkId, SecurityServiceAction action) {
        return post(ManilaShareNetwork.class, uri("/share-networks/%s/action", shareNetworkId))
                .entity(action)
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShareNetworkCreateBuilder shareNetworkCreateBuilder() {
        return ManilaShareNetworkCreate.builder();
    }
}
