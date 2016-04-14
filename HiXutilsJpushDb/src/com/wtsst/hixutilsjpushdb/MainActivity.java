package com.wtsst.hixutilsjpushdb;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.DbManager.DaoConfig;
import org.xutils.DbManager.DbUpgradeListener;
import org.xutils.x;
import org.xutils.ex.DbException;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	private EditText e1;
	private EditText e2;
	private Button b1;
	private Button b2;
	private ListView listView;
	static DbManager.DaoConfig daoConfig;
	private DbManager db;
	private List<ResponseUserModel> list;
	private static String[] arrays;

	private String[] query() {
		try {
			list = db.findAll(ResponseUserModel.class);
			Log.e("person", list.toString());
			List<String> listStr = new ArrayList<String>();
			for (ResponseUserModel u : list) {
				listStr.add(u.getUrl());
			}
			arrays = new String[listStr.size()];
			listStr.toArray(arrays);
		} catch (DbException e) {
			e.printStackTrace();
		}
		return arrays;
	}

	public static DaoConfig getDaoConfig() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath());
		if (daoConfig == null) {
			daoConfig = new DbManager.DaoConfig().setDbName("HiPushPic.db")
					.setDbVersion(1).setAllowTransaction(true)
					.setDbUpgradeListener(new DbUpgradeListener() {
						@Override
						public void onUpgrade(DbManager db, int oldVersion,
								int newVersion) {

						}
					});
		}
		return daoConfig;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		x.Ext.init(getApplication());
		x.Ext.setDebug(BuildConfig.DEBUG);
		daoConfig = getDaoConfig();
		db = x.getDb(daoConfig);
		e1 = (EditText) findViewById(R.id.et1);
		e2 = (EditText) findViewById(R.id.et2);
		b1 = (Button) findViewById(R.id.b1);
		b2 = (Button) findViewById(R.id.b2);
		listView = (ListView) findViewById(R.id.listView);
		b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String d1 = e1.getText().toString().trim();
				String d2 = e2.getText().toString().trim();
				if(d1.equals("")||d2.equals("")){
					Toast.makeText(getApplicationContext(), "數據不能为空",
							Toast.LENGTH_SHORT).show();
				}else{
					ResponseUserModel mod = new ResponseUserModel();
					mod.setName(d1);
					mod.setUrl(d2);
					try {
						db.saveOrUpdate(mod);
						Toast.makeText(getApplicationContext(), "添加數據成功",
								Toast.LENGTH_SHORT).show();
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String[] strs = query();
				if (strs != null) {
					listView.setAdapter(new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_list_item_1, strs));
				} else {
					Toast.makeText(getApplicationContext(), "数据库数据为空",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
