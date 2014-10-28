package com.example.flipkart;

import java.io.InputStream;
import java.net.ContentHandler;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CarAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Car> books;
	InTheCar car;	
	TextView total;

	public CarAdapter(ArrayList<Car> books, Context context) {
		this.context = context;
		this.books = books;
		car = new InTheCar(this.context);
		car.open();
		
		
	}

	@Override
	public int getCount() {
		return books.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return books.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int  position, View convertView, ViewGroup parent) {
		View rowView = LayoutInflater.from(context).inflate(R.layout.car_row,null);
		Car book = books.get(position);

		TextView bookName = (TextView) rowView.findViewById(R.id.carbookName);
		TextView bookPrice = (TextView) rowView.findViewById(R.id.carbookPrice);
		final EditText bookQut = (EditText) rowView.findViewById(R.id.carbookQut);
		TextView tvTax = (TextView)rowView.findViewById(R.id.tvTax);
		Button remove = (Button)rowView.findViewById(R.id.bremove);
		Button update = (Button)rowView.findViewById(R.id.bupdate);
		TextView tvTotal =(TextView)rowView.findViewById(R.id.tvTotal);
		
		remove.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				

				car.deleteNewBuy(books.get(position));
				books.remove(position);
				//Toast.makeText(context, getTotal(), Toast.LENGTH_LONG).show();
				
				notifyDataSetChanged();
				
			}
			
		});
		
		
		update.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View v) {
				
				books.get(position).bookQut=Integer.valueOf(bookQut.getText().toString());
				car.updateNewBuy(books.get(position));				
				
				notifyDataSetChanged();
				
			}
			
		});		

		ImageView productpic = (ImageView) rowView
				.findViewById(R.id.CarProductPic);
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/Download/" + book.bookId + ".jpg";
		Drawable d = Drawable.createFromPath(imagePath);
		productpic.setImageDrawable(d);
		//String tax = toString().valueOf(book.bookPrice * book.bookQut * 0.065);
		tvTax.setText("Tax: "+String.format("%.2f", book.bookPrice * book.bookQut * 0.065));
		
		
		bookName.setText(book.bookTitle);
		bookPrice.setText("USD "+ String.format("%.2f", book.bookPrice) + "    Amout: ");
		String qut=toString().valueOf(book.bookQut);
		bookQut.setText(qut);
		
		tvTotal.setText("Sub Total: USD "+String.format("%.2f",book.bookPrice*book.bookQut));

		return rowView;
	}
	
	private String getTotal() {
		// TODO Auto-generated method stub
		Double sum=0.0;
		for(int i=0;i<books.size();i++){
			sum=sum+books.get(i).bookPrice * books.get(i).bookQut;
		}
		return "Total: "+toString().valueOf(sum);
	}

}
