package com.softmasters.dawuro.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.softmasters.dawuro.umid.Basicinformation;
import com.softmasters.dawuro.umid.Businessinformation;
import com.softmasters.dawuro.umid.Clientidentification;
import com.softmasters.dawuro.umid.Comments;
import com.softmasters.dawuro.umid.Contactinformation;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.umid.Identification;
import com.softmasters.dawuro.umid.Location;
import com.softmasters.dawuro.umid.MediaPath;
import com.softmasters.dawuro.umid.Messaging;
import com.softmasters.dawuro.umid.Relatives;
import com.softmasters.dawuro.umid.Userbiometrics;
import com.softmasters.dawuro.umid.Userfingerprint;

import java.sql.SQLException;

public class UMIDDatabaseHelper extends OrmLiteSqliteOpenHelper {

	static String DATABASE_NAME = "MonitorWatch.db";
	static int DATABASE_VERSION = 2;
	Dao<Basicinformation, Integer> basicinformationDao;
	Dao<Businessinformation, Integer> businessinformationDao;
	Dao<Clientidentification, Integer> clientidentificationDao;
	Dao<Contactinformation, Integer> contactinformationDao;
	Dao<Comments, Integer> commentsDao;
	Dao<Gallery, Integer> galleryDao;
	Dao<Identification, Integer> identificationDao = null;
	Dao<Location, Integer> locationDao;
	Dao<Messaging, Integer> messagingDao;
	Dao<Relatives, Integer> relativesDao;
	Dao<Userbiometrics, Integer> userBiometricsDao;
	Dao<Userfingerprint, Integer> userfingerprintDao;
	Dao<MediaPath,Integer> mediaPathDao;


	// SQLiteDatabase db = getWritableDatabase();

	public UMIDDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// onCreate(db);
	}

	public UMIDDatabaseHelper(Context context, String databaseName,
                              CursorFactory factory, int databaseVersion) {
		super(context, databaseName, factory, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Basicinformation.class);
			TableUtils.createTable(connectionSource, Businessinformation.class);
			TableUtils
					.createTable(connectionSource, Clientidentification.class);
			TableUtils.createTable(connectionSource, Contactinformation.class);
			TableUtils.createTable(connectionSource, Gallery.class);
			TableUtils.createTable(connectionSource, Identification.class);
			TableUtils.createTable(connectionSource, Location.class);
			TableUtils.createTable(connectionSource, Messaging.class);
			TableUtils.createTable(connectionSource, Relatives.class);
			TableUtils.createTable(connectionSource, Userbiometrics.class);
			TableUtils.createTable(connectionSource, Userfingerprint.class);
			TableUtils.createTable(connectionSource, Comments.class);
			TableUtils.createTable(connectionSource, MediaPath.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
		if(oldVersion < newVersion){
			try {
				db.execSQL("alter table gallery add column latitude text");
			} catch (Exception e) {
				e.printStackTrace();
			}


			try {
				db.execSQL("alter table gallery add column longitude text");
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				db.execSQL("alter table gallery add column timestamp text");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public Dao<Basicinformation, Integer> getBasicinformationDao()
			throws SQLException {
		if (basicinformationDao == null) {
			basicinformationDao = getDao(Basicinformation.class);
		}
		return basicinformationDao;
	}

	public Dao<Businessinformation, Integer> getBusinessinformationDao()
			throws SQLException {
		if (businessinformationDao == null) {
			businessinformationDao = getDao(Businessinformation.class);
		}
		return businessinformationDao;
	}

	public Dao<Contactinformation, Integer> getContactinformationDao()
			throws SQLException {
		if (contactinformationDao == null) {
			contactinformationDao = getDao(Contactinformation.class);
		}
		return contactinformationDao;
	}

	public Dao<Clientidentification, Integer> getClientidentificationDao()
			throws SQLException {
		if (clientidentificationDao == null) {
			clientidentificationDao = getDao(Clientidentification.class);
		}
		return clientidentificationDao;
	}

	public Dao<Gallery, Integer> getGalleryDao() throws SQLException {
		if (galleryDao == null) {
			galleryDao = getDao(Gallery.class);
		}
		return galleryDao;
	}

	public Dao<Identification, Integer> getIdentificationDao()
			throws SQLException {
		if (identificationDao == null) {
			identificationDao = getDao(Identification.class);
		}
		return identificationDao;
	}

	public Dao<Location, Integer> getLocationDao() throws SQLException {
		if (locationDao == null) {
			locationDao = getDao(Location.class);
		}
		return locationDao;
	}

	public Dao<Messaging, Integer> getMessagingDao() throws SQLException {
		if (messagingDao == null) {
			messagingDao = getDao(Messaging.class);
		}
		return messagingDao;
	}

	public Dao<Relatives, Integer> getRelativesDao() throws SQLException {
		if (relativesDao == null) {
			relativesDao = getDao(Relatives.class);
		}
		return relativesDao;
	}

	public Dao<Userbiometrics, Integer> getUserbiometricsDao()
			throws SQLException {
		if (userBiometricsDao == null) {
			userBiometricsDao = getDao(Userbiometrics.class);
		}
		return userBiometricsDao;
	}

	public Dao<Userfingerprint, Integer> getUserfingerprintDao()
			throws SQLException {
		if (userfingerprintDao == null) {
			userfingerprintDao = getDao(Userfingerprint.class);
		}
		return userfingerprintDao;
	}

	public Dao<Comments, Integer> getCommentsDao() throws SQLException{
		if(commentsDao == null){
			commentsDao = getDao(Comments.class);
		}
		return commentsDao;
	}

public Dao<MediaPath,Integer> getMediaPathDao() throws SQLException{
		if(mediaPathDao==null){
			mediaPathDao=getDao(MediaPath.class);
		}

		return mediaPathDao;
}

	@Override
	public void close() {
		super.close();
		try {
			commentsDao = null;
			basicinformationDao = null;
			businessinformationDao = null;
			clientidentificationDao = null;
			contactinformationDao = null;
			galleryDao = null;
			identificationDao = null;
			locationDao = null;
			messagingDao = null;
			relativesDao = null;
			userBiometricsDao = null;
			userfingerprintDao = null;
			mediaPathDao=null;
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
