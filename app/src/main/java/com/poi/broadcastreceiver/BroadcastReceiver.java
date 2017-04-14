package com.poi.broadcastreceiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Telephony;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.poi.broadcastreceiver.mysql.Conexion;
import com.poi.broadcastreceiver.util.CopyAssetDBUtility;

import java.io.File;
import java.util.ArrayList;


public class BroadcastReceiver extends Activity {

private static BroadcastReceiver inst;


public static final String STATICS_ROOT = Environment.getExternalStorageDirectory() + File.separator + "sisdb";

	ListView lv_list;
	private final static String DB_INDICATORS_NAME = "sisdb2.sqlite";
	ArrayList<String> list_1 = new ArrayList<String>();

	public static BroadcastReceiver instance() {
		return inst;
	}

	@Override
	public void onStart() {
		super.onStart();
		inst = this;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		CopyAssetDBUtility.copyDB(this, DB_INDICATORS_NAME);
		lv_list = (ListView) findViewById(R.id.listview1);
		lista();
		doBindService();
//		 txtphoneNo = (EditText) findViewById(R.id.editText1);
//	     txtMessage = (EditText) findViewById(R.id.editText2);

	}

	public void lista(){
		Conexion cnSET = new Conexion(getApplicationContext(),STATICS_ROOT + File.separator + "sisdb2.sqlite", null, 4);
		SQLiteDatabase dbSET = cnSET.getReadableDatabase();
		Cursor cur_data = dbSET.rawQuery("SELECT date, sis_sql from sisupdate", null);
		String col_id, col_g1;
		cur_data.moveToFirst();
		//TS_code = 0;
		list_1.clear();

		if (cur_data.moveToFirst()) {
			do {
				col_g1 = cur_data.getString(0) + ", " + cur_data.getString(1);

				//Toast.makeText(this,col_g1,Toast.LENGTH_LONG).show();
				list_1.add(col_g1);

			} while (cur_data.moveToNext());

			ArrayAdapter adap_list = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list_1);
			lv_list.setAdapter(adap_list);
		}

		lv_list.setSelection(list_1.size()-1);

	}

	@Override
	protected void onResume() {
		super.onResume();
		lista();
		final String myPackageName = getPackageName();

			// App is not default.
			// Show the "not currently set as the default SMS app" interface
			//View viewGroup = findViewById(R.id.not_default_app);
			//viewGroup.setVisibility(View.VISIBLE);

			// Set up a button that allows the user to change the default SMS app
			Button button = (Button) findViewById(R.id.change_default_app);
			Button button1 = (Button) findViewById(R.id.lista);
			button1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				lista();
			}
		});
			button.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					Intent intent =
							new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
					intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
							myPackageName);
					startActivity(intent);
				}
			});

			// App is the default.
			// Hide the "not currently set as the default SMS app" interface
			//View viewGroup = findViewById(R.id.not_default_app);
			//viewGroup.setVisibility(View.GONE);

	}
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};

	void doBindService(){
		bindService(new Intent(this,service_http.class),mConnection, Context.BIND_AUTO_CREATE);
	}

}
