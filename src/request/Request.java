package src.request;

public abstract class Request {
    public enum Header {
        PUBLISH,
        RCV_IDS,
        RCV_MSG,
        REPLY,
        REPUBLISH,
        CONNECT,
        SUBSCRIBE,
        UNSUBSCRIBE
    }

    public Header header;
    public String author;
    public String message;
}
