package com.poi.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.telephony.SmsMessage;
import android.util.Log;

import com.poi.broadcastreceiver.mysql.Conexion;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;


public class ReceptorSMS extends BroadcastReceiver{
    public static String idMensaje, textoMensaje;
    String attendance[]= {"date","emis","ts","t_present","charge","shift","level","grade","section","subject","present","absent"};
    String enrollment[]= {"year","emis","shift","level","grade","studentage","total","male","female"};
    String repeaters[]= {"year","emis","shift","level","grade","studentage","total","male","female"};
    String disability[]= {"year","emis","shift","level","grade","sex","total","vision","hearing","phisical","handicap"};
    String base_a[]={"a1","a2","a3a","a3b","a3c","a3d","a4a","a4b","a5a","a5b","b1","b2","b3","b4","lon","lat","flag"};
    String base_d[]={"a1","d1a","d1b","d1c","d1d","d1e","d1f","d2a","d2b","d2c","d2d","d2e","d2f","d2g","d24","flag"};
    String base_f[] ={"a1","f1a","f1b","f2a1","f2a2","f2a3","f2a4","f2a5","f2a6","f2a7","f2a8","f2a9","f2a10","f2a11","f2a12","f2a13","f2a14","f2a15","f2a16","f2a17","f2a18","f2a19",
            "f2a20","f2a21","f2a22","f2a23","f2a24","f2a25","f2a26","f2a27","f2a28","f2a29","f2a30","f2a31","f2a32","f2a33","f2a34","f2a35","f2a36","f2a37","f2a38","f2a39","f2a40",
            "f2a41","f2a42","f2a43","f2a44", "f2a45","f2a46","f2a47","f2a48","f2a49","f2a50","f2a51","f2a52","f2a53","f2a54","f2a55","f2a56","f2a57","f2a58",
            "f2a59","f2a60","f2a61","f2a62","f2a63","f2a64","f2a65","f2a66","f2a67","f2a68","f2a69","f2a70","f2a71","f2a72","f2a73","f2a74","f2a75","f2a76","f2a77","f2a78","f2a79","f2a80","f2a81","f2a82",
            "f2a83","f2a84","f2a85","f2a86","f2a87","f2a88","f2a89","f2a90","f2a91","f2a92","f2a93","f2a94","f2a95","f2a96","f2a97","f2a98","f2a99","f2a100","f2a101","f2a102","f2a103","f2a104","f2a105",
            "f2a106","f2a107","f2a108","f2a109","f2a110","f2a111","f2a112","f2a113","f2a114","f2a115","f2a116","f2a117","f2a118","f2a119","f2a120","f2a121","f2a122","f2a123","f2a124","f2a125","f2a126",
            "f2b1","f2b2","f2b3","f2b4","f2b5","f2c1","f2c2","f2c3","f2c4","f2c5","f2d","flag"};
    String base_g[] = {"a1","g1a", "g1b", "g1c", "g1d", "g1e", "g1f", "g2a", "g2b", "g2c", "g2d", "g2e", "g2f", "g2g", "g24", "flag"};
    String base_i[]={"a1","i1a","i1b","i2a","i3","i41","i42","i43","i44","i45","i46","i47","i48","i49",
            "i410","i411","i412","i413","i414","i415","i416","i417","i418","i419","i420","i421","i422",
            "i423","i424","i425","i426","i427","i428","i429","i430","i431","i432","flag"};
    String base_if1[]={"a1","_1","_2","_3","_4","_5","_6","_7","_8","_9","_10","_11","_12","_13","_14","_15","_16","_17","_18","_19","_20","_21","_22","_23","_24","_25","_26","_27","_28","_29","_30",
            "_31","_32","_33","_34","_35","_36","_37","_38","_39","_40","_41","_42","_43","_44","_45","_46","_47","_48","_49","_50","_51","_52","_53","_54","_55","_56","_57","_58","_59",
            "_60","_61","_62","_63","_64","flag"};
    String base_if2[]={"a1","_1","_2","_3","_4","_5","_6","_7","_8","_9","_10","_11","_12","_13","_14","_15","_16","_17","_18","_19","_20","_21","_22","_23","_24","_25","_26","_27","_28","_29","_30",
            "_31","_32","_33","_34","_35","_36","_37","_38","_39","_40","_41","_42","_43","_44","_45","_46","_47","_48","_49","_50","_51","_52","_53","_54","_55","_56","_57","_58","_59",
            "_60","_61","_62","_63","_64","flag"};
    String base_if3[]={"a1","_1","_2","_3","_4","_5","_6","_7","_8","_9","_10","_11","_12","_13","_14","_15","_16","_17","_18","_19","_20","_21","_22","_23","_24","_25","_26","_27","_28","_29","_30",
            "_31","_32","_33","_34","_35","_36","_37","_38","_39","_40","_41","_42","_43","_44","_45","_46","_47","_48","_49","_50","_51","_52","_53","_54","_55","_56","_57","_58","_59",
            "_60","_61","_62","_63","_64","flag"};
    String base_if4[]={"a1","_1","_2","_3","_4","_5","_6","_7","_8","_9","_10","_11","_12","_13","_14","_15","_16","_17","_18","_19","_20","_21","_22","_23","_24","_25","_26","_27","_28","_29","_30",
            "_31","_32","_33","_34","_35","_36","_37","_38","_39","_40","_41","_42","_43","_44","_45","_46","_47","_48","_49","_50","_51","_52","_53","_54","_55","_56","_57","_58","_59",
            "_60","_61","_62","_63","_64","flag"};
    String base_if5[]={"a1","_1","_2","_3","_4","_5","_6","_7","_8","_9","_10","_11","_12","_13","_14","_15","_16","_17","_18","_19","_20","_21","_22","_23","_24","_25","_26","_27","_28","_29","_30",
            "_31","_32","_33","_34","_35","_36","_37","_38","_39","_40","_41","_42","_43","_44","_45","_46","_47","_48","_49","_50","_51","_52","_53","_54","_55","_56","_57","_58","_59",
            "_60","_61","_62","_63","_64","flag"};
    String base_if6[]={"a1","_1","_2","_3","_4","_5","_6","_7","_8","_9","_10","_11","_12","_13","_14","_15","_16","_17","_18","_19","_20","_21","_22","_23","_24","_25","_26","_27","_28","_29","_30",
            "_31","_32","_33","_34","_35","_36","_37","_38","_39","_40","_41","_42","_43","_44","_45","_46","_47","_48","_49","_50","_51","_52","_53","_54","_55","_56","_57","_58","_59",
            "_60","_61","_62","_63","_64","flag"};
    String base_j[]={"a1","j1a","j1b","j1c","j1d","j1e","j1f","j2a","j2b","j2c","j2d","flag"};
    //String base_ms[]={"a1","m_pp","m_p","m_s","a_pp","a_p","a_s","e_pp","e_p","e_s","flag"};
    String base_q[]={"a1","q1a","q1b","q1c","q1d","q1e","q2a","q2b","q2c","q2d","q2e","q3a","q3b","q3c","q3d","q3e1","q3e2","q3e3","q3e4","q3e5","q3e6","q3f1","q3f2","q3f3","q3f4","q3f5","q3f6",
            "q3f7","q3f8","q3f9","q3f10","q3f11","q3f12","q3f13","q3f14","q3f15","q3f16","q3f17","q3f18","q3f19","q3f20","q3f21","q3f22","q3g1","q3g2","q3g3","q3g4","flag"};
    String base_r[]={"a1" ,"r1a1" ,"r1a2" ,"r1b1" ,"r1b2" ,"r1c1" ,"r1c2" ,"r1d1" ,"r1d2" ,"r1e1" ,"r1e2" ,"r1f1" ,"r1f2" ,"r1g1" ,"r1g2" ,"r2a1" ,"r2a2" ,"r2a3" ,"r2b1" ,"r2b2" ,"r2b3" ,"r2c1" ,
            "r2c2" ,"r2c3" ,"r2d1" ,"r2d2" ,"r2d3" ,"r2e1" ,"r2e2" ,"r2e3" ,"r2e4" ,"r2f1" ,"r2f2" ,"r2f3" ,"r2f4" ,"r3a1" ,"r3a2" ,"r3a3" ,"r3b1" ,"r3b2" ,"r3b3" ,"r3c1" ,"r3c2" ,"r3c3" ,"r3d1" ,"r3d2" ,
            "r3d3" ,"r3e1" ,"r3e2" ,"r3e3" ,"flag"};
    String base_s[]={"a1","s1a","s1b","s1c","s1d","s2a","s2b","s2c","s2d", "flag"};
    String teacher[] = {"a1","_id","t_s","surname","givenname","sex","yearob","checkno","cp1","cp2","cp3","cp4","cp5","cp6","lt1","lt2","lt3","lt4","lt5","prof_q","acad_q"," sub_t1",
            "sub_t2","sub_t3","sub_t4","sub_t5","year_pos","salary","phone","email","addrs"};
    String student[]= {"a1","_id","surname","givenname","sex","yearob","phone","email","addrs"};
    String _sa[] = {"a1","sc","shift","level","grade","section","subject_assigned","year_ta"};
    String _ta[] = {"a1","tc","shift","level","grade","section","subject","subject_assigned","year_ta"};

