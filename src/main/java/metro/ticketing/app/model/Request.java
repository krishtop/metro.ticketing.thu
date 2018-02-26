package metro.ticketing.app.model;


import metro.ticketing.app.exception.AccessDeniedException;

public class Request {

    private Long id;

    private RequestStatus requestStatus;

    private String name;

    private String email;

    private Role role;

    public Request(final RequestStatus requestStatus, final String name, final String email, final Role role) {
        this.requestStatus = requestStatus;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public Request(final Long id, final RequestStatus requestStatus, final String name, final String email, final Role role) {
        this.id = id;
        this.requestStatus = requestStatus;
        this.name = name;
        this.email = email;
        this.role = role;
    }


    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(final RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(final Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void accept(Role role) {
        final Action action = new Action("ACCEPT_REQUEST", "");
        if (!role.checkPermission(action)) {
            throw new AccessDeniedException("");
        }

        requestStatus = RequestStatus.ACCEPTED;
    }

    public void reject(Role role) {
        final Action action = new Action("REJECT_REQUEST", "");
        if (!role.checkPermission(action)) {
            throw new AccessDeniedException("");
        }

        requestStatus = RequestStatus.REJECTED;
    }

}
