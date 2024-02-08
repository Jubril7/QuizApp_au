
**QuizApp_au**
**Quiz Application Backend (Spring Boot)**
*Introduction*
This repository contains the backend server for the Quiz Application. It provides RESTful APIs to manage users, quizzes, questions, and quiz results.

**How to Set Up and Run the Backend**
*Prerequisites*
Before setting up and running the backend, ensure you have the following installed on your machine:

*Java Development Kit (JDK)*
*Apache Maven*
*PostgreSQL (installed locally or accessible via a remote URL)*
*PostgreSQL database created for the application*

**Installation and Configuration**
1. Clone this repository to your local machine:
*git clone https://github.com/Jubril7/quiz-app-backend-spring-boot.git*
2. Navigate to the project directory:
*cd quiz-app-backend-spring-boot*
3. Open the application.properties file located in src/main/resources.
4. Update the database connection details for PostgreSQL:
   *spring.datasource.url=jdbc:postgresql://localhost:5432/your-database-name
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   server.port=your-server-port*
Replace your-database-name, your-username, and your-password with your actual PostgreSQL database details.
5. Save the changes to the application.properties file.
**How to Start the Spring Boot Application**
1.  Open a terminal or command prompt.
2.  Navigate to the project directory if you're not already there.
3.  Run the following Maven command to build the project:
*mvn clean install*
4.After the build is successful, run the following command to start the Spring Boot application:
*mvn spring-boot:run*
**The backend server will start running at http://localhost:your-server-port**

**Functionality of the Backend**
**User Authentication**
1. Users can sign up and log in to the application.
2. Only an admin user can create quiz questions.
**Quiz Taking**
1. Users can take quizzes created by the admin.
2. After completing a quiz, users can view their results for all quizzes they have taken.
**Admin Functionality**
1. Admin users can create quiz questions.
2. Admin users can also view the scores of all users who have taken quizzes.




