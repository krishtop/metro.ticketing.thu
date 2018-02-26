package metro.ticketing.app.repositoryImpl;

import metro.ticketing.app.model.Action;
import metro.ticketing.app.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Collection;


@Repository
public class ActionRepositoryImpl implements ActionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ActionRepositoryImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Action get(final Long id) {
        Collection<Action> result = jdbcTemplate.query(
                "SELECT id, name, description FROM Action WHERE id = " + id,
                (rs, rowNum) -> new Action(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"))
        );

        if (result.isEmpty()) {
            return null;
        }
        return result.iterator().next();
    }

    @Override
    public Action create(final Action action) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO Action(name, description) VALUES (?,?)", new String[]{"id"});
                    ps.setString(1, action.getName());
                    ps.setString(2, action.getDescription());
                    return ps;
                },
                keyHolder);

        long primaryKey = keyHolder.getKey().longValue();
        return get(primaryKey);
    }

    @Override
    public Action delete(final Long id) {

        jdbcTemplate.update("DELETE FROM Action WHERE ID=?", id);
        return null;
    }

    @Override
    public Collection<Action> getAll() {

        Collection<Action> result = jdbcTemplate.query(
                "SELECT id, name, description FROM Action",
                (rs, rowNum) -> new Action(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"))
        );

        return result;
    }
}
