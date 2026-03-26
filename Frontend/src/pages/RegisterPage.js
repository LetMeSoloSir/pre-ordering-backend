import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Auth.css";

export default function RegisterPage() {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");

    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");

    async function handleRegisterClick(e) {
        e.preventDefault();

        setError("");
        setSuccess("");

        if (!username || !email || !password || !confirmPassword) {
            setError("Please fill all fields");
            return;
        }

        if (password !== confirmPassword) {
            setError("Passwords do not match");
            return;
        }

        try {
            const res = await fetch("http://localhost:8080/ordering/api/auth/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    username: username,
                    email: email,
                    password: password
                })
            });

            const data = await res.json();

            if (!res.ok) {
                throw new Error(data.message || "Register failed");
            }

            setSuccess("Account created successfully!");

        } catch (err) {
            setError(err.message);
        }
    }

    return (
        <div className="auth-container">

            <div className="auth-card">

                <h2 className="auth-title">Create Account</h2>

                <p className="auth-subtitle">
                    Join FlowerCorner and start sending beautiful flowers 🌸
                </p>

                <form>

                    <div className="auth-field">
                        <label>Username</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            placeholder="Choose a username"
                        />
                    </div>

                    <div className="auth-field">
                        <label>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            placeholder="Enter your email"
                        />
                    </div>

                    <div className="auth-field">
                        <label>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            placeholder="Create a password"
                        />
                    </div>

                    <div className="auth-field">
                        <label>Confirm Password</label>
                        <input
                            type="password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                            placeholder="Re-enter your password"
                        />
                    </div>

                    <button className="auth-button" onClick={handleRegisterClick}>
                        Create Account
                    </button>

                </form>

                {error && <div className="auth-error">{error}</div>}
                {success && <div className="auth-success">{success}</div>}

                <div className="auth-links">
                    Already have an account? <Link to="/login">Login</Link>
                </div>

            </div>

        </div>
    );
}