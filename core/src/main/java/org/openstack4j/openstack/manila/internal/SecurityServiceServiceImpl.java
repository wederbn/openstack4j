package org.openstack4j.openstack.manila.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.manila.SecurityServiceService;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.manila.SecurityService;
import org.openstack4j.model.manila.SecurityServiceCreate;
import org.openstack4j.model.manila.SecurityServiceUpdateOptions;
import org.openstack4j.model.manila.builder.SecurityServiceCreateBuilder;
import org.openstack4j.openstack.compute.functions.ToActionResponseFunction;
import org.openstack4j.openstack.manila.domain.ManilaSecurityService;
import org.openstack4j.openstack.manila.domain.ManilaSecurityServiceCreate;
import org.openstack4j.openstack.manila.domain.ManilaSecurityServiceUpdate;

public class SecurityServiceServiceImpl extends BaseShareServices implements SecurityServiceService {
    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityService create(SecurityServiceCreate securityServiceCreate) {
        Objects.requireNonNull(securityServiceCreate);
        return post(ManilaSecurityService.class, uri("/security-services"))
                .entity(securityServiceCreate)
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends SecurityService> list() {
        return list(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends SecurityService> listDetails() {
        return list(true);
    }

    private List<? extends SecurityService> list(boolean detail) {
        return get(ManilaSecurityService.SecurityServices.class, uri("/security-services" + (detail ? "/detail": "")))
                .execute()
                .getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityService get(String securityServiceId) {
        Objects.requireNonNull(securityServiceId);
        return get(ManilaSecurityService.class, uri("/security-services/%s", securityServiceId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityService update(String securityServiceId, SecurityServiceUpdateOptions securityServiceUpdateOptions) {
        Objects.requireNonNull(securityServiceId);
        Objects.requireNonNull(securityServiceUpdateOptions);

        return put(ManilaSecurityService.class, uri("/security-services/%s", securityServiceId))
                .entity(ManilaSecurityServiceUpdate.fromOptions(securityServiceUpdateOptions))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String securityServiceId) {
        Objects.requireNonNull(securityServiceId);
        return ToActionResponseFunction.INSTANCE.apply(
                delete(Void.class, uri("/security-services/%s", securityServiceId)).executeWithResponse());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SecurityServiceCreateBuilder securityServiceCreateBuilder() {
        return ManilaSecurityServiceCreate.builder();
    }
}
