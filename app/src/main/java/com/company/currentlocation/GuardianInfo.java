package com.company.currentlocation;


public class GuardianInfo {

    private String guardianName1;
    private String guardianPhone1;
    private String guardianPhone2;
    private String friendPhone;

    public GuardianInfo(){}

    public GuardianInfo(String guardianName1, String guardianPhone1, String guardianPhone2, String friendPhone) {
        this.guardianName1 = guardianName1;
        this.guardianPhone1 = guardianPhone1;
        this.guardianPhone2 = guardianPhone2;
        this.friendPhone = friendPhone;

    }

    public GuardianInfo(String guardianName1, String guardianPhone1){
        this.guardianName1 = guardianName1;
        this.guardianPhone1 = guardianPhone1;
    }

    public String getGuardianName1() {
        return guardianName1;
    }

    public void setGuardianName1(String guardianName1) {
        this.guardianName1 = guardianName1;
    }

    public String getGuardianPhone1() {
        return guardianPhone1;
    }

    public void setGuardianPhone1(String guardianPhone1) {
        this.guardianPhone1 = guardianPhone1;
    }

    public String getGuardianPhone2() {
        return guardianPhone2;
    }

    public void setGuardianPhone2(String guardianPhone2) {
        this.guardianPhone2 = guardianPhone2;
    }

    public String getFriendPhone() {
        return friendPhone;
    }

    public void setFriendPhone(String friendPhone) {
        this.friendPhone = friendPhone;
    }
}
