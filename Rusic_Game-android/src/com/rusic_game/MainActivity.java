package com.rusic_game;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;

public class MainActivity extends AndroidApplication {
        
        private String[] musicpath = new String[1000];
        private String[] musicinfo = new String[1000];
        private boolean isAndroid = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Texture.setEnforcePotImages(false);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useGL20 = false;
        
        //Log path of the SD card where the music is
        Log.d("SD Card Path: ", Environment.getExternalStorageDirectory().getPath());
        
        //Log full path of songs on the SD card and point to/store song data
        ListAllSongs();
        
        initialize(new Rusic_Game(musicpath,musicinfo,isAndroid), cfg);
    }
    
    
    //Query the Media location URI to find all song data
    public void ListAllSongs()
    {
        Cursor cursor;
        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        
        String[] queryALL = {"*"};
        int i=0;
        
        if (sdCardExists()) {
            cursor = managedQuery(allsongsuri, queryALL, selection, null, null);

            Log.d("Msg: ", "Entering sd Card");
            
            if (cursor != null) {
                    Log.d("Msg: ", "managedQuery returned correctly");
                if (cursor.moveToFirst()) {
                        Log.d("Msg: ", "Songs were found in SD card, print info");
                    do {
                    	
                        String songTitle = cursor
                                .getString(cursor
                                        .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                        int songID = cursor.getInt(cursor
                                .getColumnIndex(MediaStore.Audio.Media._ID));

                        String fullpath = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.DATA));

                        Log.d("Song Path: ", fullpath.substring(17));
                        
                        String albumName = cursor.getString(cursor
                                .getColumnIndex(MediaStore.Audio.Media.ALBUM));
                        musicpath[i]=fullpath.substring(17);
                        String temp = songTitle+ " - "+ albumName;
                        if(temp.length() > 56) temp = temp.substring(1, 56);
                        musicinfo[i]= temp;
                        Log.e("MUSICINFO["+i+"]", musicinfo[i]);
                        i++;
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        }
    }


        public static boolean sdCardExists()
        {
                return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        }
}