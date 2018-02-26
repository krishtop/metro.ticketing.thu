package metro.ticketing.app.repositoryImpl;


import metro.ticketing.app.model.*;
import metro.ticketing.app.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Collection;

@Repository
public class TariffRepositoryImpl implements TariffRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public TariffRepositoryImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Tariff create(final Tariff tariff) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps =
                            connection.prepareStatement("INSERT INTO Tariff(tariff_type, day_Count, trips_Count) VALUES (?,?,?)", new String[]{"id"});
                    ps.setString(1, tariff.getTariffType().getValue());
                    if (tariff.getDayCount() == null) {
                        ps.setNull(2, 4);
                    } else {
                        ps.setInt(2, tariff.getDayCount());
                    }
                    if (tariff.getTripsCount() == null) {
                        ps.setNull(3, 4);
                    } else {
                        ps.setInt(3, tariff.getTripsCount());
                    }
                    return ps;
                },
                keyHolder);

        long primaryKey = keyHolder.getKey().longValue();
        return get(primaryKey);
    }

    @Override
    public void delete(final Long id) {
        jdbcTemplate.update("DELETE FROM Tariff WHERE ID=?", id);
    }

    @Override
    public Tariff get(final Long id) {

        Collection<Tariff> result = jdbcTemplate.query(
                "SELECT * FROM Tariff WHERE id =" + id, (rs, rowNum) -> {
                    String tariffTypeStr = rs.getString("tariff_type");
                    TariffType tariffType = tariffTypeStr.equals(TariffType.TIME.getValue()) ? TariffType.TIME : TariffType.TRIP;

                    return new Tariff(rs.getLong("id"),
                            tariffType,
                            tariffType.equals(TariffType.TRIP) && rs.getInt("day_Count") == 0 ? null : rs.getInt("day_Count"),
                            tariffType.equals(TariffType.TIME) && rs.getInt("trips_Count") == 0 ? null : rs.getInt("trips_Count"))
                    ;
                }
        );

        if (result.isEmpty()) {
            return null;
        }
        return result.iterator().next();
    }

    @Override
    public Collection<Tariff> getAll() {
        Collection<Tariff> result = jdbcTemplate.query(
                "SELECT * FROM Tariff", (rs, rowNum) -> {
                    String tariffTypeStr = rs.getString("tariff_type");
                    TariffType tariffType = tariffTypeStr.equals(TariffType.TIME.getValue()) ? TariffType.TIME : TariffType.TRIP;

                    return new Tariff(rs.getLong("id"),
                            tariffType,
                            tariffType.equals(TariffType.TRIP) && rs.getInt("day_Count") == 0 ? null : rs.getInt("day_Count"),
                            tariffType.equals(TariffType.TIME) && rs.getInt("trips_Count") == 0 ? null : rs.getInt("trips_Count"))
                            ;
                }
        );
        return result;
    }
}
