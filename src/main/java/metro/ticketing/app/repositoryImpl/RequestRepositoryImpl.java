package metro.ticketing.app.repositoryImpl;

import metro.ticketing.app.model.Request;
import metro.ticketing.app.model.RequestStatus;
import metro.ticketing.app.model.Role;
import metro.ticketing.app.repository.RequestRepository;
import metro.ticketing.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class RequestRepositoryImpl implements RequestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RoleRepository roleRepository;

    public RequestRepositoryImpl(final DataSource dataSource, final RoleRepository roleRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.roleRepository = roleRepository;
    }

    @Override
    public Request create(final Request request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO request(request_status, name, email, role_id) VALUES (?,?,?,?)", new String[]{"id"});
                    ps.setString(1, request.getRequestStatus().getValue());
                    ps.setString(2, request.getName());
                    ps.setString(3, request.getEmail());
                    ps.setLong(4, request.getRole().getId());
                    return ps;
                },
                keyHolder);

        long primaryKey = keyHolder.getKey().longValue();

        return get(primaryKey);
    }

    @Override
    public Request update(final Long oldUserId, final Request request) {
        jdbcTemplate.update("DELETE FROM request WHERE ID=?", oldUserId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO request(id, request_status, name, email, role_id) VALUES (?,?,?,?,?)", new String[]{"id"});
                    ps.setLong(1, oldUserId);
                    ps.setString(2, request.getRequestStatus().getValue());
                    ps.setString(3, request.getName());
                    ps.setString(4, request.getEmail());
                    ps.setLong(5, request.getRole().getId());
                    return ps;
                },
                keyHolder);

        long primaryKey = keyHolder.getKey().longValue();

        return get(primaryKey);
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update("DELETE FROM request WHERE ID=?", id);
    }

    @Override
    public Request get(final Long id) {

        Collection<Request> result = jdbcTemplate.query(
                "SELECT * FROM request WHERE id =" + id, (rs, rowNum) -> {
                    Long roleId = rs.getLong("role_id");
                    String request_status = rs.getString("request_status");
                    RequestStatus requestStatus = request_status.equals(RequestStatus.ACCEPTED.getValue())
                            ? RequestStatus.ACCEPTED : request_status.equals(RequestStatus.REJECTED.getValue())
                            ? RequestStatus.REJECTED : RequestStatus.NEW;
                    Role role = roleRepository.get(roleId);

                    return new Request(rs.getLong("id"),
                            requestStatus,
                            rs.getString("name"),
                            rs.getString("email"),
                            role);
                }
        );

        if (result.isEmpty()) {
            return null;
        }
        return result.iterator().next();
    }

    @Override
    public Collection<Request> getByStatus(final RequestStatus requestStatus) {
        return null;
    }

    @Override
    public Collection<Request> getAll() {
        Collection<Request> result = jdbcTemplate.query(
                "SELECT * FROM Request", (rs, rowNum) -> {
                    Long roleId = rs.getLong("role_id");
                    String request_status = rs.getString("request_status");
                    RequestStatus requestStatus = request_status.equals(RequestStatus.ACCEPTED.getValue())
                            ? RequestStatus.ACCEPTED : request_status.equals(RequestStatus.REJECTED.getValue())
                            ? RequestStatus.REJECTED : RequestStatus.NEW;
                    Role role = roleRepository.get(roleId);

                    return new Request(rs.getLong("id"),
                            requestStatus,
                            rs.getString("name"),
                            rs.getString("email"),
                            role);
                }
        );
        return result;
    }
}
