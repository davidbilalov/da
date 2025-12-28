import org.example.User;
import org.example.UserDao;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@Testcontainers
public class PostgresTest {

    @Container
    public static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static UserDao userDao;

    @BeforeAll
    static void setup() {
        HibernateUtil.getSessionFactory(
                postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword()
        );
        userDao = new UserDao();
    }

    @Test
    void testSaveAndGetById() {
        User user = new User();
        user.setName("TestUser");
        userDao.save(user);

        User fetched = userDao.getById(user.getId());
        assertNotNull(fetched);
        assertEquals("TestUser", fetched.getName());
    }

    @Test
    void testGetAll() {
        List<User> users = userDao.getAll();
        assertNotNull(users);
    }

    @Test
    void testUpdate() {
        User user = new User();
        user.setName("OldName");
        userDao.save(user);

        user.setName("NewName");
        userDao.update(user);

        User updated = userDao.getById(user.getId());
        assertEquals("NewName", updated.getName());
    }

    @Test
    void testDelete() {
        User user = new User();
        user.setName("ToDelete");
        userDao.save(user);

        userDao.delete(user.getId());
        User deleted = userDao.getById(user.getId());
        assertNull(deleted);
    }
}

