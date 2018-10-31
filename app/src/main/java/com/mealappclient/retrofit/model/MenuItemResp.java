package com.mealappclient.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItemResp {

@SerializedName("description")
@Expose
private String description;
@SerializedName("id")
@Expose
private Integer id;
@SerializedName("imageurl")
@Expose
private String imageurl;
@SerializedName("imageurlContentType")
@Expose
private String imageurlContentType;
@SerializedName("itemname")
@Expose
private String itemname;
@SerializedName("price")
@Expose
private Integer price;

public String getDescription() {
return description;
}

public void setDescription(String description) {
this.description = description;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getImageurl() {
return imageurl;
}

public void setImageurl(String imageurl) {
this.imageurl = imageurl;
}

public String getImageurlContentType() {
return imageurlContentType;
}

public void setImageurlContentType(String imageurlContentType) {
this.imageurlContentType = imageurlContentType;
}

public String getItemname() {
return itemname;
}

public void setItemname(String itemname) {
this.itemname = itemname;
}

public Integer getPrice() {
return price;
}

public void setPrice(Integer price) {
this.price = price;
}

}