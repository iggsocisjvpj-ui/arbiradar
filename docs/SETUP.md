# Setup Guide

## Prerequisites

- Node.js 16.0+
- MongoDB 4.0+
- Android Studio 4.0+
- Git

## Backend Setup

### 1. Install Dependencies
```bash
cd backend
npm install
```

### 2. Configure Environment
```bash
cp .env.example .env
```

Edit `.env` with your configuration:
```
PORT=3000
MONGODB_URI=mongodb://localhost:27017/arbiradar
JWT_SECRET=your-secret-key-here
```

### 3. Start MongoDB
```bash
# Using Docker
docker run -d -p 27017:27017 --name mongodb mongo:latest

# Or local MongoDB
mongod
```

### 4. Build & Run
```bash
npm run build
npm start
```

Backend will run on `http://localhost:3000`

## Android Setup

### 1. Open in Android Studio
```bash
cd android
open . # macOS
start . # Windows
```

### 2. Update API Configuration
Edit `src/network/ApiClient.kt` and update `BASE_URL`:
```kotlin
private const val BASE_URL = "http://your-backend-url:3000/api/"
```

### 3. Build Debug APK
```bash
./gradlew assembleDebug
```

APK will be in: `android/app/build/outputs/apk/debug/`

### 4. Build Release APK
```bash
./gradlew assembleRelease
```

## Testing

### Backend
```bash
cd backend
npm test
```

### API
Use Postman or cURL:
```bash
curl -X POST http://localhost:3000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"test123","name":"Test User"}'
```

## Deployment

### Docker
```bash
# Build
docker build -t arbiradar-backend:latest .

# Run
docker run -p 3000:3000 --env-file .env arbiradar-backend:latest
```

### Android Play Store
1. Generate signing key
2. Build release APK
3. Upload to Play Store Console
