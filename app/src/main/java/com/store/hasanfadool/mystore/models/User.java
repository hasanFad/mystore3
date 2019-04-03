package com.store.hasanfadool.mystore.models;

public class User {

    private String userName,userLName,userMail,userPhone,userCity,uStreet,  uPO_post, userPass, userImage;
    private int  uHomeNum, uPstelCode, mailAgree, smsAgree;


    public User(String userName, String userLName, String userMail, String userPhone, String userCity,
                String uStreet, int uHomeNum , int uPstelCode, String uPO_post, String userPass,
                int smsAgree, int mailAgree){

        this.userName = userName;
        this.userLName = userLName;
        this.userMail = userMail;
        this.userPhone = userPhone;
        this.userCity = userCity;
        this.uStreet = uStreet;
        this.uHomeNum = uHomeNum;
        this.uPstelCode = uPstelCode;
        this.uPO_post = uPO_post;
        this.userPass = userPass;
        this.smsAgree = smsAgree;
        this.mailAgree = mailAgree;
    }

    public User(String userMail, String userPass){
        this.userMail = userMail;
        this.userPass = userPass;
    }


    public String getUserCity() {
        return userCity;
    }

    public String getUserLName() {
        return userLName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getuStreet() {
        return uStreet;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserLName(String userLName) {
        this.userLName = userLName;
    }

    public String getuPO_post() {
        return uPO_post;
    }

    public int getMailAgree() {
        return mailAgree;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setuPO_post(String uPO_post) {
        this.uPO_post = uPO_post;
    }

    public int getuHomeNum() {
        return uHomeNum;
    }

    public void setuStreet(String uStreet) {
        this.uStreet = uStreet;
    }

    public int getSmsAgree() {
        return smsAgree;
    }

    public void setuHomeNum(int uHomeNum) {
        this.uHomeNum = uHomeNum;
    }

    public int getuPstelCode() {
        return uPstelCode;
    }

    public void setMailAgree(int mailAgree) {
        this.mailAgree = mailAgree;
    }

    public void setSmsAgree(int smsAgree) {
        this.smsAgree = smsAgree;
    }

    public void setuPstelCode(int uPstelCode) {
        this.uPstelCode = uPstelCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


}
