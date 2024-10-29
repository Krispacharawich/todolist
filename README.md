
# TodoList

This project is a full-stack application with a Spring Boot backend and a React.js frontend.

## Prerequisites

- **Java** (version 21)
- **Gradle** (for Spring Boot)
- **Node.js** and **npm** (for React)

## Setup Instructions

### Backend (Spring Boot)

1. Navigate to the backend folder:
   ```bash
   cd backend
   ```

2. Build the project using Gradle:
   ```bash
   ./gradlew clean 
   ./gradlew build
   ```

3. Run the Spring Boot application:
   ```bash
   java -jar build/libs/backend-todolist.jar
   ```

   The backend will start on `http://localhost:8080`.

### Frontend (React.js)

1. Navigate to the frontend folder:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the React application:
   ```bash
   npm start
   ```

   The frontend will start on `http://localhost:3000`.

## Decision during development process
### Backend (Spring Boot)
- In order to maintain a separation of concerns, the structure of the backend projects contains a separate layers; controller, service and repository. So each layer will have their own responsibilities.
- Leverage @lombok for helper methods like getter/setter and etc.
- As the requirement to store data in-memory storage, h2database is chosen to followed the requirement. Beside that, using h2database can help support for further development when 
 database is needed with less effort whereas using in-memory store like variable might easy to development but require a lot of code changed.
### Frontend (React.js)
- Use `redux` as a state management and use `redux-toolkit` to reduce some boilerplate codes
- The project structure is like this
```
src
├── components       # Reusable components for the UI
├── features         # Contains specific features of the app
├── service          # Service files for API calls and external data handling
├── store            # Redux store and related configurations
├── App.css          # Styles for the main App component
├── App.js           # Main App component
├── index.css        # Global styles for the app
└── index.js         # Entry point for the React application
```

### Integration between Frontend and Backend
- Backend APIs have been designed to keep their responsibility such as toggleTask will have only toggle logic so that if frontend need the updated data, it need to call to the latest data from Backend API again.
- Use `http` instead of `https` because of local development and local testing


## Technologies and Libraries Used

### Backend (Spring Boot)

The backend is built with **Spring Boot** and includes the following dependencies:

- **spring-boot-starter**: Provides core Spring Boot functionality, making it easier to create and configure a Spring-based application.
- **spring-boot-starter-web**: Adds support for building web, RESTful, and MVC applications, enabling easy creation of APIs.
- **spring-boot-starter-data-jpa**: Simplifies database interactions with Java Persistence API (JPA) support, allowing for ORM (Object-Relational Mapping) with database entities.
- **spring-boot-starter-validation**: Provides support for validating request payloads and ensuring data integrity in the application.
- **lombok**: A code generator that reduces boilerplate code for getters, setters, constructors, and more. It simplifies the model classes.
- **h2database**: An in-memory database used for development and testing purposes, which eliminates the need for a separate database server during local testing.
- **mapstruct**: A Java annotation processor that generates type-safe mappings between Java bean types. This is useful for converting between data transfer objects (DTOs) and entities.
- **spring-boot-starter-test**: A testing suite that includes various tools, such as JUnit, for unit testing the Spring Boot application.
- **junit-platform-launcher**: Required to launch JUnit tests; it helps with comprehensive testing coverage of the application.

### Frontend (React.js)

The frontend is built with **React.js** and includes the following dependencies:

- **@reduxjs/toolkit**: Simplifies Redux state management, providing tools to write Redux logic with less boilerplate.
- **@testing-library/jest-dom**: Provides custom Jest matchers for asserting on DOM nodes, useful for testing React components.
- **@testing-library/react**: A testing library for React components that encourages testing from the user’s perspective.
- **@testing-library/user-event**: Simulates user interactions to test how components behave in response to user actions.
- **axios**: A promise-based HTTP client for making API requests, ideal for fetching data from the backend.
- **prop-types**: Type-checks the props passed to components, helping catch potential bugs related to data types.
- **react**: Core library for building UI components, using a component-based approach.
- **react-beautiful-dnd**: A drag-and-drop library designed for smooth, accessible, and beautiful drag-and-drop experiences.
- **react-dnd**: Provides utilities to add drag-and-drop functionality to components in React.
- **react-dom**: Renders React components to the DOM and is required for web applications.
- **react-icons**: Provides popular icons for React applications, enhancing the UI/UX with visual icons.
- **react-redux**: Connects React components to the Redux store, enabling global state management.
- **react-router-dom**: A routing library for handling navigation in React applications, allowing for multi-page experiences.
- **react-scripts**: Configures the React project setup, scripts, and build tools, managed by Create React App.
- **web-vitals**: Provides utilities to measure and optimize web performance, tracking metrics like loading time and responsiveness.

## Running Tests

### Backend (Spring Boot)

1. **Run All Tests**:
   To run all tests for the Spring Boot application, navigate to the `backend` directory and use Gradle:
   ```bash
   cd backend
   ./gradlew test
   ```

   This command will execute all unit and integration tests defined in the project.

2. **Test Reports**:
   After running tests, Gradle will generate a report located in `build/reports/tests/test/index.html`. You can check this folder for a summary of test results.

### Frontend (React.js)

1. **Run All Tests**:
   To run all tests for the React frontend, navigate to the `frontend` directory and use the following command:
   ```bash
   cd frontend
   npm test
   ```

   This command runs tests in interactive watch mode. Press `a` to run all tests or `q` to quit.