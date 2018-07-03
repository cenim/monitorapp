package com.softmasters.dawuro;

/**
 * Created by Softmasters on 06-Sep-17.
 */

public class ImagePath {

    String imagePath;
    boolean video;

    public ImagePath(String imagePath, boolean video){
        this.imagePath = imagePath;
        this.video = video;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }
}
