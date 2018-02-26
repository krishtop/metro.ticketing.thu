package metro.ticketing.app.model;

import java.util.Collection;

public class Role {

    private Long id;

    private String name;

    private Collection<Action> actions;

    public Role(final String name, final Collection<Action> actions) {
        this.name = name;
        this.actions = actions;
    }

    public Role(final Long id, final String name, final Collection<Action> actions) {
        this.id = id;
        this.name = name;
        this.actions = actions;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Collection<Action> getActions() {
        return actions;
    }

    public void setActions(final Collection<Action> actions) {
        this.actions = actions;
    }

    public void addAction(final Action action) {
        actions.add(action);
    }

    public void removeAction(final Action action) {
        actions.remove(action);
    }

    public Boolean checkPermission(final Action action) {
        return actions.contains(action);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        if (getId() != null ? !getId().equals(role.getId()) : role.getId() != null) return false;
        if (getName() != null ? !getName().equals(role.getName()) : role.getName() != null) return false;
        return getActions() != null ? getActions().equals(role.getActions()) : role.getActions() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getActions() != null ? getActions().hashCode() : 0);
        return result;
    }
}
