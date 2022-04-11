package cl.client.Package.requests;

import java.io.Serializable;

public class BAD_REQ implements Requestable, Serializable {
    RequestsTypes Type = RequestsTypes.BAD_REQ;
    private String BAD_REQUEST_STACKTRACE;
    public RequestsTypes GetType() {
        return Type;
    }
    public BAD_REQ(String s_trace) {
        BAD_REQUEST_STACKTRACE = s_trace;
    }

    public String GetStackTrace() {
        return BAD_REQUEST_STACKTRACE;
    }
}
