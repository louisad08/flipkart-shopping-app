package com.example.flipkart;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class InTheCar {

	public static final String DATABASE_NAME = "flipkart.db";
	public static final String TABLE_NAME = "buy";
	public static final int DATABASE_VERSION = 1;
	public static final String COLUMN_ID = "_Id";

	public static final String COLUMN_BOOKID = "bookId";
	public static final String COLUMN_TITLE = "bookTitle";
	public static final String COLUMN_AUTHOR = "bookAuthor";
	public static final String COLUMN_PRICE = "bookPrice";
	public static final String COLUMN_CATEGORY = "categoryID";
	public static final String COLUMN_QUANTITY = "quantity";

	public static final String TABLE_CREATION_QUERY = "create table "
			+ TABLE_NAME + "( "
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_BOOKID + " TEXT, " 
			+ COLUMN_TITLE	+ " TEXT, " 
			+ COLUMN_AUTHOR + " TEXT, " 
			+ COLUMN_CATEGORY + " TEXT," 
			+ COLUMN_PRICE + " FLOAT, "
			+ COLUMN_QUANTITY + " INTEGER );";

	private FlipkartDBHelper flipkartDBHelper;
	private SQLiteDatabase sqLiteDatabase;
	private final Context context;
	
	private ArrayList<Car> incar;

	public InTheCar(Context context) {
		flipkartDBHelper = new FlipkartDBHelper(context);
		incar = new ArrayList<Car>();
		this.context = context;
	}

	public void open() {
		sqLiteDatabase = flipkartDBHelper.getWritableDatabase();
	}

	public void createNewBuy(Car books) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_TITLE, books.bookTitle);
		contentValues.put(COLUMN_AUTHOR, books.bookAuthor);
		contentValues.put(COLUMN_CATEGORY, books.bookCateID);
		contentValues.put(COLUMN_PRICE, books.bookPrice);
		contentValues.put(COLUMN_QUANTITY, books.bookQut);
		//contentValues.put(COLUMN_BOOKID, books.bookId);
		sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
	}

	public ArrayList<Car> getNewBuy() {
		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null,
				null, null, null);

		if (cursor != null && cursor.moveToFirst()) {

			do {
				//Toast.makeText(context, "test", Toast.LENGTH_LONG).show();
				String title = cursor.getString(cursor
						.getColumnIndex(COLUMN_TITLE));
				String author = cursor.getString(cursor
						.getColumnIndex(COLUMN_AUTHOR));
				String category = cursor.getString(cursor
						.getColumnIndex(COLUMN_CATEGORY));
				/*String id = cursor.getString(cursor
						.getColumnIndex(COLUMN_BOOKID));*/
				String price = cursor.getString(cursor
						.getColumnIndex(COLUMN_PRICE));
				String quantity = cursor.getString(cursor
						.getColumnIndex(COLUMN_QUANTITY));
				Car product = new Car("bid0001", category, author, title,
						Double.valueOf(price).doubleValue(), Integer.valueOf(
								quantity).intValue());
				incar.add(product);
				/*Toast.makeText(
						context,
						price + " " + title + " " + author + " " + category
								+ " " + quantity, Toast.LENGTH_LONG).show();*/
			} while (cursor.moveToNext());
		}
		//Toast.makeText(context, incar.size(),Toast.LENGTH_LONG).show();
		return incar;
	}

	public void updateNewBuy(Car books) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_TITLE, books.bookTitle);
		contentValues.put(COLUMN_AUTHOR, books.bookAuthor);
		contentValues.put(COLUMN_CATEGORY, books.bookCateID);
		contentValues.put(COLUMN_PRICE, books.bookPrice);
		contentValues.put(COLUMN_QUANTITY, books.bookQut);
		//contentValues.put(COLUMN_BOOKID, books.bookId);

		
		if (sqLiteDatabase.update(TABLE_NAME, contentValues,
				COLUMN_TITLE + "=?", new String[] {  books.bookTitle }) > 0) {
			Toast.makeText(context, "Update Success", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "Update Failed", Toast.LENGTH_LONG).show();
		}
	}

	public void BeforeAddNewBuy(Car books) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(COLUMN_TITLE, books.bookTitle);
		contentValues.put(COLUMN_AUTHOR, books.bookAuthor);
		contentValues.put(COLUMN_CATEGORY, books.bookCateID);
		contentValues.put(COLUMN_PRICE, books.bookPrice);		
		//contentValues.put(COLUMN_BOOKID, books.bookId);
		//Toast.makeText(context, "*****"+books.bookId+"*****", Toast.LENGTH_LONG).show();		
		Cursor cursor = sqLiteDatabase.query(TABLE_NAME, null, null, null,
				null, null, null);

		if (cursor != null && cursor.moveToFirst()) {

			do {

				String title = cursor.getString(cursor
						.getColumnIndex(COLUMN_TITLE));
				if(title.equals(books.bookTitle))
					contentValues.put(COLUMN_QUANTITY, Integer.valueOf(cursor.getString(cursor
							.getColumnIndex(COLUMN_QUANTITY)))+1);				
			} while (cursor.moveToNext());
		}
		if (sqLiteDatabase.update(TABLE_NAME, contentValues,
				COLUMN_TITLE + "=?", new String[] {  books.bookTitle }) > 0) {
			Toast.makeText(context, "Add Quantity", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(context, "Add New Item", Toast.LENGTH_LONG).show();
			contentValues.put(COLUMN_QUANTITY, books.bookQut);
			createNewBuy(books); 
		}
	}	
	
	public void deleteNewBuy() {
		sqLiteDatabase.delete(TABLE_NAME, null, null);
		close();
	}
	
	public void deleteNewBuy(Car books) {
		if (sqLiteDatabase.delete(TABLE_NAME, COLUMN_TITLE + "=?",
				new String[] { books.bookTitle }) > 0) {
			Toast.makeText(context, "Deletion Success", Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(context, "Deletion Failed", Toast.LENGTH_LONG)
					.show();
		}
	}


	public void close() {
		sqLiteDatabase.close();
	}
	
	public void deleteAll(){
		sqLiteDatabase.delete(TABLE_NAME, null, null);
		close();
	}

	class FlipkartDBHelper extends SQLiteOpenHelper {

		public FlipkartDBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(TABLE_CREATION_QUERY);
			Log.d("CREATE TABLE", "Create Table Successfully");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}
	}

}
