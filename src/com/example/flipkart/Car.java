package com.example.flipkart;

public class Car {

	public int bookQut;
	public String bookId;
	public String bookCateID;
	public String bookAuthor;
	public String bookTitle;
	public double bookPrice;
	
	public Car(String bookId,String bookCateID,String bookAuthor,String bookTitle,double bookPrice, int bookQut){
		this.bookId=bookId;
		this.bookCateID=bookCateID;
		this.bookAuthor=bookAuthor;
		this.bookTitle=bookTitle;
		this.bookPrice=bookPrice;
		this.bookQut=bookQut;
	}	

}
