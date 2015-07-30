package zarudnyi.trials.restaurant.model.dao;


import zarudnyi.trials.restaurant.model.entity.MenuCategory;
import zarudnyi.trials.restaurant.model.entity.MenuItem;

import java.util.List;

public interface MenuDAO {

    MenuItem createMenuItem();

    void removeMenuItem(MenuItem menuItem);

    MenuItem findItemById (Integer id);

    List<MenuItem> findAllItems();

    void updateMenuItem(MenuItem menuItem);

    MenuCategory createCategory();

    void removeCategory (MenuCategory category);

    MenuCategory findCategoryById (Integer id);

    List<MenuCategory> findAllCategories();

    void updateCategory(MenuCategory category);

}
