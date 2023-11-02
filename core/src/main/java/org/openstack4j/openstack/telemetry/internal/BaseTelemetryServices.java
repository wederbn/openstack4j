package org.openstack4j.openstack.telemetry.internal;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import java.util.function.Function;
import org.openstack4j.api.types.ServiceType;
import org.openstack4j.openstack.internal.BaseOpenStackService;

/**
 * Base class for Telemetry services
 *
 * @author Jeremy Unruh
 */
public class BaseTelemetryServices extends BaseOpenStackService {

    protected BaseTelemetryServices() {
        super(ServiceType.TELEMETRY, EndpointFunction.instance);
    }

    protected <T> List<T> wrapList(T[] type) {
        if (type != null)
            return Arrays.asList(type);
        return Collections.emptyList();

    }

    /**
     * Sometimes the endpoint does not contain the API version which is required.  This insures that
     */
    private static class EndpointFunction implements Function<String, String> {

        static final EndpointFunction instance = new EndpointFunction();

        @Override
        public String apply(String input) {
            if (input == null || input.contains("/v"))
                return input;
            return input.concat(input.endsWith("/") ? "v2": "/v2");
        }
    }
}
