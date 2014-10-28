package com.example.flipkart;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BookAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Book> books;

	public BookAdapter(ArrayList<Book> books, Context context) {
		this.context = context;
		this.books = books;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = LayoutInflater.from(context).inflate(R.layout.book_row,
				null);
		Book book = books.get(position);

		TextView bookName = (TextView) rowView.findViewById(R.id.bookName);
		TextView bookPrice = (TextView) rowView.findViewById(R.id.bookPrice);

		ImageView productpic = (ImageView) rowView
				.findViewById(R.id.productPic);
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/Download/" + book.bookId + ".jpg";
		Drawable d = Drawable.createFromPath(imagePath);
		productpic.setImageDrawable(d);

		String price = toString().valueOf(book.bookPrice);
		bookName.setText(book.bookTitle);
		bookPrice.setText(price);

		return rowView;
	}

}
