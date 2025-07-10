# 🏡 Airbnb Clone - Backend

This is the backend system for an Airbnb-like homestay booking platform, built with **Java**, **Spring Boot**, and **PostgreSQL**. It provides RESTful APIs for homestay management, user authentication, availability tracking, and booking flow with host confirmation support.

## 🚀 Features

- User authentication
- Homestay listing and search
- Date-based availability management
- Booking creation and confirmation by host
- Dynamic pricing per date
- CRUD for location hierarchy: City, District, Ward
- Unit tests using **JUnit** and **Mockito**

## 🛠️ Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **ORM:** Hibernate / JPA
- **Testing:** JUnit, Mockito
- **Build Tool:** Maven
- **Other:** MapStruct, Lombok

## 📁 Project Structure
```markdown
src/
├── main
    ├── java
        ├── com.khanh.airbnb
            ├── config
            ├── controllers
            ├── domain
            ├── dto
            ├── exceptions
            ├── mappers
            ├── repositories
            ├── services
            ├── util
            ├── AirbnbApplication
```


## ⚙️ Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/qkhanh39/airbnb-back-clone.git
cd airbnb-back-clone
```

### 2. Set up the Database
- Install PostgreSQL

- Create a database named airbnb_clone

- Update src/main/resources/application.properties:

```properties
# Database Configuration
DB_URL=jdbc:postgresql://localhost:5432/airbnb_clone
DB_USERNAME=your_db_username
DB_PASSWORD=your_db_password

# Encryption Salt (e.g., BCrypt rounds)
ROUND=10

# JWT Configuration
JWT_ALGORITHM_KEY=your_secret_key
JWT_ISSUER=your_app_name
JWT_EXPIRY_IN_SECOND=86400
```

### 3. Run the Application
```shell
./mvnw spring-boot:run
```

The backend will start on:\
📍 http://localhost:8080

### 4. API Testing
You can test the APIs using tools like:

- Postman

### 5. Running the unit tests
```shell
./mvnw test
```

### 📌 API Endpoints

#### 🔐 Authentication
| Method | Endpoint         | Description            |
|--------|------------------|------------------------|
| POST   | `/register`      | Register new user      |
| POST   | `/login`         | Login and get token    |

#### 🏨 Homestays
| Method | Endpoint                          | Description                           |
|--------|-----------------------------------|---------------------------------------|
| GET    | `/homestays`                      | List all homestays                    |
| GET    | `/homestays/{homestayId}`         | Get details of a specific homestay    |
| GET    | `/me/homestays`                   | List homestays owned by current user  |
| POST   | `/me/homestays`                   | Create a new homestay (as host)       |
| PATCH  | `/me/homestays/{homestayId}`      | Update homestay owned by user         |

#### 📆 Bookings
| Method | Endpoint                                    | Description                         |
|--------|---------------------------------------------|-------------------------------------|
| POST   | `/bookings`                                 | Create a booking                    |
| GET    | `/me/bookings`                              | Get all bookings by current user    |
| GET    | `/me/bookings/{bookingId}`                  | Get booking detail by ID            |
| PATCH  | `/host/bookings/{bookingId}/confirm`        | Host confirms a booking             |

#### 🗺️ Location
| Method | Endpoint                                | Description                         |
|--------|-----------------------------------------|-------------------------------------|
| GET    | `/cities`                               | Get all cities                      |
| GET    | `/cities/{cityId}/districts`            | Get districts of a city             |
| GET    | `/districts/{districtId}/wards`         | Get wards of a district             |

#### 👤 User
| Method | Endpoint      | Description              |
|--------|---------------|--------------------------|
| GET    | `/me`         | Get current user profile |
| PATCH  | `/me`         | Update user profile      |
