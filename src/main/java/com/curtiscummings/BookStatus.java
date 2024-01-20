package com.curtiscummings;

public enum BookStatus {
	AVAILABLE(0),
	ON_LOAN(1),
	WITHDRAWN(2);
	
	private String status[] = {"Available", "On Loan", "Withdrawn"};
	
	private int indexStatus;
	
	private BookStatus(int num)
	{
		indexStatus = num;
	}
	public String getStatus() 
	{
		return status[indexStatus];
	}

}
