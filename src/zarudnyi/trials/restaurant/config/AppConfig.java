package zarudnyi.trials.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;


@Configuration
public class AppConfig {
    private DataSource dataSource = null;

    @Bean(name = "dataSource")
    public DataSource dataSource() {

        if (dataSource==null) {
            DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
            driverManagerDataSource.setUrl("jdbc:sqlite:D:\\restaurant.db");
            dataSource = driverManagerDataSource;
        }


        return dataSource;
    }

}
