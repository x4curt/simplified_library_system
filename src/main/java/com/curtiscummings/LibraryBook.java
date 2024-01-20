package com.curtiscummings;

public class LibraryBook extends Book implements Borrower{
	
	private BookStatus status;
	private int id;
	private String availability;

	
	public LibraryBook(int id, String availability, String title, String author, String publisher, String isbn) {
		super(title, author, publisher, isbn);
		this.id = id;
		this.availability = availability;
		
	}
	public int getID()
	{
		return this.id;
	}
	public String getAvailability()
	{
		return this.availability;
	}

	public String getDetails()
	{
		return "(" + getAvailability() +")" + " ID: " + getID() + super.getDetails();
	}
	
	public BookStatus getStatus()
	{
		return status;
	}
	
	public void setStatus(BookStatus status)
	{
		this.status = status;
	}
	
	public boolean checkout() {
		boolean statusCheck;
		if (getStatus() == BookStatus.ON_LOAN)
		{	
			statusCheck = false;
			return statusCheck;
		}
		else if(getStatus() == BookStatus.WITHDRAWN)
		{
			statusCheck = false;
			return statusCheck;
		
		}
		else statusCheck = true;
		
		return statusCheck;
	}

	public boolean checkin() {
		
		boolean statusCheck;
		if (getStatus() == BookStatus.AVAILABLE)
		{	
			statusCheck = false;
			return statusCheck;
		}
		else if(getStatus() == BookStatus.WITHDRAWN)
		{
			statusCheck = false;
			return statusCheck;
		
		}
		else statusCheck = true;
		
		return statusCheck;
	}

}
