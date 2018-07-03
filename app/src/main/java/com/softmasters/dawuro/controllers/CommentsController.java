package com.softmasters.dawuro.controllers;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.umid.Comments;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Softmasters on 08-May-17.
 */

public class CommentsController {

    Context context;
    Dao<Comments, Integer> commentsDao;

    public CommentsController(Context context, Dao<Comments, Integer> commentsDao){
        this.context = context;
        this.commentsDao = commentsDao;
    }

    public void saveOrUpdateCommentsController(String applicantid, Date applieddate, String comment,
                                               String macaddress, String status, String uniqueuid) throws SQLException {
        List<Comments> commentsList = new ArrayList<>();
        Comments comments = new Comments();

        try{
            commentsList = commentsDao.query(commentsDao.queryBuilder().where().
                    eq("applicantid",applicantid).prepare());
        }catch (Exception e){
            e.printStackTrace();
        }

        if(commentsList.size() > 0){
            comments = commentsList.get(0);
            updateComments(comments, applicantid,applieddate,comment,macaddress,status, uniqueuid);
        }else{
            saveComments(applicantid,applieddate,comment,macaddress,status, uniqueuid);
        }

    }

    public void saveComments(String applicantid, Date applieddate, String comment, String macaddress, String status,
                             String uniqueuid) throws SQLException {
        Comments comments = new Comments();
        comments.setApplicantid(applicantid);
        comments.setApplieddate(applieddate);
        comments.setComment(comment);
        comments.setMacaddress(macaddress);
        comments.setStatus(status);
        comments.setUniqueuid(uniqueuid);

        commentsDao.create(comments);
    }

    public void updateComments(Comments comments, String applicantid, Date applieddate, String comment, String macaddress,
                               String status, String uniqueuid) throws SQLException {
        comments.setApplieddate(applieddate);
        comments.setComment(comment);
        comments.setMacaddress(macaddress);
        comments.setStatus(status);
        comments.setUniqueuid(uniqueuid);

        commentsDao.update(comments);
    }

    public void updateComment(Comments comments, String status, String uniqueuid) throws SQLException{
        comments.setStatus(status);
        comments.setUniqueuid(uniqueuid);

        commentsDao.update(comments);

        Log.d("","Updated ");
    }
}
