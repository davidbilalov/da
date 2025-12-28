
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static PostgreSQLContainer<?> postgresContainer;

    public static SessionFactory getSessionFactory(String jdbcUrl, String username, String password) {
        if (sessionFactory == null) {
            postgresContainer = new PostgreSQLContainer<>("postgres:15")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");
            postgresContainer.start();

            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.connection.url", postgresContainer.getJdbcUrl());
            configuration.setProperty("hibernate.connection.username", postgresContainer.getUsername());
            configuration.setProperty("hibernate.connection.password", postgresContainer.getPassword());
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");

            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) sessionFactory.close();
        if (postgresContainer != null) postgresContainer.stop();
    }
}
