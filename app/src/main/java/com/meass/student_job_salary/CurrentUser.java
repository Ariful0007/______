package com.meass.student_job_salary;

public class CurrentUser {
    String usernmae,email;

    public String getUsernmae() {
        return usernmae;
    }

    public void setUsernmae(String usernmae) {
        this.usernmae = usernmae;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CurrentUser(String usernmae, String email) {
        this.usernmae = usernmae;
        this.email = email;
    }

    public CurrentUser() {
    }
}
