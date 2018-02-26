package metro.ticketing.app.repositoryImpl;

import metro.ticketing.app.model.Action;
import metro.ticketing.app.model.Role;
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
public class RoleRepositoryImpl implements RoleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public RoleRepositoryImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Role create(final Role role) {

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO Role(name) VALUES (?)", new String[]{"id"});
                    ps.setString(1, role.getName());
                    return ps;
                },
                keyHolder);
        long roleKey = keyHolder.getKey().longValue();

        role.getActions().forEach(a -> {
            jdbcTemplate.update("INSERT INTO action_role(action_id, role_id) VALUES (?,?)",
                    a.getId(), roleKey);
        });


        return get(roleKey);
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update("DELETE FROM action_role WHERE role_id=?", id);
        jdbcTemplate.update("DELETE FROM Role WHERE ID=?", id);
    }

    @Override
    public Role get(final Long id) {

        Collection<Role> result = jdbcTemplate.query(
                "SELECT * FROM Role WHERE id=" + id, (rs, rowNum) -> {
                    Long roleId = rs.getLong("id");

                    Collection<Action> actions = jdbcTemplate.query(
                            "SELECT * from action JOIN action_role on action.id = action_role.action_id WHERE role_id =" + roleId + "",
                            (rs_, rowNum_) -> new Action(rs_.getLong("id"),
                                    rs_.getString("name"),
                                    rs_.getString("description"))
                    );
                    return new Role(roleId, rs.getString("name"), actions);
                }
        );

        if (result.isEmpty()) {
            return null;
        }
        return result.iterator().next();
    }

    @Override
    public Collection<Role> getAll() {
        Collection<Role> result = jdbcTemplate.query(
                "SELECT * FROM Role",
                (rs, rowNum) -> {
                    Long roleId = rs.getLong("id");

                    Collection<Action> actions = jdbcTemplate.query(
                            "SELECT * from action JOIN action_role on action.id = action_role.action_id WHERE role_id =" + roleId + "",
                            (rs_, rowNum_) -> new Action(rs_.getLong("id"),
                                    rs_.getString("name"),
                                    rs_.getString("description"))
                    );
                    return new Role(roleId, rs.getString("name"), actions);
                }
        );

        return result;
    }
}
