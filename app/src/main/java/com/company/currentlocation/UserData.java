package com.company.currentlocation;

public class UserData {

    private String phone;
    private String fullname;
    private String age;
    private String gender;

    public UserData(String phone, String fullname, String age, String gender) {

        this.phone = phone;
        this.fullname = fullname;
        this.age = age;
        this.gender = gender;
    }

    public UserData(){}


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
