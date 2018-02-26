package metro.ticketing.app.model;

public enum RequestStatus {
    NEW("FREE"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    protected String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
