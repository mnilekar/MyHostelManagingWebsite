# Hostel Room Management (Phase 1 Starter)

Production-ready starter template for a **Hostel Room Management** platform with:

- **Frontend**: React + TypeScript + React Router
- **Backend**: Java 17 + Spring Boot microservice (`auth-service`) in Maven multi-module setup
- **Database**: Oracle XE (schema user: `HOSTEL_APP`)

## Project Structure

```text
.
├── frontend/
├── backend/
│   ├── pom.xml
│   └── auth-service/
├── db/
│   └── 01_schema.sql
└── README.md
```

## Functional Coverage (Phase 1)

- Home page with welcome and company section + Home/Register/Login tabs.
- Register choice page with:
  - Register As User
  - Register As Employee
  - Register Your Hostel (Owner)
- Role-specific registration forms with required defaults, field behavior, password policy, and success redirect.
- Login page with role-based post-login routing.
- OWNER users land on `/owner/dashboard` (owner-only page with profile hover card, right-side tabs, and logout).
- USER/EMPLOYEE continue to use the placeholder `/dashboard` with logout.
- Backend REST APIs for registration and login.
- Backend validation, password hashing (BCrypt), and username/email uniqueness.

## Prerequisites

- Node.js 20+
- Java 17+
- Maven 3.9+
- Oracle XE / Oracle DB with accessible PDB/service

## Database Setup

Use the `HOSTEL_APP` schema and run:

```bash
sqlplus HOSTEL_APP/Hostel@123@//localhost:1521/XEPDB1 @db/01_schema.sql
```

> If your service name differs (example: `ORCLPDB1`), update the connect string accordingly.

### DB connection reference

- Host: `localhost`
- Port: `1521`
- Service Name: `XEPDB1` (or `ORCLPDB1` if your environment uses that)
- Username: `HOSTEL_APP`
- Password: `Hostel@123`

## Backend Run

```bash
cd backend
export ORACLE_URL='jdbc:oracle:thin:@//localhost:1521/XEPDB1'
export ORACLE_USERNAME='HOSTEL_APP'
export ORACLE_PASSWORD='Hostel@123'
mvn -pl auth-service spring-boot:run
```

Service starts at `http://localhost:8080`.

### Backend API Endpoints

- `POST /api/auth/register/user`
- `POST /api/auth/register/employee`
- `POST /api/auth/register/owner`
- `POST /api/auth/login` (returns token + user details including role)

### Sample curl requests

```bash
curl -X POST http://localhost:8080/api/auth/register/user \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName":"Amit",
    "surname":"Shah",
    "email":"amit.user@example.com",
    "nationality":"India",
    "countryCode":"+91",
    "mobileNumber":"9876543210",
    "stateName":"Maharashtra",
    "cityName":"Pune",
    "idProofType":"AADHAR_CARD",
    "idProofValue":"1234567890",
    "username":"AmitShah",
    "password":"Abcd@123",
    "confirmPassword":"Abcd@123"
  }'

curl -X POST http://localhost:8080/api/auth/login \
  -H 'Content-Type: application/json' \
  -d '{"username":"AmitShah","password":"Abcd@123"}'
```

## Frontend Run

```bash
cd frontend
npm install
npm run dev
```

Frontend starts at `http://localhost:5173`.

If backend is on a different host/port, set:

```bash
export VITE_API_BASE_URL='http://localhost:8080'
```

## Notes

- Dropdown location values are currently hardcoded with placeholders for future DB-driven master-data integration.
- Password policy enforced in both frontend and backend:
  - exactly 8 chars
  - at least 1 uppercase, 1 lowercase, 1 number, 1 special char
- Passwords are stored using BCrypt hash.


## Troubleshooting: "Failed to create PR"

If GitHub shows **Failed to create PR**, verify these checks:

1. Ensure your repository has a remote configured:
   ```bash
   git remote -v
   ```
   If empty, add your origin:
   ```bash
   git remote add origin <your-github-repo-url>
   ```

2. Ensure your feature branch is pushed:
   ```bash
   git push -u origin <your-branch-name>
   ```

3. Ensure your branch is ahead of target branch and has commits:
   ```bash
   git log --oneline origin/main..HEAD
   ```

4. Re-open PR URL directly:
   ```text
   https://github.com/<owner>/<repo>/compare/main...<your-branch-name>?expand=1
   ```

In this workspace, `git remote -v` was empty, which commonly causes PR creation failure until an origin is set and branch is pushed.
