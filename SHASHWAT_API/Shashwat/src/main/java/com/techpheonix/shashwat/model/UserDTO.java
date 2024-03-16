package com.techpheonix.shashwat.model;

import java.io.Serializable;

public class UserDTO implements Serializable {

    private int id;
    private String fullname, username, email, mobile, password, gender, dob;
    private boolean block;

    public UserDTO(String fullname, String username, String email, String mobile, String password, String gender, String dob, boolean block) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.gender = gender;
        this.dob = dob;
        this.block = block;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public UserDTO() {
    }

    public UserDTO(String fullname, String username, String email, String mobile, String password, String gender, String dob) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.gender = gender;
        this.dob = dob;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

}
