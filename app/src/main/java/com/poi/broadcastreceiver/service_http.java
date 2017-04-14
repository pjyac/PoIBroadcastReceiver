package com.poi.broadcastreceiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.IBinder;

import com.poi.broadcastreceiver.mysql.Conexion;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by PJYAC on 11/04/2016.
 */
public class service_http extends Service{
    Context context = this;
    private Timer timer= new Timer();
    private static final long UPDATE_INTERVAL=300000;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
    private APIUtility apiUtility;
    ArrayList<String> list_g3 = new ArrayList<String>();
    ArrayList<String> list_g2 = new ArrayList<String>();
    @Override
    public void onCreate() {
        super.onCreate();
        httpsend();
    }

    private void httpsend(){
        apiUtility = new APIUtility();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Conexion cnGEO3 = new Conexion(getApplicationContext(), STATICS_ROOT + File.separator + "sisdb2.sqlite", null, 4);
                SQLiteDatabase dbGEO3 = cnGEO3.getWritableDatabase();
                Cursor cur_g3 = dbGEO3.rawQuery("SELECT id, sis_sql from sisupdate where flag = 1 order by id ", null);
                String col_g3;
                cur_g3.moveToFirst();
                if (cur_g3.moveToFirst()) {
                    do {

                        col_g3 = cur_g3.getString(1);
                        list_g3.add(col_g3);
                    } while (cur_g3.moveToNext());
                }
                cur_g3.close();
                cnGEO3.close();
                for (int a = 0; a < list_g3.size(); a++) {
                    String msglog = list_g3.get(a);
                    String msglog2=msglog.replaceAll("'","`");
                    apiUtility.saveData(msglog2,context);

                }

                list_g3.clear();
            }
        }, 0, UPDATE_INTERVAL);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
