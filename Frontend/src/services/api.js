/**
 * API Service - Handles all communication with the backend
 * Base URL: http://localhost:8080/ordering
 */

const API_BASE_URL = 'http://localhost:8080/ordering';

/**
 * Read saved auth token from localStorage (set after login)
 */
function getAccessToken() {
  try {
    const raw = localStorage.getItem('auth');
    if (!raw) return null;
    const parsed = JSON.parse(raw);
    return parsed?.accessToken || null;
  } catch {
    return null;
  }
}

/**
 * Helper function to make API requests
 * @param {string} endpoint - API endpoint path
 * @param {object} options - Fetch options (method, body, etc.)
 * @returns {Promise} - Response data
 */
async function apiRequest(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`;
  
  // Default headers
  const headers = {
    'Content-Type': 'application/json',
    // If user is logged in, send JWT to backend
    ...(getAccessToken() ? { Authorization: `Bearer ${getAccessToken()}` } : {}),
    ...options.headers,
  };

  // Prepare fetch options
  const fetchOptions = {
    ...options,
    headers,
  };

  try {
    const response = await fetch(url, fetchOptions);
    const data = await response.json();
    
    // Check if response is successful
    if (data.status === 'SUCCESS' && data.code === 200) {
      return data.data;
    } else {
      throw new Error(data.message || 'API request failed');
    }
  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

/**
 * Product API functions
 */
export const productAPI = {

    getProducts: async (page = 0, size = 20, sort = 'productName') => {
        return apiRequest(`/api/product/list?page=${page}&size=${size}&sort=${sort}`);
    },

    getProductDetail: async (productId) => {
        return apiRequest('/api/product/detail', {
            method: 'POST',
            body: JSON.stringify({ productId }),
        });
    },

    filterProducts: async (filterRequest) => {
        return apiRequest('/api/product/filter', {
            method: 'POST',
            body: JSON.stringify(filterRequest),
        });
    },

    // ✅ THÊM CÁI NÀY
    getRecentViewed: async () => {
        return apiRequest('/api/product/recent-viewed');
    }

};

/**
 * User API functions
 */
export const userAPI = {

    /**
     * Get logged-in user's profile information
     */
    getUserProfile: async () => {
        return apiRequest('/api/account/me');
    },

    /**
     * Update user profile
     */
    updateUserProfile: async (userData) => {
        return apiRequest('/api/account/update', {
            method: 'PUT',
            body: JSON.stringify(userData),
        });
    },

};

export const cartAPI = {
    getCart: async (userId) => apiRequest(`/api/cart/${userId}`),
    updateCart: async (userId, productId, quantity) =>
        apiRequest(`/api/cart/${userId}/${productId}?quantity=${quantity}`, { method: "PUT" }),
    removeCart: async (userId, productId) =>
        apiRequest(`/api/cart/${userId}/${productId}`, { method: "DELETE" }),
};

// Order API
export const orderAPI = {
    createOrder: async (createOrderRequest) =>
        apiRequest("/api/orders", {
            method: "POST",
            body: JSON.stringify(createOrderRequest),
        }),
    payWithVnPay: async (orderId) =>
        apiRequest(`/api/payments/vnpay/${orderId}`, { method: "POST" }),
};
