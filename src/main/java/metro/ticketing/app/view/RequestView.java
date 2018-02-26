package metro.ticketing.app.view;

import metro.ticketing.app.model.RequestStatus;

public class RequestView {

    private Long id;

    private RequestStatus requestStatus;

    private String name;

    private String email;

    private Long roleId;

    public RequestView() {
    }

    public RequestView(final Long id, final RequestStatus requestStatus, final String name, final String email, final Long roleId) {
        this.id = id;
        this.requestStatus = requestStatus;
        this.name = name;
        this.email = email;
        this.roleId = roleId;
    }

    public RequestView(final RequestStatus requestStatus, final String name, final String email, final Long roleId) {
        this.requestStatus = requestStatus;
        this.name = name;
        this.email = email;
        this.roleId = roleId;
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

    public String getName() {
        return name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setRequestStatus(final RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setRoleId(final Long roleId) {
        this.roleId = roleId;
    }
}
