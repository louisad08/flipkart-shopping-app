
package com.example.flipkart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button bSign, bClear;
	private EditText etUsername, etPassword;
	private SharedPreferences preference;
	private CheckBox ckKeepLogin;
	private String state, usr, pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		bSign = (Button) findViewById(R.id.bSign);
		bClear = (Button) findViewById(R.id.bClear);
		etUsername = (EditText) findViewById(R.id.etUsername);
		etPassword = (EditText) findViewById(R.id.etPassword);
		ckKeepLogin = (CheckBox) findViewById(R.id.ckKeepLogin);

		preference = getSharedPreferences("login_data", Context.MODE_PRIVATE);
		state = preference.getString("verify", "");
		usr = preference.getString("usr", "");
		pwd = preference.getString("pwd", "");
		

		if (state.equals("no")) {

		} else {
			ckKeepLogin.setChecked(true);
			// Toast.makeText(MainActivity.this, usr + " " +
			// pwd,Toast.LENGTH_LONG).show();
			Bundle bundle = new Bundle();
			bundle.putString("usr", usr);
			bundle.putString("pwd", pwd);
			Class ourClass;
			try {
				ourClass = Class.forName("com.example.flipkart.Activity_Three");
				Intent ourIntent = new Intent(MainActivity.this, ourClass);
				ourIntent.putExtras(bundle);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		bClear.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Class ourClass;
				try {
					ourClass = Class.forName("com.example.flipkart.Activity_Two");
					Intent ourIntent = new Intent(MainActivity.this, ourClass);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			
		});
		
		
		bSign.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				if (ckKeepLogin.isChecked()) {
					preference.edit().putString("verify", "yes").commit();
					preference.edit()
							.putString("usr", etUsername.getText().toString())
							.commit();
					preference.edit()
							.putString("pwd", etPassword.getText().toString())
							.commit();

				} else {

					preference.edit().putString("verify", "no").commit();

				}
				
				usr = etUsername.getText().toString();
				pwd = etPassword.getText().toString();
				if(usr.equals("Louis@gmail.com")&&pwd.equals("1234")){
					Bundle bundle = new Bundle();
					bundle.putString("usr", usr);
					bundle.putString("pwd", pwd);
					Class ourClass;
					try {
						ourClass = Class.forName("com.example.flipkart.Activity_Three");
						Intent ourIntent = new Intent(MainActivity.this, ourClass);
						ourIntent.putExtras(bundle);
						startActivity(ourIntent);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
								
				}else{
					Toast.makeText(MainActivity.this, "username and password have error!", Toast.LENGTH_LONG).show();
					etUsername.setText("");
					etPassword.setText("");
				}
			}

		});

	}
}
