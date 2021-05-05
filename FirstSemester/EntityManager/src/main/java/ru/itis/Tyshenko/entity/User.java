package ru.itis.Tyshenko.entity;


public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Boolean isWorker;

    public User() {
    }

    public User(Long id, String firstName, String lastName, boolean isWorker) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isWorker = isWorker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isWorker() {
        return isWorker;
    }

    public void setWorker(boolean worker) {
        isWorker = worker;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", isWorker=" + isWorker +
                '}';
    }
}
