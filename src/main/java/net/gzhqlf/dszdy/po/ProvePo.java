package net.gzhqlf.dszdy.po;

/**
 * Created by Destiny_hao on 2017/11/5.
 */
public class ProvePo {

    private FilePo identity;
    private FilePo education;
    private FilePo car;
    private FilePo house;
    private int identityStatus;
    private int educationStatus;
    private int carStatus;
    private int houseStatus;
    private String identityRefuse;
    private String educationRefuse;
    private String carRefuse;
    private String houseRefuse;

    public FilePo getIdentity() {
        return identity;
    }

    public void setIdentity(FilePo identity) {
        this.identity = identity;
    }

    public FilePo getEducation() {
        return education;
    }

    public void setEducation(FilePo education) {
        this.education = education;
    }

    public FilePo getCar() {
        return car;
    }

    public void setCar(FilePo car) {
        this.car = car;
    }

    public FilePo getHouse() {
        return house;
    }

    public void setHouse(FilePo house) {
        this.house = house;
    }

    public int getIdentityStatus() {
        return identityStatus;
    }

    public void setIdentityStatus(int identityStatus) {
        this.identityStatus = identityStatus;
    }

    public int getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(int educationStatus) {
        this.educationStatus = educationStatus;
    }

    public int getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(int carStatus) {
        this.carStatus = carStatus;
    }

    public int getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(int houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getIdentityRefuse() {
        return identityRefuse;
    }

    public void setIdentityRefuse(String identityRefuse) {
        this.identityRefuse = identityRefuse;
    }

    public String getEducationRefuse() {
        return educationRefuse;
    }

    public void setEducationRefuse(String educationRefuse) {
        this.educationRefuse = educationRefuse;
    }

    public String getCarRefuse() {
        return carRefuse;
    }

    public void setCarRefuse(String carRefuse) {
        this.carRefuse = carRefuse;
    }

    public String getHouseRefuse() {
        return houseRefuse;
    }

    public void setHouseRefuse(String houseRefuse) {
        this.houseRefuse = houseRefuse;
    }
}
