-- Create the 'library_system' database
-- CREATE DATABASE library_system_db;

-- Connect to the 'library_system' database
\c library_system_db;

-- Create the 'library_books' table
CREATE TABLE library_books (
    book_id SERIAL PRIMARY KEY,
    title VARCHAR NOT NULL,
    author VARCHAR NOT NULL,
    publisher VARCHAR NOT NULL,
    isbn VARCHAR NOT NULL,
    availability VARCHAR DEFAULT 'AVAILABLE' NOT NULL
);

-- Insert example data into the 'library_books' table
INSERT INTO library_books (title, author, publisher, isbn, availability)
VALUES
    ('Macbeth', 'Shakespeare', 'Penguin', '12-34-56', 'AVAILABLE'),
    ('The Hobbit', 'Tolkein', 'Harper Collins', '56-34-21', 'AVAILABLE'),
    ('Jaws', 'Benchley', 'Collins', '55-88-11', 'AVAILABLE'),
    ('Oliver Twist', 'Dickens', 'Wiley', '34-21-76', 'AVAILABLE');
