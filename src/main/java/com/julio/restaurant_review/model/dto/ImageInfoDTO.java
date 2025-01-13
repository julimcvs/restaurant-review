package com.julio.restaurant_review.model.dto;

public class ImageInfoDTO {
    private String filename;

    private String url;

    public ImageInfoDTO() {
    }

    public ImageInfoDTO(String filename, String url) {
        this.url = url;
        this.filename = filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilename() {
        return filename;
    }
}
