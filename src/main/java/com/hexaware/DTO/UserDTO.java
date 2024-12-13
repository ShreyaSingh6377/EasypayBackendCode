package com.hexaware.DTO;

public class UserDTO {
    
    private int userId;
    private String username;
    private String password;
    private String role;  

    // Constructors
    public UserDTO() {
    }

    public UserDTO(int userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString Method
    @Override
    public String toString() {
        return "UsersDTO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +  // Be cautious with displaying password
                ", role='" + role + '\'' +
                '}';
    }
}
