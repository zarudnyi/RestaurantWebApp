package zarudnyi.trials.restaurant.model.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import zarudnyi.trials.restaurant.model.dao.MenuDAO;
import zarudnyi.trials.restaurant.model.dao.RestaurantAppSQLiteDao;
import zarudnyi.trials.restaurant.model.dao.impl.rowmappers.MenuItemRowMapper;
import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;

import java.util.List;

@Repository("menuDao")
public class MenuSQLiteDAOImpl extends RestaurantAppSQLiteDao implements MenuDAO {
    public MenuItem createMenuItem() {
        Integer id = genericInsert("INSERT INTO menu( category_id, name, price, description) VALUES(NULL ,NULL ,NULL ,NULL ) ");
        return findItemById(id);
    }

    public void removeMenuItem(MenuItem menuItem) {
        jdbc.update("DELETE FROM  menu WHERE id=?",menuItem.getId());
    }

    public MenuItem findItemById(Integer id) {
        return jdbc.queryForObject("select * from menu join categories on categories.id = menu.category_id where menu.id=?",new Object[]{id}, new MenuItemRowMapper());
    }

    public List<MenuItem> findAllItems() {
        return jdbc.query("select * from menu join categories on categories.id = menu.category_id", new MenuItemRowMapper());
    }

    public void updateMenuItem(MenuItem menuItem) {
        jdbc.update("INSERT or replace INTO menu (id, category_id, name, price, description) VALUES (?,?,?,?,?)",
                menuItem.getId(),menuItem.getCategory().getId(),menuItem.getName(),menuItem.getPrice(),menuItem.getDescription());
    }

    public MenuCategory createCategory() {
        Integer id = genericInsert("INSERT INTO  categories ( name, description) VALUES (NULL ,NULL )");
        return findCategoryById(id);
    }

    public void removeCategory(MenuCategory category) {
        jdbc.update("DELETE FROM categories WHERE id=?",category.getId());

    }

    public MenuCategory findCategoryById(Integer id) {
        return jdbc.queryForObject("select * from categories where id=?", new Object[]{id},new BeanPropertyRowMapper<MenuCategory>(MenuCategory.class));
    }

    public List<MenuCategory> findAllCategories() {
        return jdbc.query("select * from categories", new BeanPropertyRowMapper<MenuCategory>(MenuCategory.class));
    }

    public void updateCategory(MenuCategory category) {
        jdbc.update("INSERT or replace INTO categories (id, name, description) VALUES (?,?,?)",category.getId(),category.getName(),category.getDescription());
    }
}
