# User Management Module

## Overview
This Android application provides user authentication and management functionality, along with a book management system. Users can sign up, log in, and manage their personal book collection.

## Features
- User Authentication (Login/Signup)
- Password Reset
- User Profile Management
- Book Management (Add/View)
  - Add books with external image URLs
  - Browse books in a grid layout
  - Download book covers
- Session Management

## Implementation Details

### Authentication
The application uses Firebase Authentication for user management:
- Email/Password authentication
- User data stored in Firestore

### Book Management
- Books are stored in Firestore with the following details:
  - Name
  - Description
  - Language
  - Publication date
  - Cover image (direct URL to external image sources)
- Book covers can be viewed in a grid similar to popular reading apps
- Users can download book covers by tapping on a book
- **Cost-Effective Approach**: Instead of using Firebase Storage (which has costs), the app uses direct links to images from external sources

### Navigation Flow
1. **Splash Screen**: Displays on app launch
2. **Welcome Screen**: Options to log in or sign up
3. **Login/Signup Screen**: User authentication
4. **Home Screen**: Central hub for navigation after login
5. **Book Management**: Add or view books in collection
   - **Book List**: Grid display of book covers and names
   - **Add Book**: Form to add new books with image URLs

### Components

#### Fragments
- `LoginFragment`: Handles user login
- `SignupFragment`: User registration
- `ForgotFragment`: Password reset
- `HomeFragment`: Main navigation screen after login
- `AddDataFragment`: Add new books to collection using external image URLs
- `BookListFragment`: Display books in a grid layout

#### Main Classes
- `MainActivity`: Core activity managing fragments
- `FirebaseServices`: Singleton for Firebase operations
- `DataUser`: User model class
- `Book`: Book model class
- `BookGridAdapter`: Adapter for displaying books in a grid
- `BookDownloadManager`: Utility for downloading book covers from external URLs

## Recent Changes
- Modified book addition to use external image URLs instead of Firebase Storage
- Added image URL preview functionality
- Added URL validation to ensure proper links are provided
- Enhanced error handling for image loading
- Added proper permissions management for downloading

## Development Notes
- Firebase services must be configured correctly in your project
- Ensure Google Services JSON file is properly set up
- The app uses AndroidX libraries
- Storage permissions are required for downloading books
- Images must be from publicly accessible URLs (http/https)

## Setup Instructions
1. Clone the repository
2. Open in Android Studio
3. Connect to Firebase (ensure google-services.json is in app directory)
4. Build and run

## Image URL Sources
For book covers, you can use image URLs from various sources:
- Public image hosting services (e.g., Imgur, Flickr)
- Book cover APIs
- Your own hosted images
- Any publicly accessible image URL 