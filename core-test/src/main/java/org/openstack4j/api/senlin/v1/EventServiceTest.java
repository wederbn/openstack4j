package org.openstack4j.api.senlin.v1;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import org.openstack4j.api.AbstractTest;
import org.openstack4j.model.senlin.Event;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Test cases for event on Senlin
 *
 * @author lion
 */
@Test(suiteName = "senlin/event")
public class EventServiceTest extends AbstractTest {

    private static final String EVENTS = "/senlin/v1/events.json";
    private static final String EVENT = "/senlin/v1/event.json";

    @Override
    protected Service service() {
        return Service.CLUSTERING;
    }

    @Test
    public void testListEvent() throws Exception {
        respondWith(EVENTS);
        List<? extends Event> eventList = osv3().senlin().event().list();
        assertEquals(4, eventList.size());
        Objects.requireNonNull(eventList.get(0));
        Logger.getLogger(getClass().getName()).info(getClass().getName() + " : Event from List : " + eventList.get(0));
        assertEquals(eventList.get(0).getId(), "b6d7b823-1811-492b-8a54-fb15a5a0bafe");
    }

    @Test
    public void testGetEvent() throws Exception {
        respondWith(EVENT);
        String enentID = "2d255b9c-8f36-41a2-a137-c0175ccc29c3";
        Event event = osv3().senlin().event().get(enentID);
        Logger.getLogger(getClass().getName()).info(getClass().getName() + " : Event by ID : " + event);
        assertNotNull(event);
        assertEquals(enentID, event.getId());
    }

}
