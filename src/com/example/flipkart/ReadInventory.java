package com.example.flipkart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;


public class ReadInventory extends Activity {
	private HashMap<String, String> inventory,incaritem;
	private SharedPreferences preference;
	private Bundle bundle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();    
		incaritem = (HashMap<String, String>) intent.getSerializableExtra("incaritem1");
		//Toast.makeText(ReadInventory.this, incaritem.get("cid0020001"), Toast.LENGTH_LONG).show();
		
		String state = Environment.getExternalStorageState();
		inventory=new HashMap<String, String>();
		if (!(state.equals(Environment.MEDIA_MOUNTED))) {
			Toast.makeText(this, "There is no any sd card", Toast.LENGTH_LONG)
					.show();

		} else {
			BufferedReader reader = null;
			try {
				Toast.makeText(this, "Sd card available", Toast.LENGTH_LONG)
						.show();
				File file = Environment.getExternalStorageDirectory();
				File textFile = new File(file.getAbsolutePath()
						+ File.separator + "Download/inventory1.txt");
				reader = new BufferedReader(new FileReader(textFile));
				StringBuilder textBuilder = new StringBuilder();

				String line;
				while ((line = reader.readLine()) != null) {
					textBuilder.append(line);
					textBuilder.append("\n");
					String[] tmp = line.split(",");
					//Toast.makeText(ReadInventory.this, incaritem.get("Diary of a Wimpy Kid : A Novel In Cartoons"), Toast.LENGTH_LONG).show();
					if(incaritem.get(tmp[0])!=null){
						Integer inven= Integer.valueOf(tmp[1]); 
						Integer buy= Integer.valueOf(incaritem.get(tmp[0]));
						inventory.put(tmp[0], toString().valueOf(inven-buy));
					}else{	
						inventory.put(tmp[0], tmp[1]);
					}	
				}

			} catch (FileNotFoundException e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		preference = getSharedPreferences("login_data", Context.MODE_PRIVATE);
		String usr = preference.getString("usr", "");
		String pwd = preference.getString("pwd", "");
		Bundle bundle = new Bundle();
		bundle.putString("usr", usr);
		bundle.putString("pwd", pwd);		
		/*String tmpInventory=toString().valueOf(Integer.valueOf(inventory.get("Diary of a Wimpy Kid : A Novel In Cartoons"))-1);
		inventory.remove("Diary of a Wimpy Kid : A Novel In Cartoons");
		inventory.put("Diary of a Wimpy Kid : A Novel In Cartoons", tmpInventory);*/
		
		File sdcard = Environment.getExternalStorageDirectory();
		
		File dir = new File(sdcard.getAbsolutePath() + "/download/");
		// create this directory if not already created
		dir.mkdir();
		// create the file in which we will write the contents
		File file = new File(dir, "inventory1.txt");
		FileOutputStream os=null;
		try {
			os = new FileOutputStream(file);
			for(String key : inventory.keySet()){
				String data = key+","+inventory.get(key).toString();
				data+="\n";
				os.write(data.getBytes());
			}
			
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Toast.makeText(ReadInventory.this,"CheckOut successful!!", Toast.LENGTH_LONG).show();
		Class ourClass;
		try {
			ourClass = Class.forName("com.example.flipkart.Activity_Three");
			Intent ourIntent = new Intent(ReadInventory.this, ourClass);
			ourIntent.putExtras(bundle);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
