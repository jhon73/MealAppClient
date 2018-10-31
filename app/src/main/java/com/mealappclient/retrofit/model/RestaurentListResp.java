package com.mealappclient.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurentListResp {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("image")
@Expose
private String image;
@SerializedName("imageContentType")
@Expose
private String imageContentType;
@SerializedName("resturentlocation")
@Expose
private Resturentlocation resturentlocation;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getImage() {
return image;
}

public void setImage(String image) {
this.image = image;
}

public String getImageContentType() {
return imageContentType;
}

public void setImageContentType(String imageContentType) {
this.imageContentType = imageContentType;
}

public Resturentlocation getResturentlocation() {
return resturentlocation;
}

public void setResturentlocation(Resturentlocation resturentlocation) {
this.resturentlocation = resturentlocation;
}

}