    private APIUtility apiUtility;
    public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";
   // public static final String STATICS_ROOT_DB = STATICS_ROOT + File.separator + "sisdb2.sqlite";
    SQLiteDatabase dbSET;
    Conexion cnSET;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        apiUtility = new APIUtility();
        cnSET = new Conexion(context,STATICS_ROOT + File.separator + "sisdb2.sqlite", null, 4);
        dbSET = cnSET.getReadableDatabase();
        Bundle b = intent.getExtras();

        if (b != null) {
            Object[] pdus = (Object[]) b.get("pdus");

            SmsMessage[] mensajes = new SmsMessage[pdus.length];

            for (int i = 0; i < mensajes.length; i++) {
                mensajes[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                idMensaje = mensajes[i].getOriginatingAddress();
                if (i>0){
                    textoMensaje = textoMensaje + mensajes[i].getMessageBody();



                }
                else{
                    textoMensaje = mensajes[i].getMessageBody();


                }

                if (i == mensajes.length-1)
                {

                      graba_datos(textoMensaje);

                      apiUtility.saveData(textoMensaje, context);


                    Log.i("ReceptorSMS", "Mensaje: " + textoMensaje);

                }




            }


        }

    }



    public void graba_bitacora(String datos){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        ContentValues Bitacora = new ContentValues();
        Bitacora.put("date",strDate);
        Bitacora.put("sis_sql", datos);
        dbSET.insert("sisupdate", null, Bitacora);
        com.poi.broadcastreceiver.BroadcastReceiver inst = com.poi.broadcastreceiver.BroadcastReceiver.instance();
        inst.lista();
    }

