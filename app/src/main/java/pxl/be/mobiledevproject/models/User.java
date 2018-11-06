package pxl.be.mobiledevproject.models;


public class User {

    private int id;

    private String name;
    private JarnacRole role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JarnacRole getRole() {
        return role;
    }

    public void setRole(JarnacRole role) {
        this.role = role;
    }
}
