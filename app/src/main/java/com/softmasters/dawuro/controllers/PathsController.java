package com.softmasters.dawuro.controllers;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.MediaPath;

import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;

public class PathsController {
    Context context;
    Dao<MediaPath, Integer> mediaPathsDao;

    public PathsController(Context context, Dao<MediaPath, Integer> mediaPathsDao) {
        this.context = context;
        this.mediaPathsDao = mediaPathsDao;
    }


    public void savePaths(String applicantid, String path ){
        List<MediaPath> mediaPaths;
        try {
            mediaPaths = mediaPathsDao.query(mediaPathsDao.queryBuilder().where()
                    .eq("applicantid", applicantid).prepare());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MediaPath mediaPath=new MediaPath();
        mediaPath.setApplicantid(applicantid);
        mediaPath.setPaths(path);
        try {
            mediaPathsDao.create(mediaPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
