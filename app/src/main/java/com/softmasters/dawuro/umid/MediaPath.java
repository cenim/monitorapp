package com.softmasters.dawuro.umid;


import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

public class MediaPath implements Serializable {


    private static final long serialVersionUID = 1L;
    @DatabaseField(generatedId = true)
    private int galleryId;
    @DatabaseField
    private String applicantid;
    @DatabaseField
    private  String paths;

    public String getApplicantid() {
        return applicantid;
    }

    public void setApplicantid(String applicantid) {
        this.applicantid = applicantid;
    }


    public String getPaths() {
        return paths;
    }

    public void setPaths(String paths) {
        this.paths = paths;
    }
    @Override
    public String toString() {
        return "MediaPath{" +
                "galleryId=" + galleryId +
                ", applicantid='" + applicantid + '\'' +
                ", paths='" + paths + '\'' +
                '}';
    }


}
