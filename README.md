# MikiFit Fitness Equipment E-Commerce Back End

This repository contains the back-end code for the fitness equipment e-commerce site inspired by Mirafit.co.uk. The server is built in Java with Spring Boot, utilizing various technologies to provide a robust and efficient backend for the e-commerce platform.

### Docker Image

Docker image of the server is available on DockerHub: [micknice/aws-demo](https://hub.docker.com/repository/docker/micknice/aws-demo/general)

### Live Site

The live site is hosted at: [Fitness E-Commerce Live Site](https://e-commerce-fe-micknice.vercel.app/category/weights-and-bars)

## Technologies Used

- **Java and Spring Boot:** The backend is developed using Java with the Spring Boot framework, providing a scalable and modular architecture.
- **Hibernate:** Object-relational mapping for efficient database interactions.
- **PostgreSQL:** Database storage for user information, product details, reviews, orders, and inventory.
- **WebSockets and STOMP:** Enabling real-time updates, ensuring live inventory updates when items are added to the cart.
- **JWT (JSON Web Tokens):** Custom user authentication, registration, login, and password reminder functionalities.
- **Sendgrid SMTP Server:** Utilized for sending email notifications in the password reminder process.
- **CI/CD with Git:** Continuous Integration and Continuous Deployment are achieved using Git and DockerHub.
- **Maven and Docker:** Build tools to manage dependencies and package the application for deployment.
- **JUnit:** Unit testing for code quality assurance.

## Features

- **User Authentication and Authorization:** Secure endpoints for auth, users, products, reviews, orders, and inventory.
- **Custom User Authentication:** Implementation of JWT-based custom user authentication for enhanced security.
- **Live Inventory Updates:** WebSockets and STOMP provide real-time updates of inventory when items are added to the cart.
- **Email Notifications:** Password reminder functionality with email notifications powered by Sendgrid SMTP.
- **CI/CD with Docker:** The application is developed and deployed using CI/CD with Git and DockerHub.

## Getting Started

To run this project locally, follow these steps:

1. Clone the repository: `git clone https://github.com/micknice/e-commerce-be.git`
2. Navigate to the project directory: `cd e-commerce-be`
3. Build the project: `mvn clean install`
4. Run the application: `java -jar target/e-commerce-be-<version>.jar`

### Docker

Alternatively, you can use Docker to run the application:

```bash
docker run -p 8080:8080 micknice/aws-demo:latest
```

## Acknowledgments

- Inspired by Mirafit.co.uk


