package metro.ticketing.app.repositoryImpl;

import metro.ticketing.app.model.Tariff;
import metro.ticketing.app.model.Ticket;
import metro.ticketing.app.repository.TariffRepository;
import metro.ticketing.app.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class TicketRepositoryImpl implements TicketRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TariffRepository tariffRepository;

    public TicketRepositoryImpl(final DataSource dataSource, final TariffRepository tariffRepository) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.tariffRepository = tariffRepository;
    }

    @Override
    public Ticket create(final Ticket ticket) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO Ticket(activated, remining_trips, exp_Time, tariff_id) VALUES (?,?,?,?)", new String[]{"id"});
                    ps.setBoolean(1, ticket.getActivated());
                    if (ticket.getReminingTrips() == null) {
                        ps.setNull(2, 4);
                    } else {
                        ps.setInt(2, ticket.getReminingTrips());
                    }
                    if (ticket.getExpTime() == null) {
                        ps.setNull(3, 91);
                    } else {
                        ps.setDate(3, Date.valueOf(ticket.getExpTime()));
                    }
                    ps.setLong(4, ticket.getTariff().getId());
                    return ps;
                },
                keyHolder);

        long primaryKey = keyHolder.getKey().longValue();
        return get(primaryKey);
    }

    @Override
    public Ticket update(final Long oldTicketId, final Ticket ticket) {

        jdbcTemplate.update("DELETE FROM Ticket WHERE ID=?", oldTicketId);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO Ticket(id, activated, remining_trips, exp_Time, tariff_id) VALUES (?,?,?,?,?)", new String[]{"id"});
                    ps.setLong(1, oldTicketId);
                    ps.setBoolean(2, ticket.getActivated());
                    if (ticket.getReminingTrips() == null) {
                        ps.setNull(3, 4);
                    } else {
                        ps.setInt(3, ticket.getReminingTrips());
                    }
                    if (ticket.getExpTime() == null) {
                        ps.setNull(4, 91);
                    } else {
                        ps.setDate(4, Date.valueOf(ticket.getExpTime()));
                    }
                    ps.setLong(5, ticket.getTariff().getId());
                    return ps;
                },
                keyHolder);

        long primaryKey = keyHolder.getKey().longValue();
        return get(primaryKey);
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update("DELETE FROM Ticket WHERE ID=?", id);
    }

    @Override
    public Collection<Ticket> getAll() {
        Collection<Ticket> result = jdbcTemplate.query(
                "SELECT * FROM Ticket", (rs, rowNum) -> {
                    Long tariff_id = rs.getLong("tariff_id");

                    Tariff tariff = tariffRepository.get(tariff_id);
                    Date date = rs.getDate("exp_Time");

                    return new Ticket(rs.getLong("id"),
                            rs.getBoolean("activated"),
                            rs.getInt("remining_trips"),
                            date != null ? date.toLocalDate() : null,
                            tariff);
                }
        );
        return result;
    }

    @Override
    public Ticket get(final Long id) {

        Collection<Ticket> result = jdbcTemplate.query(
                "SELECT * FROM Ticket  WHERE id =" + id, (rs, rowNum) -> {
                    Long tariff_id = rs.getLong("tariff_id");

                    Tariff tariff = tariffRepository.get(tariff_id);
                    Date date = rs.getDate("exp_Time");

                    return new Ticket(rs.getLong("id"),
                            rs.getBoolean("activated"),
                            rs.getInt("remining_trips"),
                            date != null ? date.toLocalDate() : null,
                            tariff);
                }
        );

        if (result.isEmpty()) {
            return null;
        }
        return result.iterator().next();
    }
}
