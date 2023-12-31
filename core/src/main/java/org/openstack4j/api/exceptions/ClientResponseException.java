package org.openstack4j.api.exceptions;

import org.openstack4j.util.ToStringHelper;

/**
 * Captures Server based Errors (Return Codes between 400 - 499)
 *
 * @author Jeremy Unruh
 */
public class ClientResponseException extends ResponseException {

    private static final long serialVersionUID = 1L;

    private final StatusCode code;

    public ClientResponseException(String message, int status, Throwable cause) {
        super(message, status, cause);
        code = StatusCode.fromCode(status);
    }

    public ClientResponseException(String message, int status) {
        super(message, status);
        code = StatusCode.fromCode(status);
    }

    /**
     * @return the status code mapping for the current {@link #getStatus()}
     */
    public StatusCode getStatusCode() {
        return code;
    }

    @Override
    public String toString() {
        return new ToStringHelper(this)
                .add("message", getMessage())
                .add("status", getStatus())
                .add("status-code", code)
                .add("X-Openstack-Request-Id", requestId)
                .add("request", requestInfo)
                .toString();
    }
}
