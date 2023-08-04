# BookingApp

BookingApp is a web application built using Java and the Spring framework. The project utilizes a PostgreSQL database and is designed for managing hotel reservations.

## Features

- **User Registration**: New users can register, with the first registered user automatically receiving an administrator role, while subsequent users get a regular user role.

- **User Management**: Administrators can manage user profiles, view, and modify user details.

- **Hotel and Room Management**: Administrators can add new hotels and assign rooms to them.

- **Room Reservations**: Registered users can make reservations for available hotel rooms.

- **Automatic Reservation Updates**: The system automatically updates reservations to prevent multiple users from reserving the same room simultaneously.

- **Reservation Status Management**: Administrators can change reservation statuses, with the process automated.

- **Security**: Spring Security with PasswordEncoder is used for secure password storage.

- **Unit Tests**: The project includes several unit tests using JUnit and Mockito.

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- Thymeleaf
- PostgreSQL

## System Requirements

- Java version 17
- Maven

## Project Structure

The project is organized as follows:

- `src/main/java/io/github/bialekmm/bookingapp/`
  - `controller/`: Controllers handling HTTP requests.
  - `service/`: Business service layer.
  - `repository/`: Database interactions and JPA repositories.
  - `entity/`: Data model entities.
  - `dto/`: Data Transfer Objects used for communication.
  - `security/`: Spring Security configuration and authentication handling.
- `src/main/resources/`
  - `templates/`: Thymeleaf templates for the user interface.

## Automated Testing

The project includes a suite of automated tests using JUnit and Mockito. These tests cover various aspects of the application's functionality to ensure its correctness and reliability. Tests are present in the `src/test/java/` directory, and they are organized into relevant packages based on the tested components.

Here are a few examples of the tests included:

- `UserServiceImplTest`: Contains tests for user-related services such as user retrieval, user creation, and user deletion.

- `HotelServiceImplTest`: Includes tests for hotel-related services, like finding hotels, finding hotel by room ID, and more.

These tests are essential for maintaining the project's quality and for catching any regressions or bugs early in the development process. You can run the tests using the following command:
  ```shell
  mvn test
  ```

## Installation and Usage

1. Clone the repository to your local machine.
   ```shell
   git clone https://github.com/bialekmm/BookingApp.git
   
2. Navigate to the project directory:
   ```shell
   cd BookingApp
   
3. Modify the application.properties file to configure access to the PostgreSQL database.

4. Run the application:
   ```shell
   mvn spring-boot:run

5. Open your web browser and go to http://localhost:9090 to access the application.

## Authors

Maciej Białek - bialeek.m@gmail.com

## License

This project is released under the MIT License.