    public void graba_datos (String datos)
    {
        String items[] = datos.split("%");


        if (items[0].equals("a")){
            graba_bitacora(datos);
                ContentValues sql = new ContentValues();

                for(int x=0;x<base_a.length-1;x++){

                        sql.put(base_a[x], items[x + 1]);


                }
                sql.put("flag", items[items.length-1]);
                if (items[items.length-1].equals("I")){
                    dbSET.insert("a", null, sql);
                }
                else
                {
                    dbSET.update("a",sql, "a1="+items[1], null);
                }
        }
        else if (items[0].equals("d")){
            graba_bitacora(datos);
                ContentValues sql = new ContentValues();

                for(int x=0;x<base_d.length-1;x++){
                    sql.put(base_d[x],items[x+1]);
                }
                sql.put("flag", items[items.length-1]);
                if (items[items.length-1].equals("I")){
                    dbSET.insert("d", null, sql);
                }
                else
                {
                    dbSET.update("d",sql, "a1="+items[1], null);
                }
        }
        else if(items[0].equals("f")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_f.length-1;x++){
                sql.put(base_f[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("f", null, sql);
            }
            else
            {
                dbSET.update("f",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("g")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_g.length-1;x++){
                sql.put(base_g[x],items[x+1]);
            }
            sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("g", null, sql);
            }
            else
            {
                dbSET.update("g",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("i")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_i.length-1;x++){
                sql.put(base_i[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("i", null, sql);
            }
            else
            {
                dbSET.update("i",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("if1")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_if1.length-1;x++){
                sql.put(base_if1[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("if1", null, sql);
            }
            else
            {
                dbSET.update("if1",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("if2")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_if2.length-1;x++){
                sql.put(base_if2[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("if2", null, sql);
            }
            else
            {
                dbSET.update("if2",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("if3")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_if3.length-1;x++){
                sql.put(base_if3[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("if3", null, sql);
            }
            else
            {
                dbSET.update("if3",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("if4")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_if4.length-1;x++){
                sql.put(base_if4[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("if4", null, sql);
            }
            else
            {
                dbSET.update("if4",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("if5")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_if5.length-1;x++){
                sql.put(base_if5[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("if5", null, sql);
            }
            else
            {
                dbSET.update("if5",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("if6")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_if6.length-1;x++){
                sql.put(base_if6[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("if6", null, sql);
            }
            else
            {
                dbSET.update("if6",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("j")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_j.length-1;x++){
                sql.put(base_j[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("j", null, sql);
            }
            else
            {
                dbSET.update("j",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("q")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_q.length-1;x++){
                sql.put(base_q[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("q", null, sql);
            }
            else
            {
                dbSET.update("q",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("r")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_r.length-1;x++){
                sql.put(base_r[x],items[x+1]);
            }
            sql.put("flag", items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("r", null, sql);
            }
            else
            {
                dbSET.update("r",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("s")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<base_s.length-1;x++){
                sql.put(base_s[x],items[x+1]);
            }
            sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("s", null, sql);
            }
            else
            {
                dbSET.update("s",sql, "a1="+items[1], null);
            }
        }
        else if(items[0].equals("teacher")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<teacher.length-1;x++){
                sql.put(teacher[x],items[x+1]);
            }
            sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("teacher", null, sql);
            }
            else
            {
                dbSET.update("teacher",sql, "a1="+items[1]+" and _id="+items[2], null);
            }
        }
        else if(items[0].equals("attendance")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<attendance.length-1;x++){
                sql.put(attendance[x],items[x+1]);
            }
            sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("attendance", null, sql);
            }
            else
            {
                dbSET.update("attendance",sql, "date="+items[1]+" and emis="+items[2]+" and ts="+items[3]+" and shift="+items[4]+" and level="+items[5]+" and grade="+items[6]+" and section="+items[7]+" and subject="+items[8], null);
            }
        }
        else if(items[0].equals("enrollment")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<enrollment.length-1;x++){
                sql.put(enrollment[x],items[x+1]);
            }
            //sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("enrollment", null, sql);
            }
            else
            {
                dbSET.delete("enrollment","year = "+items[1]+" and emis="+items[2],null);
                //dbSET.update("enrollment",sql, "date="+items[1]+" and emis="+items[2]+" and ts="+items[3]+" and shift="+items[4]+" and level="+items[5]+" and grade="+items[6]+" and section="+items[7]+" and subject="+items[8], null);
            }
        }
        else if(items[0].equals("repeater")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<repeaters.length-1;x++){
                sql.put(repeaters[x],items[x+1]);
            }
            //sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("repeater", null, sql);
            }
            else
            {
                dbSET.delete("repeater","year = "+items[1]+" and emis="+items[2],null);
                //dbSET.update("enrollment",sql, "date="+items[1]+" and emis="+items[2]+" and ts="+items[3]+" and shift="+items[4]+" and level="+items[5]+" and grade="+items[6]+" and section="+items[7]+" and subject="+items[8], null);
            }
        }
        else if(items[0].equals("disability")){
            graba_bitacora(datos);
            ContentValues sql = new ContentValues();

            for(int x=0;x<disability.length-1;x++){
                sql.put(disability[x],items[x+1]);
            }
            //sql.put("flag",items[items.length-1]);
            if (items[items.length-1].equals("I")){
                dbSET.insert("disability", null, sql);
            }
            else
            {
                dbSET.delete("disability","year = "+items[1]+" and emis="+items[2],null);
                //dbSET.update("enrollment",sql, "date="+items[1]+" and emis="+items[2]+" and ts="+items[3]+" and shift="+items[4]+" and level="+items[5]+" and grade="+items[6]+" and section="+items[7]+" and subject="+items[8], null);
            }
        }
//        else if(items[0].equals("student")){
//            graba_bitacora(datos);
//            ContentValues sql = new ContentValues();
//
//            for(int x=0;x<student.length-1;x++){
//                sql.put(student[x],items[x+1]);
//            }
//            sql.put("flag",items[items.length-1]);
//            if (items[items.length-1].equals("I")){
//                dbSET.insert("student", null, sql);
//            }
//            else
//            {
//                dbSET.update("student",sql, "a1="+items[1], null);
//            }
//        }
        else {


            Arrays.fill(items, null);

        }

    }
}
