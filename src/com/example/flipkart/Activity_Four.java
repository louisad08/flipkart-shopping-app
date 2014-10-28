package com.example.flipkart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Four extends Activity implements OnClickListener {
	private ImageView productpic;
	private TextView booktitle, bookauthor, bookprice;
	private Button addincar, checkcar;
	private InTheCar car;
	// private SharedPreferences preference;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// preference = getSharedPreferences("login_data",
		// Context.MODE_PRIVATE);
		bundle = getIntent().getExtras();
		setContentView(R.layout.activity_four);
		productpic = (ImageView) findViewById(R.id.productPic);
		booktitle = (TextView) findViewById(R.id.booktitle);
		bookauthor = (TextView) findViewById(R.id.bookauthor);
		bookprice = (TextView) findViewById(R.id.bookprice);
		addincar = (Button) findViewById(R.id.addInCar);
		checkcar = (Button) findViewById(R.id.checkCar);
		addincar.setOnClickListener(this);
		checkcar.setOnClickListener(this);

		booktitle.setText(bundle.getString("bookTitle"));
		bookauthor.setText(bundle.getString("bookAuthor"));
		bookprice.setText(bundle.getString("bookPrice"));

		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/Download/" + bundle.getString("bookId") + ".jpg";
		Drawable d = Drawable.createFromPath(imagePath);
		productpic.setImageDrawable(d);
		
		car = new InTheCar(this);
		car.open();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.addInCar:
			Car product = new Car(bundle.getString("bookId"),
					bundle.getString("categoryID"),
					bundle.getString("bookAuthor"),
					bundle.getString("bookTitle"), Double.valueOf(
							bundle.getString("bookPrice")).doubleValue(),
					Integer.valueOf("1").intValue());
			car.BeforeAddNewBuy(product);
			break;
		case R.id.checkCar:
			Class ourClass;
			try {
				ourClass = Class.forName("com.example.flipkart.Activity_Five");
				Intent ourIntent = new Intent(Activity_Four.this, ourClass);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		}

	}

}
