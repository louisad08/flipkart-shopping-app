package com.example.flipkart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Activity_Three extends Activity implements OnItemSelectedListener, OnClickListener, OnItemClickListener {
	private static final String SERVER_URL = "http://www.json-generator.com/api/json/get/cvcfBLkXyq?indent=2";
	private static final String SERVER_URL1 = "http://www.json-generator.com/api/json/get/crZdoFkeYy?indent=2";
	
	private ProgressBar progressBar;
	private ArrayAdapter <String> booksAdapter;
	private ArrayAdapter <String> booksIDAdapter;
	private Button bsignout, bcheckout;
	private Spinner spinner;
	private ListView booksList;
	private ArrayList<Book> books;
	private BookAdapter bookAdapter;
	private String forSpinner="true";
	private SharedPreferences preference;
	private String usr,pwd;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_three);
		bsignout = (Button)findViewById(R.id.bsignout);
		bcheckout = (Button)findViewById(R.id.bcheckout);
		preference = getSharedPreferences("login_data", Context.MODE_PRIVATE);
		
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		spinner=(Spinner)findViewById(R.id.spinner1);
		booksAdapter = new ArrayAdapter <String>(this,android.R.layout.simple_spinner_item);
		booksIDAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item);
		spinner.setAdapter(booksAdapter);
		spinner.setOnItemSelectedListener(this);
		new bookFectcherTask().execute(SERVER_URL,forSpinner,"cid001");
		forSpinner="false";
		//new bookFectcherTask().execute(SERVER_URL1, forSpinner,"cid001");		
		booksList = (ListView) findViewById(R.id.books_list);
		books = new ArrayList<Book>();
		bsignout.setOnClickListener(this);
		bcheckout.setOnClickListener(this);
		booksList.setOnItemClickListener(this);
		preference = getSharedPreferences("login_data", Context.MODE_PRIVATE);
		usr = preference.getString("usr", "");
		pwd = preference.getString("pwd", "");
		Bundle bundle = new Bundle();
		bundle.putString("usr", usr);
		bundle.putString("pwd", pwd);
		Toast.makeText(Activity_Three.this, usr + " Login App!!",Toast.LENGTH_LONG).show();
		
		
		
	}


	class bookFectcherTask extends AsyncTask<String, Void, String> {
		private String forSpinner;
		private String CateId;

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);

		}


		@Override
		protected String doInBackground(String... params) {
			try {
				forSpinner=params[1].toString();
				CateId=params[2].toString();
				URL url = new URL(params[0]);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				Log.i("vforce", "====>" + connection.getResponseCode());
				InputStream inputstream = connection.getInputStream();
				InputStreamReader streamreader = new InputStreamReader(
						inputstream);
				BufferedReader buffereader = new BufferedReader(streamreader);
				StringBuilder container = new StringBuilder();
				String lineholder = "";
				while ((lineholder = buffereader.readLine()) != null) {
					container.append(lineholder);
				}
				connection.disconnect(); //very important!!!!
				buffereader.close();
				streamreader.close();
				inputstream.close();
				return container.toString();
			} catch (Exception e) {

				return null;
			}

		}

		@Override
		protected void onPostExecute(String result) {

			progressBar.setVisibility(View.INVISIBLE);
			if(!TextUtils.isEmpty(result)){
				try {
					if(forSpinner.equals("true"))
						parseResponse(result);
					else
						parseResponse1(result);
				} catch (JSONException e) {
					e.printStackTrace();
				} 
			}
		}

		private void parseResponse(String result) throws JSONException {
			JSONArray fruitsArray = new JSONArray (result);
			for (int i=0;i<fruitsArray.length();i++){
				JSONObject fruitObject = fruitsArray.getJSONObject(i);
				booksAdapter.add(fruitObject.getString("categoryTitle"));
				booksIDAdapter.add(fruitObject.getString("categoryID"));
			}
			spinner.setVisibility(View.VISIBLE);
		}
		
		private void parseResponse1(String result) throws JSONException {
			books.clear();
			JSONArray booksArray = new JSONArray(result);
			for (int i = 0; i < booksArray.length(); i++) {
				JSONObject bookObject = booksArray.getJSONObject(i);
				String CateId1=bookObject.getString("categoryID");

				Book book = new Book(bookObject.getString("ID"),
						bookObject.getString("categoryID"),
						bookObject.getString("author"),
						bookObject.getString("Title"),
						bookObject.getDouble("price"));
				
				if(CateId.equals(CateId1))
					books.add(book);
			
			}
			bookAdapter = new BookAdapter(books,Activity_Three.this);
			booksList.setAdapter(bookAdapter);
			booksList.setVisibility(View.VISIBLE);
			

		}
		

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		
		String a = booksIDAdapter.getItem(position).toString();
		forSpinner="false";
		new bookFectcherTask().execute(SERVER_URL1, forSpinner, a);

		//Toast.makeText(this,a , Toast.LENGTH_LONG).show();
		int position1 = spinner.getSelectedItemPosition();
		switch (position1) {
		case 0:

			break;
		case 1:

			break;
		case 2:

			break;

		}		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Class ourClass;
		switch(arg0.getId()){
		case R.id.bsignout:
			preference.edit().putString("verify", "no").commit();
			preference.edit().putString("usr", "").commit();
			preference.edit().putString("pwd", "").commit();
			try {
				ourClass = Class.forName("com.example.flipkart.MainActivity");
				Intent ourIntent = new Intent(Activity_Three.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case R.id.bcheckout:
			try {
				ourClass = Class.forName("com.example.flipkart.Activity_Five");
				Intent ourIntent = new Intent(Activity_Three.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
			
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		//Toast.makeText(Activity_Three.this, books.get(position).bookTitle, Toast.LENGTH_LONG).show();

		Bundle bundle = new Bundle();
		bundle.putString("bookTitle", books.get(position).bookTitle);
		bundle.putString("bookAuthor", books.get(position).bookAuthor);
		bundle.putString("bookPrice", Double.toString(books.get(position).bookPrice));
		bundle.putString("categoryID", books.get(position).bookCateID);
		bundle.putString("bookId", books.get(position).bookId);
		Class ourClass;
		try {
			ourClass = Class.forName("com.example.flipkart.Activity_Four");
			Intent ourIntent = new Intent(Activity_Three.this, ourClass);
			ourIntent.putExtras(bundle);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}