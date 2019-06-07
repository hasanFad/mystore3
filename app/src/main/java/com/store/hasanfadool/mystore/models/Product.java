package com.store.hasanfadool.mystore.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Product extends ArrayList<String> implements Serializable {

    private String proName, proColor, compName, gender, proPic, proCode;
    private int proPrice, shipping, range , quantity;
    private double cheap;
    private boolean favoriteProduct;


            // for details
    public Product(String proCode,String productName, String productColor,
                   String companyName, String gender, int productPrice,
                  double productCheap, int shipping ,String productPicture) {
        this.proCode = proCode;
        this.compName = companyName;
        this.gender = gender;
        this.shipping = shipping;
        this.proColor = productColor;
        this.proName = productName;
        this.proPic = productPicture;
        this.proPrice = productPrice;
        this.cheap = productCheap;
    }

            // for main product list
    public Product(String productName, int productPrice, double productCheap,
                   int shipping, String productImage){
        this.cheap = productCheap;
        this.proName = productName;
        this.proPic = productImage;
        this.proPrice = productPrice;
        this.shipping = shipping;
    }

    public Product(String proCode, int range, int quantity){
        this.range = range;
        this.proCode = proCode;
        this.quantity = quantity;

    }

    public Product(String proCode){
        this.proCode = proCode;
    }

    public String getProPic() {
    return proPic;

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isFavoriteProduct() {
        return favoriteProduct;
    }

    public int getShipping() {
        return shipping;
    }

    public void setShipping(int shipping) {
        this.shipping = shipping;
    }

    public String getProName() {
        return proName;
    }


    public int getProPrice() {
        return proPrice;
    }

    public String getProColor() {
        return proColor;
    }

    public double getCheap() {
        return cheap;
    }

    public void setCheap(double cheap) {
        this.cheap = cheap;
    }

    public void setProColor(String proColor) {
        this.proColor = proColor;
    }

    public void setProPrice(int proPrice) {
        this.proPrice = proPrice;
    }

    public String getCompName() {
        return compName;
    }

    public String getGender() {
        return gender;
    }


    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public void setFavoriteProduct(boolean favoriteProduct) {
        this.favoriteProduct = favoriteProduct;
    }

    public int getRange() {
        return range;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setProName(String proName) {
        this.proName = proName;
    }

    public void setProPic(String proPic) {
        this.proPic = proPic;
    }




//    public Array getRange() {
//        return range;
//    }


    public void setRange(int range) {
        this.range = range;
    }





}
