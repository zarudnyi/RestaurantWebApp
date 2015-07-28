package zarudnyi.trials.restaurant.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


public abstract class RestaurantAppDAO extends JdbcDaoSupport {

    @Autowired
    @Qualifier("dataSource")
    protected DataSource dataSource;

    @PostConstruct
    private void init() {
        setDataSource(dataSource);
    }

}
