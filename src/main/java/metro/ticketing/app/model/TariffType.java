package metro.ticketing.app.model;

public enum TariffType {
    TRIP("TRIP"),
    TIME("TIME");

    protected String value;

    TariffType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
