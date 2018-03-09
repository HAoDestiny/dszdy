package net.gzhqlf.dszdy.po;

public class UserVerifyInfoPo {

    private int people = 0;
    private int identity = 0;
    private int education = 0;
    private int car = 0;
    private int house = 0;
    private String verifyLevel = "r";
    private int verifyPercent= 0;

    public int getPeople() {
        return people;
    }

    public void setPeople(int people) {
        this.people = people;
    }

    public int getIdentity() {
        return identity;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public int getEducation() {
        return education;
    }

    public void setEducation(int education) {
        this.education = education;
    }

    public int getCar() {
        return car;
    }

    public void setCar(int car) {
        this.car = car;
    }

    public int getHouse() {
        return house;
    }

    public void setHouse(int house) {
        this.house = house;
    }

    public String getVerifyLevel() {
        return verifyLevel;
    }

    public void setVerifyLevel(String verifyLevel) {
        this.verifyLevel = verifyLevel;
    }

    public int getVerifyPercent() {
        return verifyPercent;
    }

    public void setVerifyPercent(int verifyPercent) {
        this.verifyPercent = verifyPercent;
    }
}
