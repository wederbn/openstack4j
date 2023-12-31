package org.openstack4j.openstack.heat.internal;

import java.util.List;
import java.util.Objects;

import org.openstack4j.api.heat.EventsService;
import org.openstack4j.model.heat.Event;
import org.openstack4j.openstack.heat.domain.HeatEvent;
import org.openstack4j.openstack.heat.domain.HeatEvent.Events;

/**
 * This class implements some methods for manipulation of {@link HeatEvent} objects. The
 * non-exhaustive list of methods is oriented along
 * http://developer.openstack.org/api-ref-orchestration-v1.html#stacks
 *
 * @author Octopus Zhang
 */
public class EventsServiceImpl extends BaseHeatServices implements EventsService {

    @Override
    public List<? extends Event> list(String stackName, String stackId) {
        Objects.requireNonNull(stackId);
        Objects.requireNonNull(stackName);
        return get(Events.class, uri("/stacks/%s/%s/events", stackName, stackId)).execute().getList();
    }

    @Override
    public List<? extends Event> list(String stackName, String stackId, String resourceName) {
        Objects.requireNonNull(stackId);
        Objects.requireNonNull(stackName);
        Objects.requireNonNull(resourceName);
        return get(Events.class, uri("/stacks/%s/%s/resources/%s/events", stackName, stackId, resourceName)).execute().getList();
    }

    @Override
    public Event show(String stackName, String stackId, String resourceName, String eventId) {
        Objects.requireNonNull(stackId);
        Objects.requireNonNull(stackName);
        Objects.requireNonNull(resourceName);
        Objects.requireNonNull(eventId);
        return get(HeatEvent.class, uri("/stacks/%s/%s/resources/%s/events/%s", stackName, stackId, resourceName, eventId)).execute();
    }

}
