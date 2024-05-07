package net.java.model;

public class User {
    protected String id;
    protected String email;
    protected String name;
    protected String password;
    protected boolean isAdmin=false;
    protected String address;
    protected String phoneNumber;
    protected String photoUrl="/default"; // New attribute for storing the URL of the user's photo

    public User(String id,String name, String email, String password, String address, String phoneNumber) {
        this.id = id;
        this.name=name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters for the id attribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getters and setters for the email attribute
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Getters and setters for the password attribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters and setters for the isAdmin attribute
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    // Getters and setters for the address attribute
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Getters and setters for the phoneNumber attribute
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getName() {
        return this.name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getters and setters for the photoUrl attribute
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
