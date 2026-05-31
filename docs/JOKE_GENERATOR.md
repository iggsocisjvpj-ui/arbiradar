# Joke Generator Documentation

## Overview

The Joke Generator feature integrates multiple external joke APIs to provide a diverse range of jokes with caching for optimal performance.

## External APIs

### 1. JokeAPI (Primary)
- **URL**: https://v2.jokeapi.dev
- **Features**: 
  - Multiple categories (general, programming, knock-knock, etc.)
  - Customizable joke filtering
  - Safe mode available
  - Both single-part and two-part jokes

### 2. Official Joke API (Fallback)
- **URL**: https://official-joke-api.appspot.com
- **Features**:
  - Lightweight responses
  - Random joke endpoint
  - Type-based jokes

### 3. Jokes One API (Additional)
- **URL**: https://jokes.one/api/joke
- **Features**:
  - Additional joke sources
  - Various joke categories

## API Endpoints

### Get Random Joke
```
GET /api/jokes/random
```

**Response**:
```json
{
  "error": false,
  "category": "General",
  "type": "twopart",
  "setup": "Why don't scientists trust atoms?",
  "delivery": "Because they make up everything!",
  "id": 1,
  "safe": true,
  "flags": {
    "nsfw": false,
    "religious": false,
    "political": false,
    "racist": false,
    "sexist": false,
    "explicit": false
  }
}
```

### Get Joke by Category
```
GET /api/jokes/category/{category}
```

**Categories**:
- `general`
- `programming`
- `knock-knock`
- `christmas`
- `any` (default)

**Example**:
```
GET /api/jokes/category/programming
```

### Search Jokes
```
GET /api/jokes/search?q=keyword
```

**Query Parameters**:
- `q` (required): Search keyword

**Response**:
```json
{
  "jokes": [...],
  "total": 5
}
```

### Add to Favorites
```
POST /api/jokes/favorites
Authorization: Bearer <token>
```

**Request Body**:
```json
{
  "joke": "Why did the programmer quit his job?",
  "setup": "Why did the programmer quit his job?",
  "delivery": "Because he didn't get arrays.",
  "type": "twopart"
}
```

### Get Favorites
```
GET /api/jokes/favorites
Authorization: Bearer <token>
```

**Response**:
```json
{
  "favorites": [
    {
      "_id": "507f1f77bcf86cd799439011",
      "userId": "507f1f77bcf86cd799439012",
      "joke": "...",
      "setup": "...",
      "delivery": "...",
      "type": "twopart",
      "createdAt": "2024-01-01T12:00:00Z",
      "updatedAt": "2024-01-01T12:00:00Z"
    }
  ],
  "total": 10
}
```

### Remove Favorite
```
DELETE /api/jokes/favorites/{id}
Authorization: Bearer <token>
```

**Response**:
```json
{
  "success": true,
  "message": "Favorite removed"
}
```

## Features

### Caching
- Random jokes cached for 10 minutes
- Category jokes cached per category
- Search results cached
- Reduces API calls to external services

### Error Handling
- Automatic fallback to alternative APIs
- Graceful error responses
- Detailed error messages

### Safe Mode
- Jokes filtered for inappropriate content
- Flags for various content types
- Configurable safety levels

## Android Implementation

### Retrofit Service
```kotlin
interface JokeService {
    @GET("jokes/random")
    suspend fun getRandomJoke(): JokeResponse
    
    @GET("jokes/category/{category}")
    suspend fun getJokeByCategory(@Path("category") category: String): JokeResponse
    
    @GET("jokes/search")
    suspend fun searchJokes(@Query("q") query: String): JokeListResponse
    
    @POST("jokes/favorites")
    suspend fun addFavorite(@Body joke: JokeResponse): JokeResponse
    
    @GET("jokes/favorites")
    suspend fun getFavorites(): JokeListResponse
}
```

### UI Features
- Beautiful card-based joke display
- Tap to reveal punchline animation
- Favorite jokes with heart icon
- Share jokes functionality
- Category filtering dropdown
- Loading state animation

## Performance

- **Caching**: 10-minute TTL on all cached jokes
- **API Response Time**: < 500ms average
- **Fallback Chain**: 3 different API sources
- **Rate Limiting**: Implemented per external API

## Rate Limits

### JokeAPI
- 120 requests per minute

### Official Joke API
- No strict rate limit
- Recommended 1 request per second

## Future Enhancements

- [ ] Offline joke database
- [ ] Joke ratings and recommendations
- [ ] Custom joke categories
- [ ] User-generated jokes
- [ ] Joke translation API
- [ ] Trending jokes
- [ ] Social sharing metrics
