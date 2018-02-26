package metro.ticketing.app.utils;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Created by Dmitry on 15.01.2018.
 */
public class DataBaseUtils {

    public static final String DRIVER = "org.postgresql.Driver";
    public static final String JDBC_URL = "jdbc:postgresql://localhost/ais_db";
    public static final String USERNAME = "postgres";
    public static final String PASSWORD = "huevokukuevo1488";

    private static DriverManagerDataSource dataSource;

    public static DataSource getDataSource() {

        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DRIVER);
        dataSource.setUrl(JDBC_URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
}
