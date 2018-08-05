package com.softmasters.dawuro.umid;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.softmasters.dawuro.umid.Gallery;

import static com.j256.ormlite.android.apptools.OrmLiteConfigUtil.writeConfigFile;



public class Main extends OrmLiteConfigUtil {
    
    private static final Class<?>[] classes = new Class[] {
            Gallery.class,Basicinformation.class,Businessinformation.class,Clientidentification.class,
            Comments.class,Contactinformation.class,Identification.class,Location.class,Message.class,
            Messaging.class,Relatives.class,ResponseEntity.class,ResponseMessages.class,Userbiometrics.class
            
    };
    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt",classes);
    }
}
