package pl.lewandowski.raportgenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {

    @Id
    private Long id;

    private String username;
    private String userSalary;
    private String userDepartment;

    public User() {
    }

    public User(Long id, String username, String userSalary, String userDepartment) {
        this.id = id;
        this.username = username;
        this.userSalary = userSalary;
        this.userDepartment = userDepartment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserSalary() {
        return userSalary;
    }

    public void setUserSalary(String userSalary) {
        this.userSalary = userSalary;
    }

    public String getUserDepartment() {
        return userDepartment;
    }

    public void setUserDepartment(String userDepartment) {
        this.userDepartment = userDepartment;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userSalary='" + userSalary + '\'' +
                ", userDepartment='" + userDepartment + '\'' +
                '}';
    }
}
