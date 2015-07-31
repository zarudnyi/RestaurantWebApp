package zarudnyi.trials.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.sqlite.SQLiteDataSource;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.services.impl.MenuService;
import zarudnyi.trials.restaurant.services.impl.OrderService;
import zarudnyi.trials.restaurant.services.impl.UserService;

import javax.sql.DataSource;


@Configuration
@ComponentScan
@Import({SecurityConfig.class,WebConfig.class})
public class AppConfig {


    public static void resetSchema() {
        DataSource ds = ApplicationContextProvider.getBean("dataSource", DataSource.class);
        JdbcTemplate jdbc = new JdbcTemplate(ds);

        jdbc.execute("DROP TABLE IF EXISTS users");
        jdbc.execute("CREATE TABLE IF NOT EXISTS users " +
                "(id INTEGER PRIMARY KEY," +
                "login TEXT UNIQUE," +
                "password TEXT," +
                "fname TEXT," +
                "lname TEXT," +
                "role INTEGER)");

        jdbc.update("INSERT INTO users (fname,lname,role,login, password) VALUES (?,?,?,?,?)", "Ivan", "Zarudnyi",1,"zarudnyi","{bcrypt}$2a$10$.Urwm2YffF5pW/MMXIJ5yeqV5RLxISUec2vxjA0psfnXgx.t4fVYO");
        jdbc.update("INSERT INTO users (fname,lname,login,password) VALUES (?,?,?,?)", "Natalia", "Sirobaba","sirobaba","{bcrypt}$2a$10$maqWEu.4/0YD1XKzl9f8ee1s66E/bxmBw.AkRhdXPEEoEF/Y7J4sS");


        jdbc.execute("DROP TABLE IF EXISTS groups");
        jdbc.execute("CREATE TABLE IF NOT EXISTS groups " +
                "(id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT)" );

        jdbc.update("INSERT INTO groups (name,description) VALUES (?,?)", "Awesome Party", "");


        jdbc.execute("DROP TABLE IF EXISTS order_statuses");
        jdbc.execute("CREATE TABLE IF NOT EXISTS order_statuses " +
                "(id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT)" );

        jdbc.update("INSERT INTO order_statuses (name,description) VALUES (?,?)", "Received", "");
        jdbc.update("INSERT INTO order_statuses (name,description) VALUES (?,?)", "In Progress", "");
        jdbc.update("INSERT INTO order_statuses (name,description) VALUES (?,?)", "Completed", "");



        jdbc.execute("DROP TABLE IF EXISTS orders");
        jdbc.execute("CREATE TABLE IF NOT EXISTS orders " +
                "(id INTEGER PRIMARY KEY," +
                "user_id INTEGER," +
                "group_id INTEGER," +
                "description TEXT," +
                "checkout_sum INTEGER," +
                "status_id INTEGER," +
                "order_date DATE," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (status_id) REFERENCES order_statuses(id)," +
                "FOREIGN KEY (group_id) REFERENCES groups(id))");



        jdbc.execute("DROP TABLE IF EXISTS user_group");
        jdbc.execute("CREATE TABLE IF NOT EXISTS user_group " +
                "(user_id INTEGER," +
                "group_id INTEGER," +
                "option INTEGER," +
                "FOREIGN KEY (user_id) REFERENCES users(id)," +
                "FOREIGN KEY (group_id) REFERENCES groups(id))");

        jdbc.update("INSERT INTO user_group (user_id,group_id, option) VALUES (?,?,?)", 1, 1, 1);
        jdbc.update("INSERT INTO user_group (user_id,group_id, option) VALUES (?,?,?)", 2, 1, 0);




        jdbc.execute("DROP TABLE IF EXISTS categories");
        jdbc.execute("CREATE TABLE IF NOT EXISTS categories " +
                "(id INTEGER PRIMARY KEY," +
                "name TEXT," +
                "description TEXT)");

        jdbc.update("INSERT INTO categories (name,description) VALUES (?,?)","Beverage","");
        jdbc.update("INSERT INTO categories (name,description) VALUES (?,?)","Dish","");



        jdbc.execute("DROP TABLE IF EXISTS menu");
        jdbc.execute("CREATE TABLE IF NOT EXISTS menu " +
                "(id INTEGER PRIMARY KEY," +
                "category_id INTEGER," +
                "name TEXT," +
                "price INTEGER," +
                "description TEXT," +
                "picture TEXT," +
                "FOREIGN KEY (category_id) REFERENCES categories(id))" );

        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",1,"Beer",10,"","Craft-Beer-300x188.jpeg");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Pasta",40,"","rice-pasta.jpg");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Sweet Heat Burger",1019,"This juicy burger is topped with cheddar cheese, sweet jalape?o relish, crispy jalape?os, lettuce, tomato and a chipotle drizzle.","SweetHeatBurger.png");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Chicken Kabob Salad",999,"Fresh mixed greens are topped with flame broiled marinated chicken tips, tomatoes, cucumbers, roasted red peppers, red onion and finished with feta cheese, Kalamata olives, fresh oregano and banana peppers. Served with creamy Mediterranean dressing.","ChixKabobSalad.png");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Sweet Potato Crusted Cod",1199,"A North Atlantic cod filet is crusted with our signature sweet potato crumbs then oven roasted to perfection. Served with potato and vegetable and a refreshing tropical fruit salsa.","SweetPotatoCrustedCod.png");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Sliced Sirloin",1199,"Steakhouse seasoned sirloin steak is flame broiled, then sliced and served with garlic red skin mashed potatoes topped with white cheddar cheese sauce and caramelized onions. Served with au jus for dipping and choice of vegetable.","SlicedSirloin.png");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Bruschetta Chicken",1079,"Two lemon rosemary marinated chicken breasts are flame broiled and topped with seasoned tomatoes, fresh basil and fresh mozzarella cheese then finished with a balsamic glaze and grilled asparagus. Served with rice.","bruschetta-chicken.png");
        jdbc.update("INSERT INTO menu (category_id,name,price,description,picture) VALUES (?,?,?,?,?)",2,"Chipotle Honey Mustard Turkey Wrap",959,"Sliced oven roasted turkey, crisp lettuce, tomatoes, smoked bacon and Monterey Jack and cheddar cheeses are drizzled with chipotle honey mustard sauce then wrapped up in a warm tortilla.","ChipotleTurkeyWrap.png");



        jdbc.execute("DROP TABLE IF EXISTS order_items");
        jdbc.execute("CREATE TABLE IF NOT EXISTS order_items " +
                "(id INTEGER PRIMARY KEY," +
                "order_id INTEGER," +
                "menu_item_id INTEGER," +
                "description TEXT," +
                "FOREIGN KEY (order_id) REFERENCES orders(id)," +
                "FOREIGN KEY (menu_item_id) REFERENCES menu(id))" );
    }

    @Bean
    public DataSource dataSource() {

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:D:\\restaurant.db");

        return sqLiteDataSource;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserService();
    }

    @Bean
    public MenuService menuService(){
        return new MenuService();
    }

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public OrderService orderService(){
        return new OrderService();
    }


}
