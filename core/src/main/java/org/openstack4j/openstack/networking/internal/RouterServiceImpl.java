package org.openstack4j.openstack.networking.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.networking.RouterService;
import org.openstack4j.core.transport.ExecutionOptions;
import org.openstack4j.core.transport.propagation.PropagateOnStatus;
import org.openstack4j.model.common.ActionResponse;
import org.openstack4j.model.network.AttachInterfaceType;
import org.openstack4j.model.network.HostRoute;
import org.openstack4j.model.network.Router;
import org.openstack4j.model.network.RouterInterface;
import org.openstack4j.model.network.builder.RouterBuilder;
import org.openstack4j.openstack.networking.domain.AddRouterInterfaceAction;
import org.openstack4j.openstack.networking.domain.NeutronRouter;
import org.openstack4j.openstack.networking.domain.NeutronRouter.Routers;
import org.openstack4j.openstack.networking.domain.NeutronRouterInterface;

/**
 * RouterService implementation that provides Neutron Router based Service Operations.
 *
 * @author Jeremy Unruh
 */
public class RouterServiceImpl extends BaseNetworkingServices implements RouterService {

    /**
     * {@inheritDoc}
     */
    @Override
    public List<? extends Router> list() {
        return get(Routers.class, uri("/routers")).execute().getList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Router get(String routerId) {
        Objects.requireNonNull(routerId);
        return get(NeutronRouter.class, uri("/routers/%s", routerId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse delete(String routerId) {
        Objects.requireNonNull(routerId);
        return deleteWithResponse(uri("/routers/%s", routerId)).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Router create(String name, boolean adminStateUp) {
        Objects.requireNonNull(name);
        return post(NeutronRouter.class, uri("/routers")).entity(NeutronRouter.builder().name(name).adminStateUp(adminStateUp).build()).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Router create(Router router) {
        Objects.requireNonNull(router);
        return post(NeutronRouter.class, uri("/routers")).entity(router).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Router update(Router router) {
        Objects.requireNonNull(router);
        Objects.requireNonNull(router.getId());

        RouterBuilder rb = NeutronRouter.builder().name(router.getName()).adminStateUp(router.isAdminStateUp()).externalGateway(router.getExternalGatewayInfo());
        List<? extends HostRoute> routes = router.getRoutes();

        if (routes != null && !routes.isEmpty()) {
            for (HostRoute route : routes) {
                rb.route(route.getDestination(), route.getNexthop());
            }
        } else {
            rb.noRoutes();
        }

        return put(NeutronRouter.class, uri("/routers/%s", router.getId()))
                .entity(rb.build())
                .execute();
    }

    @Override
    public Router toggleAdminStateUp(String routerId, boolean adminStateUp) {
        Objects.requireNonNull(routerId);
        return put(NeutronRouter.class, uri("/routers/%s", routerId)).entity(NeutronRouter.builder().adminStateUp(adminStateUp).build()).execute();
    }

    @Override
    public RouterInterface attachInterface(String routerId, AttachInterfaceType type, String portOrSubnetId) {
        Objects.requireNonNull(routerId);
        Objects.requireNonNull(type);
        Objects.requireNonNull(portOrSubnetId);
        return put(NeutronRouterInterface.class, uri("/routers/%s/add_router_interface", routerId))
                .entity(AddRouterInterfaceAction.create(type, portOrSubnetId))
                .execute();
    }

    @Override
    public RouterInterface detachInterface(String routerId, String subnetId, String portId) {
        Objects.requireNonNull(routerId);
        if (subnetId == null && portId == null) {
            throw new IllegalStateException("Either a Subnet or Port identifier must be set");
        }
        return put(NeutronRouterInterface.class, uri("/routers/%s/remove_router_interface", routerId))
                .entity(new NeutronRouterInterface(subnetId, portId))
                .execute(ExecutionOptions.<NeutronRouterInterface>create(PropagateOnStatus.on(404)));
    }

}
