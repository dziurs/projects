package app;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class SampleRestAppApplicationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDatabaseConnection() throws SQLException {
        assertEquals(dataSource.getClass().getName(),"org.apache.tomcat.jdbc.pool.DataSource");
        assertNotNull(dataSource.getConnection("restUser","restPass"));
        assertEquals(dataSource.getDriverClassName(),"com.mysql.cj.jdbc.Driver");
        assertEquals(dataSource.getUrl(),"jdbc:mysql://localhost:3306/restdatabase?serverTimezone=UTC");
    }

}