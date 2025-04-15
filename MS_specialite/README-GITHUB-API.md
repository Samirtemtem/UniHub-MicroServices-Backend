# GitHub API Integration for MS_specialite

This document provides information about the GitHub API integration for the MS_specialite microservice, which allows you to fetch relevant open-source projects related to different academic specializations.

## Overview

The GitHub API integration allows you to:
- Search for repositories related to a specific specialization
- Get top repositories sorted by stars
- Find repositories by specialization ID
- Get cached results for better performance

## Endpoints

### 1. Get Repositories by Specialization Name

Retrieves GitHub repositories related to a specific specialization keyword.

- **URL**: `/github/repositories/{specialization}`
- **Method**: `GET`
- **URL Parameters**: 
  - `specialization`: The specialization name to search for (e.g., "computer-science", "data-science", "medicine")
- **Response**: List of GitHub repositories related to the specialization

**Example Request**:
```
GET /github/repositories/computer-science
```

**Example Response**:
```json
[
  {
    "id": 12345678,
    "name": "awesome-computer-science",
    "fullName": "username/awesome-computer-science",
    "description": "A curated list of Computer Science resources",
    "htmlUrl": "https://github.com/username/awesome-computer-science",
    "language": "Python",
    "stargazersCount": 5000,
    "forksCount": 1200,
    "createdAt": "2020-01-01T00:00:00Z",
    "updatedAt": "2023-05-15T12:30:45Z"
  },
  // ... more repositories
]
```

### 2. Get Top Repositories by Specialization Name

Retrieves the top N repositories for a specific specialization, sorted by star count.

- **URL**: `/github/repositories/{specialization}/top/{limit}`
- **Method**: `GET`
- **URL Parameters**:
  - `specialization`: The specialization name to search for
  - `limit`: The maximum number of repositories to return
- **Response**: List of top GitHub repositories related to the specialization

**Example Request**:
```
GET /github/repositories/data-science/top/5
```

**Example Response**:
```json
[
  {
    "id": 87654321,
    "name": "data-science-handbook",
    "fullName": "username/data-science-handbook",
    "description": "Data Science Handbook with Python",
    "htmlUrl": "https://github.com/username/data-science-handbook",
    "language": "Python",
    "stargazersCount": 10000,
    "forksCount": 3000,
    "createdAt": "2019-05-10T00:00:00Z",
    "updatedAt": "2023-06-20T15:45:30Z"
  },
  // ... 4 more repositories
]
```

### 3. Get Repositories by Specialization ID

Retrieves GitHub repositories related to a specialization in your database.

- **URL**: `/github/repositories/specialite/{specialiteId}`
- **Method**: `GET`
- **URL Parameters**:
  - `specialiteId`: The ID of the specialization in your database
- **Response**: List of GitHub repositories related to the specialization

**Example Request**:
```
GET /github/repositories/specialite/1
```

**Example Response**:
Same format as previous endpoints

### 4. Get Top Repositories by Specialization ID

Retrieves the top N repositories related to a specialization in your database.

- **URL**: `/github/repositories/specialite/{specialiteId}/top/{limit}`
- **Method**: `GET`
- **URL Parameters**:
  - `specialiteId`: The ID of the specialization in your database
  - `limit`: The maximum number of repositories to return
- **Response**: List of top GitHub repositories related to the specialization

**Example Request**:
```
GET /github/repositories/specialite/1/top/5
```

**Example Response**:
Same format as previous endpoints

## Authentication

The GitHub API has rate limits that may affect the functionality:
- Without authentication: 60 requests per hour
- With authentication: 5,000 requests per hour

To increase the rate limit, you can add a GitHub personal access token in the `application.properties` file:
```
github.api.token=your_github_token_here
```

## Caching

The API uses Caffeine cache to improve performance and reduce the number of actual API calls to GitHub. The cache TTL (Time To Live) is set to 30 minutes by default.

## Error Handling

- If the specialization ID doesn't exist in your database, the API will return a 500 error with a message indicating that the specialization was not found.
- If GitHub API rate limits are exceeded, appropriate error messages will be returned.

## Integration with Frontend

You can integrate these endpoints with your frontend to:
1. Display related GitHub projects on each specialization's detail page
2. Create a dedicated "Open Source Projects" tab for each specialization
3. Add filtering options by programming language, stars, or recent activity

## Security Considerations

1. Do not expose your GitHub API token in client-side code
2. Consider implementing additional authorization for these endpoints if needed
3. Monitor API usage to ensure you're not exceeding rate limits 