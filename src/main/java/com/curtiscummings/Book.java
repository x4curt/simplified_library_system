package com.curtiscummings;

public class Book {
	//Private variables of type string to be used to store information on the book
	private String title;
	private String author;
	private String publisher;
	private String isbn;
	
	//The constructor class which passes the arguments title, author, publisher and isbn number
	public Book(String title, String author, String publisher, String isbn)
	{
		//Setting the input values from the class constructor to equal the variables declared above
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
	}
	//The Getter methods to get the information stored about title, author, publisher and ISBN
	public String getTitle()
	{
		return this.title;
	}
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public String getPublisher()
	{
		return this.publisher;
	}
	
	public String getIsbn()
	{
		return this.isbn;
	}
	
	//Returns a string with all the book information to be displayed
	public String getDetails()
	{
		return " Title: " + getTitle() + " Author: " + getAuthor() + " Publisher: " + getPublisher() + " ISBN: " + getIsbn(); 
	}
	

}
