package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Gallery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GalleryController {

    Context context;
    Dao<Gallery, Integer> galleryDao;

    public GalleryController(Context context, Dao<Gallery, Integer> galleryDao) {
        this.context = context;

        this.galleryDao = galleryDao;
    }

    public void saveOrUpdateGallery(String applicantid, String businessid, String latitude, String longitude,
                                    String macaddress, byte[] picture, String status, Date timestamp, String uniqueuid)
            throws SQLException {
        List<Gallery> galleries = new ArrayList<Gallery>();
        Gallery gallery = new Gallery();
        try {
            galleries = galleryDao.query(galleryDao.queryBuilder().where()
                    .eq("applicantid", applicantid).prepare());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (galleries.size() > 0) {
            gallery = galleries.get(0);
            updateGallery(gallery, applicantid, businessid, latitude, longitude, macaddress,
                    picture, status, timestamp, uniqueuid);
        } else {
            saveGallery(applicantid, businessid, latitude, longitude, macaddress, picture, status, timestamp, uniqueuid);
        }
    }

    private void saveGallery(String applicantid, String businessid, String latitude, String longitude,
                             String macaddress, byte[] picture, String status, Date timestamp, String uniqueuid)
            throws SQLException {
        Gallery gallery = new Gallery();
        gallery.setApplicantid(applicantid);
        gallery.setBusinessid(businessid);
        gallery.setLatitude(latitude);
        gallery.setLongitude(longitude);
        gallery.setMacaddress(macaddress);
        gallery.setPicture(picture);
        gallery.setStatus(status);
        gallery.setTimestamp(timestamp);
        gallery.setUniqueuid(uniqueuid);

        galleryDao.create(gallery);
    }

    private void updateGallery(Gallery gallery, String applicantid, String businessid, String latitude,
                               String longitude, String macaddress, byte[] picture, String status,
                               Date timestamp, String uniqueuid)
            throws SQLException {
        gallery.setApplicantid(applicantid);
        gallery.setBusinessid(businessid);
        gallery.setLatitude(latitude);
        gallery.setLongitude(longitude);
        gallery.setMacaddress(macaddress);
        gallery.setPicture(picture);
        gallery.setStatus(status);
        gallery.setTimestamp(timestamp);
        gallery.setUniqueuid(uniqueuid);

        galleryDao.update(gallery);
    }

    public void updateGallery(Gallery gallery, String status, String uniqueuid)
            throws SQLException {

        gallery.setStatus(status);
        gallery.setUniqueuid(uniqueuid);

        galleryDao.update(gallery);
    }

}
