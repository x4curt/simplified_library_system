# Simple Console Based Library System

This project is written in **Java and uses **Maven build tools for importing dependcies such as the JDBC required to connect to the database. The database of choice for this system is a **PostgreSQL **DB, which is required to be downloaded, installed and setup running on the localhost machine in order for this code to run and access it. This code will allow for users to access library books stored in the database, add more books to the system, delete them and also check-in/check-out books in the traditional library sense. This project is a simple java console based application that was developed in order to demonstrate my ability to develop Java-based projects with a build tools system and a database.

## How To Use:
### Download Docker
Download docker desktop or docker build tools to allow for docker containers to be built and run.
### Pull Repo
You can either clone this repository using ssh or https or download the zip file to get the contents on your machine.
### Build Docker Image
Using the Dockerfile in the repo, you can use the following docker commands to build and run the docker image containing the database of books used for the java application. 

Commands:

+ docker build -t library_system_image .
+ docker run --name library_system_container -p 5432:5432 -d library_system_image

### Run The Java App
Once the database docker container is up and running, you can use the LibraryApp.java file to start the application were you will be presented with the main menu of the application. After this you can interact with the app, viewing all books in the database, check some books in and out as well as delete books from the database and add new ones too.
