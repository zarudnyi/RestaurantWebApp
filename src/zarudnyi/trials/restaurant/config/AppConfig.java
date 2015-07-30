package zarudnyi.trials.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.sqlite.SQLiteDataSource;
import zarudnyi.trials.restaurant.services.impl.UserService;

import javax.sql.DataSource;


@Configuration
@ComponentScan({"zarudnyi.trials.restaurant.model.dao.impl", "zarudnyi.trials.restaurant.services"})
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

        jdbc.update("INSERT INTO users (fname,lname,role,login) VALUES (?,?,?,?)", "Ivan", "Zarudnyi",1,"zarudnyi");
        jdbc.update("INSERT INTO users (fname,lname,login) VALUES (?,?,?)", "Natalia", "Sirobaba","sirobaba");


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
                "FOREIGN KEY (category_id) REFERENCES categories(id))" );

        jdbc.update("INSERT INTO menu (category_id,name,price,description) VALUES (?,?,?,?)",1,"Beer",10,"");
        jdbc.update("INSERT INTO menu (category_id,name,price,description) VALUES (?,?,?,?)",2,"Pasta",40,"");



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



}
