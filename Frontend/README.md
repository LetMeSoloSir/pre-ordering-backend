# Frontend - Online Ordering System

A React-based frontend application for an online ordering system. Built with basic React concepts suitable for university-level learning.

## Features

- **Homepage**: Basic introduction of the website with navigation links
- **Product Listing**: Browse all available products with pagination
- **Product Detail**: View detailed product information
- **User Profile**: Display logged-in user's information

## Technology Stack

- React 18.2.0
- React Router DOM 6.8.0 (for navigation)
- Basic CSS (no external UI libraries)
- Fetch API (for backend communication)

## Project Structure

```
frontend/
├── public/
│   └── index.html          # HTML template
├── src/
│   ├── components/         # React components
│   │   ├── Homepage.js     # Homepage component
│   │   ├── ProductListing.js  # Product list page
│   │   ├── ProductDetail.js   # Product detail page
│   │   ├── UserProfile.js  # User profile page
│   │   └── Navigation.js   # Navigation bar
│   ├── services/
│   │   └── api.js          # API service for backend communication
│   ├── App.js              # Main app component with routing
│   ├── App.css             # App styles
│   ├── index.js            # Entry point
│   └── index.css           # Global styles
├── package.json            # Dependencies and scripts
└── README.md              # This file
```

## Setup Instructions

### Prerequisites

- Node.js (version 14 or higher)
- npm or yarn package manager
- Backend server running on `http://localhost:8080/ordering`

### Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Start the development server:
   ```bash
   npm start
   ```

4. The application will open in your browser at `http://localhost:3000`

## API Configuration

The frontend communicates with the backend API. The base URL is configured in `src/services/api.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/ordering';
```

Make sure the backend server is running before using the frontend.

## Available Scripts

- `npm start` - Start development server
- `npm build` - Build for production
- `npm test` - Run tests

## Important Notes

### API Endpoints Used

- `GET /api/product/list` - Get all products
- `POST /api/product/detail` - Get product details
- `GET /api/account/me` - Get logged-in user profile information

## Code Style

- Uses functional components only
- Uses `useState` and `useEffect` hooks
- Clear variable and function names
- Comments for learning purposes
- Simple and readable code structure

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)
