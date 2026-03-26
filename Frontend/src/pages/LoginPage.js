import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { loginWithPassword } from '../services/auth';
import './Auth.css';

export default function LoginPage() {
    const [usernameOrEmail, setUsernameOrEmail] = useState('');
    const [password, setPassword] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [error, setError] = useState('');

    async function handleSubmit(e) {
        e.preventDefault();
        setError('');

        if (!usernameOrEmail.trim() || !password) {
            setError('Please enter your email/username and password.');
            return;
        }

        try {
            setIsSubmitting(true);

            // login và lưu auth
            const auth = await loginWithPassword({
                usernameOrEmail: usernameOrEmail.trim(),
                password
            });

            // check role từ user object (đã fix trong auth.js)
            if (auth?.user?.role === "ADMIN") {
                window.location.href = "/admin/orders";
            } else {
                window.location.href = "/";
            }

        } catch (err) {
            setError(err?.message || 'Login failed');
        } finally {
            setIsSubmitting(false);
        }
    }

    return (
        <div className="auth-container">
            <div className="auth-card">
                <h2 className="auth-title">Welcome Back</h2>

                <form onSubmit={handleSubmit}>
                    <div className="auth-field">
                        <label>Email or Username</label>
                        <input
                            type="text"
                            value={usernameOrEmail}
                            onChange={(e) => setUsernameOrEmail(e.target.value)}
                            placeholder="Enter your email or username"
                        />
                    </div>

                    <div className="auth-field">
                        <label>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Enter your password"
                        />
                    </div>

                    <button className="auth-button" type="submit" disabled={isSubmitting}>
                        {isSubmitting ? 'Logging in...' : 'Login'}
                    </button>
                </form>

                {error && <div className="auth-error">{error}</div>}

                <div className="auth-links">
                    Don’t have an account? <Link to="/register">Register</Link>
                </div>
            </div>
        </div>
    );
}