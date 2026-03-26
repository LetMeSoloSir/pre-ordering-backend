import axios from "axios";

const BASE_URL = "http://localhost:8080/ordering/api/cart";

export const cartAPI = {

    getCart: (userId) =>
        axios.get(`${BASE_URL}/user/${userId}`),

    addToCart: async (data) => {
        return axios.post(
            "http://localhost:8080/ordering/api/cart/add",
            data
        );
    },

    updateCart: (userId, productId, quantity) =>
        axios.put(`${BASE_URL}/update`, {
            userId,
            productId,
            quantity
        }),

    removeCart: (userId, productId) =>
        axios.delete(`${BASE_URL}/remove`, {
            data: { userId, productId }
        }),

    clearCart: (userId) =>
        axios.delete(`${BASE_URL}/clear/${userId}`)
};