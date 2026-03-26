import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import Homepage from './components/Homepage';
import ProductListing from './components/ProductListing';
import ProductDetail from './components/ProductDetail';
import UserProfile from './components/UserProfile';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import CartPage from "./pages/CartPage";
import { clearAuth } from './services/auth';
import CheckoutPage from "./pages/CheckoutPage";
import PaymentSuccess from "./pages/PaymentSuccess";
import './App.css';
import AdminOrdersPage from "./pages/admin/AdminOrdersPage";

function App() {

    useEffect(() => {

        if (!sessionStorage.getItem("app_started")) {
            clearAuth();
            sessionStorage.setItem("app_started", "true");
        }

    }, []);

    return (
        <Router>

            <div className="App">

                <Navigation />

                <main className="main-content">

                    <Routes>

                        <Route path="/" element={<Homepage />} />
                        <Route path="/products" element={<ProductListing />} />
                        <Route path="/products/:id" element={<ProductDetail />} />
                        <Route path="/profile" element={<UserProfile />} />
                        <Route path="/login" element={<LoginPage />} />
                        <Route path="/register" element={<RegisterPage />} />

                        <Route path="/cart" element={<CartPage />} />
                        <Route path="/checkout" element={<CheckoutPage />} />
                        <Route path="/payment-success" element={<PaymentSuccess />} />
                        <Route path="/admin/orders" element={<AdminOrdersPage />} />

                    </Routes>

                </main>

            </div>

        </Router>
    );
}

export default App;