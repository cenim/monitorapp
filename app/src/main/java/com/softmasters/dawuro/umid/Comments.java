package com.softmasters.dawuro.umid;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Softmasters on 08-May-17.
 */

@DatabaseTable
public class Comments implements Serializable {

    @DatabaseField(generatedId = true)
    private int commentsid;
    @DatabaseField
    private String comment;
    @DatabaseField
    private String uniqueuid;
    @DatabaseField(dataType = DataType.DATE_STRING)
    private Date applieddate;
    @DatabaseField
    private String macaddress;
    @DatabaseField
    private String status;
    @DatabaseField
    private String applicantid;


    public int getCommentsid() {
        return commentsid;
    }

    public void setCommentsid(int commentsid) {
        this.commentsid = commentsid;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUniqueuid() {
        return uniqueuid;
    }

    public void setUniqueuid(String uniqueuid) {
        this.uniqueuid = uniqueuid;
    }

    public Date getApplieddate() {
        return applieddate;
    }

    public void setApplieddate(Date applieddate) {
        this.applieddate = applieddate;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplicantid() {
        return applicantid;
    }

    public void setApplicantid(String applicantid) {
        this.applicantid = applicantid;
    }
}
