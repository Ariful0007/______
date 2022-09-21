package com.meass.student_job_salary;

import java.io.Serializable;

public class Help_Model implements Serializable {
    String name,area,phone,uuid,time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Help_Model(String name, String area, String phone, String uuid, String time) {
        this.name = name;
        this.area = area;
        this.phone = phone;
        this.uuid = uuid;
        this.time = time;
    }

    public Help_Model() {
    }
}
