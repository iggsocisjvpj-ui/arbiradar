# Arbiradar API Documentation

## Base URL
```
http://localhost:3000/api
```

## Authentication
All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

## Endpoints

### Authentication

#### Register User
- **POST** `/auth/register`
- **Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123",
    "name": "John Doe"
  }
  ```
- **Response**: `200 OK`
  ```json
  {
    "success": true,
    "token": "jwt_token",
    "user": {"id": "...", "email": "...", "name": "..."}
  }
  ```

#### Login
- **POST** `/auth/login`
- **Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
- **Response**: `200 OK`
  ```json
  {
    "success": true,
    "token": "jwt_token",
    "user": {"id": "...", "email": "...", "name": "..."}
  }
  ```

### Users (Protected)

#### Get Profile
- **GET** `/users/profile`
- **Response**: `200 OK`
  ```json
  {
    "id": "...",
    "email": "...",
    "name": "...",
    "phone": "...",
    "avatar": "..."
  }
  ```

#### Update Profile
- **PUT** `/users/profile`
- **Body**:
  ```json
  {
    "name": "Updated Name",
    "phone": "+1234567890",
    "avatar": "url_to_avatar"
  }
  ```

### Data Management (Protected)

#### Get Data List
- **GET** `/data/list?page=1`
- **Response**: `200 OK`
  ```json
  {
    "data": [...],
    "total": 100,
    "page": 1,
    "pages": 10
  }
  ```

#### Get Single Data
- **GET** `/data/{id}`

#### Create Data
- **POST** `/data/create`
- **Body**:
  ```json
  {
    "title": "Data Title",
    "description": "Data Description",
    "content": "Optional content"
  }
  ```

#### Update Data
- **PUT** `/data/{id}`
- **Body**: Same as Create

#### Delete Data
- **DELETE** `/data/{id}`

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Validation error"
}
```

### 401 Unauthorized
```json
{
  "success": false,
  "message": "Invalid token"
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "Resource not found"
}
```

### 500 Server Error
```json
{
  "success": false,
  "message": "Internal Server Error"
}
```

## WebSocket Events

### Connection
```javascript
const socket = io('http://localhost:3000');
socket.on('connect', () => console.log('Connected'));
```

### Send Message
```javascript
socket.emit('message', { text: 'Hello' });
```

### Receive Message
```javascript
socket.on('message', (data) => console.log(data));
```
