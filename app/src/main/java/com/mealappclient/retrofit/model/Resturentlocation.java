package com.mealappclient.retrofit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resturentlocation {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("loclat")
@Expose
private Object loclat;
@SerializedName("loclong")
@Expose
private Object loclong;
@SerializedName("houseno")
@Expose
private Object houseno;
@SerializedName("landmark")
@Expose
private Object landmark;
@SerializedName("resturentid")
@Expose
private Object resturentid;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public Object getLoclat() {
return loclat;
}

public void setLoclat(Object loclat) {
this.loclat = loclat;
}

public Object getLoclong() {
return loclong;
}

public void setLoclong(Object loclong) {
this.loclong = loclong;
}

public Object getHouseno() {
return houseno;
}

public void setHouseno(Object houseno) {
this.houseno = houseno;
}

public Object getLandmark() {
return landmark;
}

public void setLandmark(Object landmark) {
this.landmark = landmark;
}

public Object getResturentid() {
return resturentid;
}

public void setResturentid(Object resturentid) {
this.resturentid = resturentid;
}

}