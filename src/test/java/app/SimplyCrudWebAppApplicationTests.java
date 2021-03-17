package app;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SimplyCrudWebAppApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() {
    }


    @Test
    public void givenTomcatConnectionPoolInstance() {
        Assert.assertEquals(dataSource.getClass().getName(),"org.apache.tomcat.jdbc.pool.DataSource");
    }

}
