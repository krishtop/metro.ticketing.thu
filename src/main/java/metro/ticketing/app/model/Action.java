package metro.ticketing.app.model;

public class Action {

    private Long id;

    private String name;

    private String description;


    public Action(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public Action(final Long id, final String name, final String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Action)) return false;

        Action action = (Action) o;

        if (getName() != null ? !getName().equals(action.getName()) : action.getName() != null) return false;
        return getDescription() != null ? getDescription().equals(action.getDescription()) : action.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
