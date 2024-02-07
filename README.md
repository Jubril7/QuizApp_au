# QuizApp_au

#Quiz Application Backend (Spring Boot)
Introduction
This is the backend server for the Quiz Application. It provides RESTful APIs to manage users, quizzes, questions, and quiz results.

#How to Set Up and Run the Backend
Prerequisites
Java Development Kit (JDK) installed on your machine
Apache Maven installed on your machine
PostgreSQL installed and running locally or accessible via a remote URL
A PostgreSQL database created for the application
Installation and Configuration
Clone this repository to your local machine:
git clone https://github.com/Jubril7/quiz-app-backend-spring-boot.git
Navigate to the project directory:
cd quiz-app-backend-spring-boot
Open the application.properties file located in src/main/resources.
Update the database connection details for PostgreSQL:
spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
spring.datasource.username=your-username
spring.datasource.password=your-password
Replace your-database-name, your-username, and your-password with your actual PostgreSQL database details.
Save the changes to the application.properties file.
How to Start the Spring Boot Application
Open a terminal or command prompt.
Navigate to the project directory if you're not already there.
Run the following Maven command to build the project:
mvn clean install
After the build is successful, run the following command to start the Spring Boot application:
mvn spring-boot:run
The backend server will start running at http://localhost:8080.
Functionality of the Backend
User Authentication
Users can sign up and log in to the application.
Only an admin user can create quiz questions.
Quiz Taking
Users can take quizzes created by the admin.
After completing a quiz, users can view their results for all quizzes they have taken.
Admin Functionality
Admin users can create quiz questions.
Admin users can also view the scores of all users who have taken quizzes.