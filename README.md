☢️ DoseSync – Backend v1

Planning of the patients in the Nuclear Department for better treatment and cost.
It allows one to plan today's or week's schedule - when to inject, how much to inject, and see how much remains after injection.
It also has calculations to improve the amount that can be injected.

There are user management, profile, and admin settings for calculation, hospital, and isotope management.
Additionally, DoseSync provides user management with two roles:  
- **Admins** (Doctors or Physicists) who can create and modify plans  
- **Technicians** who can view results only
Information then can be stored in the local database or saved as PDF.
[2025-06-03.FPyl study-29.pdf](https://github.com/user-attachments/files/20592176/2025-06-03.FPyl.study-29.pdf)

----------------------------------------------------------------------------
![HomeView](https://github.com/user-attachments/assets/0168bd82-bac0-47ab-bf3f-b5cfeff6674e)
![StudyView](https://github.com/user-attachments/assets/abfe7e0b-73b7-4cc3-bb30-4ddc7441fc6a)
![AdminView](https://github.com/user-attachments/assets/8b430a67-bddf-4925-9b6c-317ca2348c42)
DoseSync includes a separate frontend application for managing schedules, dose planning, and user roles.
The frontend communicates with this backend via REST APIs, repository is available at:  
`https://github.com/andy-reiu/dosesyncfront`

DoseSync is built with Spring Boot and Java 21. 
This repository contains the server-side codebase that handles data storage, REST APIs, and application logic.
🚀 Features
- Planning and optimization of nuclear medicine patient doses  
- Scheduling for daily or weekly injection plans  
- User management with Admin and Technician roles  
- Hospital, isotope, and calculation management by Admins  
- Data storage in PostgreSQL
- Export schedules and dose info as PDFs  
- REST API with validation and Swagger/OpenAPI docs  

🛠️ Tech Stack
- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL / H2
- Swagger (OpenAPI 3)
- MapStruct
- Lombok

### Prerequisites
- Java 21+
- Gradle (or use the wrapper)
- PostgreSQL (if not using H2)
- IDE (e.g., IntelliJ IDEA)

Getting Started

### Setup Database

1. Create a PostgreSQL database named `dosesync` (or your preferred name).  
2. Create a user and grant appropriate privileges.  
3. Update `src/main/resources/application.properties` (or `application.yml`) with your PostgreSQL credentials:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dosesync
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
```

🚀 Roadmap

- Enhance authentication and authorization mechanisms (JWT)
- Backend validation
- Unit tests and H2 tests
- Add additional user roles with customized permissions  
- Implement a notification system to remind staff about scheduled injections
- Add comments between doctor and technician
- Develop a frontend application for easier interaction with the backend  
- Improve dose calculation algorithm for better accuracy  
- Add audit logging and activity tracking

## 👤 Authors

- **Andy Reiu** – *El Capitan* – [andy-reiu](https://github.com/andy-reiu)
- **Kevin Kuusk** – *El Equipo* – [baluuba](https://github.com/baluuba)
- **Olga Kuvatova** – *El Equipo* – [apelsina365](https://github.com/Apelsinka365)
