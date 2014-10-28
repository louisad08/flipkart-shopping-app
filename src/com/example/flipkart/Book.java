package com.example.flipkart;

public class Book {
	public String bookId;
	public String bookCateID;
	public String bookAuthor;
	public String bookTitle;
	public double bookPrice;
	
	public Book(String bookId,String bookCateID,String bookAuthor,String bookTitle,double bookPrice){
		this.bookId=bookId;
		this.bookCateID=bookCateID;
		this.bookAuthor=bookAuthor;
		this.bookTitle=bookTitle;
		this.bookPrice=bookPrice;
	}

}
