package com.meass.student_job_salary;

public class UsernameModel {
    String username,usercode,fullname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public UsernameModel(String username, String usercode, String fullname) {
        this.username = username;
        this.usercode = usercode;
        this.fullname = fullname;
    }

    public UsernameModel() {
    }
}
