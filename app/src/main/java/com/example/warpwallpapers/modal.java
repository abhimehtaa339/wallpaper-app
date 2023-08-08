package com.example.warpwallpapers;

public class modal {
    private int id;
    private String orignalurl , mediamurl , photgrapher , photgrapher_id;

    public modal(int id, String orignalurl, String mediamurl, String photgrapher, String photgrapher_id) {
        this.id = id;
        this.orignalurl = orignalurl;
        this.mediamurl = mediamurl;
        this.photgrapher = photgrapher;
        this.photgrapher_id = photgrapher_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotgrapher() {
        return photgrapher;
    }

    public void setPhotgrapher(String photgrapher) {
        this.photgrapher = photgrapher;
    }

    public String getPhotgrapher_id() {
        return photgrapher_id;
    }

    public void setPhotgrapher_id(String photgrapher_id) {
        this.photgrapher_id = photgrapher_id;
    }

    public String getOrignalurl() {
        return orignalurl;
    }

    public void setOrignalurl(String orignalurl) {
        this.orignalurl = orignalurl;
    }

    public String getMediamurl() {
        return mediamurl;
    }

    public void setMediamurl(String mediamurl) {
        this.mediamurl = mediamurl;
    }
}
