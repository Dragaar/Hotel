package com.rosivanyshyn.dao.entity;

import java.util.Objects;

public class Account {
    private int id;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    //відображає статус аккаунту (false - заблокований/ true - активний)
    private boolean state = true;

    private Account(int id, String role, String firstName, String lastName, String email) {
        this.id = id;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.state = state;
    }

    public static Account createAccount(int id, String role, String firstName, String lastName, String email){
        return new Account(id, role, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "Account{" +
                "role='" + role + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return role.equals(account.role) && firstName.equals(account.firstName) && lastName.equals(account.lastName) && email.equals(account.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, firstName, lastName, email);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
