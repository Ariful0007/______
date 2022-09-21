package com.meass.student_job_salary;

public class Userpackages {
    String name,usercode,packageammount,packagein,email;

    public Userpackages() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getPackageammount() {
        return packageammount;
    }

    public void setPackageammount(String packageammount) {
        this.packageammount = packageammount;
    }

    public String getPackagein() {
        return packagein;
    }

    public void setPackagein(String packagein) {
        this.packagein = packagein;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Userpackages(String name, String usercode,
                        String packageammount, String packagein, String email) {
        this.name = name;
        this.usercode = usercode;
        this.packageammount = packageammount;
        this.packagein = packagein;
        this.email = email;
    }
}
