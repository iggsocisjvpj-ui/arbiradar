# Arbiradar - Mobile & Backend Application

A complete full-stack application with Android mobile frontend and Node.js backend API.

## Project Structure

```
arbiradar/
├── android/              # Android mobile application (APK)
├── backend/             # Node.js/Express API server
├── docs/                # Documentation
└── README.md           # This file
```

## Features

- **Mobile App**: Native Android application for iOS and Android devices
- **Backend API**: RESTful API with authentication and data management
- **Database**: MongoDB integration for data persistence
- **Real-time**: WebSocket support for real-time updates
- **Authentication**: JWT-based user authentication

## Getting Started

### Prerequisites

- Android Studio 4.0+
- Node.js 16.0+
- MongoDB 4.0+
- Git

### Quick Setup

#### Android App Setup
```bash
cd android
# Open in Android Studio or build via command line
./gradlew assembleDebug
```

#### Backend Setup
```bash
cd backend
npm install
npm start
```

## API Documentation

See `/docs/API.md` for complete API reference.

## Development

- **Android**: Kotlin/Java with Jetpack Compose
- **Backend**: Express.js with TypeScript
- **Database**: MongoDB with Mongoose
- **Authentication**: JWT tokens

## License

MIT License

## Contact

For questions or support, contact: iggsocisjvpj-ui