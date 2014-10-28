package com.example.flipkart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Two extends Activity implements OnClickListener {
	private EditText etname, etemail, etpassword, etcnpassword;
	private Button bsign, bclear;	
	private SharedPreferences preference;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_two);
		etname = (EditText)findViewById(R.id.etName);
		etemail = (EditText)findViewById(R.id.etEmail);
		etpassword = (EditText)findViewById(R.id.etPassword);
		etcnpassword = (EditText)findViewById(R.id.etCNPassword);
		bsign = (Button)findViewById(R.id.bSign);
		bclear = (Button)findViewById(R.id.bClear);
		bsign.setOnClickListener(this);
		bclear.setOnClickListener(this);
		preference = getSharedPreferences("login_data", Context.MODE_PRIVATE);
	
	}

	public void savePref(){
		
		preference.edit().putString("usr", etname.getText().toString()).commit();
		preference.edit().putString("pwd", etpassword.getText().toString()).commit();
		preference.edit().putString("email", etemail.getText().toString()).commit();
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bSign:
			if(etpassword.getText().toString().equals(etcnpassword.getText().toString())){
				savePref();
				Bundle bundle = new Bundle();
				bundle.putString("usr", etname.getText().toString());
				bundle.putString("pwd", etpassword.getText().toString());
				
				Class ourClass;
				try {
					ourClass = Class.forName("com.example.flipkart.Activity_Three");
					Intent ourIntent = new Intent(Activity_Two.this, ourClass);
					ourIntent.putExtras(bundle);
					startActivity(ourIntent);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				Toast.makeText(Activity_Two.this, "Password error", Toast.LENGTH_LONG).show();
			}
			
			break;
		case R.id.bClear:
			etname.setText("");
			etemail.setText("");
			etpassword.setText("");
			etcnpassword.setText("");			
			break;
		}
		
	}

}
