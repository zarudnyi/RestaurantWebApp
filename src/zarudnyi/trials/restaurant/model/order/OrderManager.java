package zarudnyi.trials.restaurant.model.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Service;
import zarudnyi.trials.restaurant.model.RestaurantAppDAO;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Service("orderManager")
@Scope("singleton")
public class OrderManager  extends RestaurantAppDAO {


    public void test(){
        getJdbcTemplate().execute("SELECT 1");

    }



}
