# UrlShortener
A simple and efficient URL Shortener built with **Spring Boot**, **MongoDB**, **Java 17**, and **Maven**. It takes long URLs and generates shortened, shareable versions with redirection support.


## Features

- Shorten any valid URL
- Redirect to original URL
- Track number of visits
- RESTful API
- MongoDB for storage
- Built with Java & Spring Boot

## Tech Stack
![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Maven](https://img.shields.io/badge/Maven-Build-blue?logo=apachemaven)
![MongoDB](https://img.shields.io/badge/Database-MongoDB-green?logo=mongodb)
![Lombok](https://img.shields.io/badge/Lombok-Used-orange?logo=lombok)


## Setup Instructions
### Prerequisites
- Java 17+
- Maven
- MongoDB running locally or remotely
### Steps
```bash
# Clone the repository
git clone https://github.com/mukuldeep/UrlShortener.git
cd urlshortener
```
```bash
# Build the project
mvn clean install
```
```bash
# Run the application
mvn spring-boot:run
```


## API Endpoints

| Method | Endpoint           | Description            |
| ------ | ------------------ | ---------------------- |
| `POST` | `/shorten`         | Create short URL       |
| `GET`  | `/{shortCode}`     | Redirect to long URL   |

## How It Works
1. Accepts a long URL from the user.
2. Generates a unique short code (e.g., abc123).
3. Saves it in MongoDB.
4. Redirects short URL (/abc123) to the original long URL.

## Project Structure
```css
src/
├── main/
│   ├── java/com/offlinew/urlshortener/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── UrlShortenerApplication.java
│   └── resources/
│       ├── application.properties
│       └── ...
```

## Contributing
Contributions are welcome! Feel free to submit issues or pull requests.
