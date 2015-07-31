package zarudnyi.trials.restaurant.model.entity;


public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer menuItemId;
    private String description;

     public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", menuItemId=" + menuItemId +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;

        OrderItem orderItem = (OrderItem) o;

        if (getId() != null ? !getId().equals(orderItem.getId()) : orderItem.getId() != null) return false;
        if (getOrderId() != null ? !getOrderId().equals(orderItem.getOrderId()) : orderItem.getOrderId() != null)
            return false;
        if (getMenuItemId() != null ? !getMenuItemId().equals(orderItem.getMenuItemId()) : orderItem.getMenuItemId() != null)
            return false;
        return !(getDescription() != null ? !getDescription().equals(orderItem.getDescription()) : orderItem.getDescription() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getOrderId() != null ? getOrderId().hashCode() : 0);
        result = 31 * result + (getMenuItemId() != null ? getMenuItemId().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
