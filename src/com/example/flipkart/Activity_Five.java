package com.example.flipkart;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Activity_Five extends Activity implements OnClickListener {
	private TextView tvTotalTax, tvTotalsum;
	private Button button1, button12;
	private InTheCar car;
	private ArrayList<Car> incar;
	private ListView car_list;
	private CarAdapter carAdapter;
	private HashMap<String, String> incaritem;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_five);
		incaritem=new HashMap<String, String>();
		tvTotalsum=(TextView)findViewById(R.id.tvTotalSum);
		tvTotalTax=(TextView)findViewById(R.id.tvTotalTax);
		button12 = (Button)findViewById(R.id.button12);
		car_list = (ListView)findViewById(R.id.car_list);
		button12.setOnClickListener(this);
		incar= new ArrayList<Car>();
		car = new InTheCar(this);
		car.open();
		getProduct();
		tvTotalTax.setText("Tax: " + String.format("%.2f",getTotal()*0.065));
		tvTotalsum.setText("   Total: "+ String.format("%.2f",getTotal()));
		
		
	}

	private Double getTotal() {
		// TODO Auto-generated method stub
		Double sum=0.0;
		for(int i=0;i<incar.size();i++){
			sum=sum+incar.get(i).bookPrice * incar.get(i).bookQut;
		}
		return sum;
	}

	private void insertProduct(Car books){
		car.createNewBuy(books);
	}
	
	private void getProduct(){
		incar=car.getNewBuy();
		//Toast.makeText(Activity_Five.this, incar.get(0).bookId, Toast.LENGTH_LONG).show();
		carAdapter = new CarAdapter(incar,Activity_Five.this);
		car_list.setAdapter(carAdapter);
		car_list.setVisibility(View.VISIBLE);		
	}
	private void checkOut(){
		for(int i=0;i<incar.size();i++){
			incaritem.put(incar.get(i).bookTitle, toString().valueOf(incar.get(i).bookQut));
		}	
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button12:
			checkOut();
			Class ourClass;
			try {
				ourClass = Class.forName("com.example.flipkart.ReadInventory");
				Intent ourIntent = new Intent(Activity_Five.this, ourClass);
				ourIntent.putExtra("incaritem1", incaritem);
				startActivity(ourIntent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
}
