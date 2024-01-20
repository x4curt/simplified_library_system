package com.curtiscummings;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class LibraryApp {
	
	static Menu libMenu;
	static Scanner input = new Scanner(System.in);
	static ArrayList<LibraryBook> library = new ArrayList<>();
	static ArrayList<String> isbn_list = new ArrayList<>();
	static Connection c = null;
    static Statement stmt = null;
    static String create_sql = null;
    
	

	public static void main(String[] args){
		getBooksFromDB();
		createMenu();
		processSystemRequests();    
	}
	
	public static Connection create()
	{
		  try {
			  Class.forName("org.postgresql.Driver");
			  c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/library_system");
			  c.setAutoCommit(false);
		  	} catch (Exception e) {
	            e.printStackTrace();
	            System.err.println(e.getClass().getName()+": " + e.getMessage());
	            System.exit(0);
		  	}
		  return c;
	}
	
	private static void createMenu() {
		String options[] = { "List All Books", "Borrow A Book", "Return A Book", "Add New Book", "Remove a Book", "Exit" };
		libMenu = new Menu("Library System", options);
	}

	private static void processSystemRequests() {
		int option = libMenu.getUserChoice();
		while (option != 6) {
			switch (option) {
			case 1:
				getBooksFromDB();
				displayBooks();
				break;
			case 2:
				getBooksFromDB();
				borrowBook();
				break;
			case 3:
				getBooksFromDB();
				returnBook();
				break;
			case 4:
				getBooksFromDB();
				getInsertData();
				break;
			case 5:
				getDeleteData();
				break;
			default:
				System.out.println("\nInvalid option, try again.\n");
			}
			option = libMenu.getUserChoice();
		}
		System.out.println("\nApplication Terminated.\n");
	}

	
	private static void getInsertData()
	{
		System.out.println("Enter Book Title: ");
		String get_title = input.nextLine();
		System.out.println("Enter Book's Author: ");
		String get_author = input.nextLine();
		System.out.println("Enter Book Publisher: ");
		String get_publisher = input.nextLine();
		System.out.println("Enter Book ISBN: ");
		String get_isbn = input.nextLine();
		addToDB(get_title, get_author, get_publisher, get_isbn);
	}
	
	private static void getDeleteData()
	{
		System.out.println("Enter Book ID: ");
		int get_id = input.nextInt();
		deleteBook(get_id);
	}
	
	private static void borrowBook() {
		Connection c = create(); 
		try {
	            System.out.println("Enter book ID: ");
	    		int inputID = input.nextInt();
	    		String get_status_before = library.get((inputID)-1).getAvailability();
	    		if(get_status_before.equals(BookStatus.ON_LOAN.toString())) 
	    		{
	    			System.out.println("\nUnfortunately this book is not available to checkout as it's already on loan.\n");
	    		}
	    		else if(get_status_before.equals(BookStatus.WITHDRAWN.toString()))
	    		{
	    			System.out.println("\nUnfortunately this book has been withdrawn from the library.\n");
	    		}
	    		else {
	    		library.get((inputID)-1).setStatus(BookStatus.ON_LOAN);
	    		String get_status_after = library.get((inputID)-1).getStatus().toString();
	    		create_sql = "UPDATE library_books SET availability=? WHERE book_id=?;";
	            PreparedStatement pstmt = c.prepareStatement(create_sql);
	    		pstmt.setString(1, get_status_after);
	            pstmt.setInt(2, inputID);
	    		pstmt.executeUpdate();
	            c.commit();
	            System.out.println("\nThe book is available to borrow. Don't forget to return it when you are done!\n");
	            pstmt.close();
	            c.close();

		}}   catch (Exception e) {
	            e.printStackTrace();
	            System.err.println(e.getClass().getName()+": " + e.getMessage());
//	            System.exit(0);
	        }
		
	}

	private static void returnBook() 
	{
		Connection c = create();
		try {
	            System.out.println("Enter book ID: ");
	    		int inputID = input.nextInt();
	    		String get_status_before = library.get((inputID)-1).getAvailability();
	    		if(get_status_before.equals(BookStatus.AVAILABLE.toString())) 
	    		{
	    			System.out.println("\nThis book is not out on loan and doesn't need returned, however it is available to checkout.\n");
	    		}
	    		else {
	    		library.get((inputID)-1).setStatus(BookStatus.AVAILABLE);
	    		String get_status = library.get((inputID)-1).getStatus().toString();
	    		create_sql = "UPDATE library_books SET availability=? WHERE book_id=?;";
	            PreparedStatement pstmt = c.prepareStatement(create_sql);
	    		pstmt.setString(1, get_status);
	            pstmt.setInt(2, inputID);
	    		pstmt.executeUpdate();
	            c.commit();
	            System.out.println("\nBook has been checked-in successfully, thanks for returning it!\n");
	            pstmt.close();
	            c.close();

	        }}  catch (Exception e) {
	            e.printStackTrace();
	            System.err.println(e.getClass().getName()+": " + e.getMessage());
	            System.exit(0);
	        }
	}

	
	private static void getBooksFromDB()
    {
		Connection c = create();
        try {
            stmt = c.createStatement();
            stmt.setFetchSize(0);
            ResultSet rs = stmt.executeQuery("SELECT * FROM library_books ORDER BY book_id ASC;");
            library.clear();
            while (rs.next())
            {
                int id = rs.getInt("book_id");
                String availability = rs.getString("availability");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String publisher = rs.getString("publisher");
                String isbn = rs.getString("ISBN");
                library.add( new LibraryBook(id, availability, title, author, publisher, isbn));
            }
            rs.close();
            stmt.close();
            c.close();

        }   catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }
    }

	private static void getIsbn(){
		isbn_list.clear();
		for(int index=0; index<library.size(); index++)
		{
			isbn_list.add(library.get(index).getIsbn());
		}
	}
	
	private static boolean checkIsbn(String isbn)
	{
		boolean result = false;
		getIsbn();
		for (int index=0; index<isbn_list.size(); index++)
		{
			if(isbn.equals(isbn_list.get(index)))
			{
				result = true;
			}
		}
		return result;
	}
	private static void addToDB(String title, String author, String publisher, String isbn)
	{
		Connection c = create();
		try {
			boolean isbnInList = checkIsbn(isbn);
			if (isbnInList == true){
				System.out.println("\nThis book is already in the library.\n");
			}
			else {
				create_sql = "INSERT INTO library_books (title, author, publisher, isbn) VALUES (?, ?, ?, ?);";
				PreparedStatement pstmt = c.prepareStatement(create_sql);
				pstmt.setString(1, title);
				pstmt.setString(2, author);
				pstmt.setString(3, publisher);
				pstmt.setString(4, isbn);
				pstmt.executeUpdate();
				c.commit();
				pstmt.close();
				c.close();
				System.out.println("\nBook was added successfully\n");
			}

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }
		
	}

	private static void deleteBook(int book_id)
	{
		Connection c = create();
		boolean found = false;
		for (int index=0; index<library.size(); index++)
		{
			if(book_id == library.get(index).getID())
			{
				found = true;
				break;
			}
		}
		if(found == false)
		{
			System.out.println("\nThis book is either not in the library or you entered an incorrect ID.\n");
		}
		else{
			try {
				create_sql = "DELETE FROM library_books WHERE book_id=?;";
				PreparedStatement pstmt = c.prepareStatement(create_sql);
				pstmt.setInt(1, book_id);
				pstmt.executeUpdate();
				c.commit();
				pstmt.close();
				c.close();

			} catch (Exception e) {
				e.printStackTrace();
				System.err.println(e.getClass().getName()+": " + e.getMessage());
				System.exit(0);
			}
			
			System.out.println("\nDelete was successful");
		}
	}
	
	private static void displayBooks() 
	{
		for(int index=0; index<library.size(); index++)
		{
		    System.out.println();
			System.out.println(library.get(index).getDetails());
			System.out.println();		
			
		}
			
	}


}