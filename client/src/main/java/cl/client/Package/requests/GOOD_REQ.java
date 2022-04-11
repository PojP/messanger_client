package cl.client.Package.requests;

import java.io.Serializable;

public class GOOD_REQ implements Requestable, Serializable {
    RequestsTypes Type = RequestsTypes.GOOD_REQ;
    private String GOOD_REQUEST_STACKTRACE;
    public RequestsTypes GetType() {
        return Type;
    }
    public GOOD_REQ(String g_msg) {
        GOOD_REQUEST_STACKTRACE = g_msg;
    }

    public String GetStackTrace() {
        return GOOD_REQUEST_STACKTRACE;
    }
}
