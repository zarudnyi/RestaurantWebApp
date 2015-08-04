package zarudnyi.trials.restaurant.model.entity;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import static zarudnyi.trials.restaurant.model.dao.OrderDAO.*;


public class Order {
    private static final Map<Integer,String> statusMap = new HashMap<Integer, String>();


    static {
        statusMap.put(STATUS_RECEIVED,"Received");
        statusMap.put(STATUS_RECEIVED,"Received");
        statusMap.put(STATUS_IN_PROGRESS,"In Progress");
        statusMap.put(STATUS_COMPLETED,"Completed");
    }

    private Integer id;
    private Integer userId;
    private Integer groupId;
    private String description;
    private Integer checkOutSum;
    private Integer statusId;
    private Date orderDate;

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCheckOutSum() {
        return checkOutSum;
    }

    public void setCheckOutSum(Integer checkOutSum) {
        this.checkOutSum = checkOutSum;
    }

    public String getStatus(){
        return statusMap.get(getStatusId());
    }

    public boolean isGroupOrder(){
        return groupId==null;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (!getId().equals(order.getId())) return false;
        if (getUserId() != null ? !getUserId().equals(order.getUserId()) : order.getUserId() != null) return false;
        if (getGroupId() != null ? !getGroupId().equals(order.getGroupId()) : order.getGroupId() != null) return false;
        if (getDescription() != null ? !getDescription().equals(order.getDescription()) : order.getDescription() != null)
            return false;
        if (getCheckOutSum() != null ? !getCheckOutSum().equals(order.getCheckOutSum()) : order.getCheckOutSum() != null)
            return false;
        if (getStatusId() != null ? !getStatusId().equals(order.getStatusId()) : order.getStatusId() != null)
            return false;
        return !(getOrderDate() != null ? !getOrderDate().equals(order.getOrderDate()) : order.getOrderDate() != null);

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getGroupId() != null ? getGroupId().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getCheckOutSum() != null ? getCheckOutSum().hashCode() : 0);
        result = 31 * result + (getStatusId() != null ? getStatusId().hashCode() : 0);
        result = 31 * result + (getOrderDate() != null ? getOrderDate().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", groupId=" + groupId +
                ", description='" + description + '\'' +
                ", checkOutSum=" + checkOutSum +
                ", statusId=" + statusId +
                ", orderDate=" + orderDate +
                '}';
    }
}
