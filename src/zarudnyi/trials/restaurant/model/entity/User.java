package zarudnyi.trials.restaurant.model.entity;


public class User {
    public static final Integer USER_ROLE_ADMIN = 1;


    private String fname;
    private String lname;
    private Integer id;
    private Integer role;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getFname() != null ? !getFname().equals(user.getFname()) : user.getFname() != null) return false;
        if (getLname() != null ? !getLname().equals(user.getLname()) : user.getLname() != null) return false;
        if (!getId().equals(user.getId())) return false;
        return !(getRole() != null ? !getRole().equals(user.getRole()) : user.getRole() != null);

    }

    @Override
    public int hashCode() {
        int result = getFname() != null ? getFname().hashCode() : 0;
        result = 31 * result + (getLname() != null ? getLname().hashCode() : 0);
        result = 31 * result + getId().hashCode();
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", id=" + id +
                ", role=" + role +
                '}';
    }
}
