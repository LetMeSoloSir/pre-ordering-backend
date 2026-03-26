import React, { useState, useEffect } from "react";
import { userAPI } from "../services/api";
import "./UserProfile.css";

function UserProfile() {

    const [user, setUser] = useState(null);
    const [formData, setFormData] = useState({});
    const [isEditing, setIsEditing] = useState(false);

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        fetchUserProfile();
    }, []);

    const fetchUserProfile = async () => {
        try {
            setLoading(true);
            const userData = await userAPI.getUserProfile();
            setUser(userData);
            setFormData(userData);
        } catch (err) {
            setError("Failed to load user profile");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleEdit = () => {
        setIsEditing(true);
    };

    const handleSave = async () => {
        try {
            const updatedUser = await userAPI.updateUserProfile(formData);
            setUser(updatedUser);
            setIsEditing(false);
            alert("Profile updated successfully!");
        } catch (error) {
            console.error(error);
            alert("Update failed!");
        }
    };

    const handleCancel = () => {
        setFormData(user);
        setIsEditing(false);
    };

    if (loading) return <div className="loading">Loading profile...</div>;
    if (error) return <div className="error">{error}</div>;
    if (!user) return <div>No user data</div>;

    return (
        <div className="account-container">

            <div className="account-sidebar">

                <div className="user-box">
                    <img
                        src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                        alt="avatar"
                    />
                    <div>
                        <h3>{user.username}</h3>
                        <p className="logout">Đăng xuất</p>
                    </div>
                </div>

                <ul className="menu">
                    <li className="active">Thông tin tài khoản</li>

                    <li>Lịch sử đơn hàng</li>
                </ul>

            </div>

            <div className="account-content">

                <h2>Thông tin tài khoản</h2>

                <div className="profile-grid">

                    <div className="avatar-box">
                        <img
                            src="https://cdn-icons-png.flaticon.com/512/3135/3135715.png"
                            alt="avatar"
                        />
                    </div>

                    <div className="form-area">

                        <div className="form-row">
                            <label>Họ và tên *</label>
                            <input
                                name="fullname"
                                value={formData.fullname || ""}
                                onChange={handleChange}
                                disabled={!isEditing}
                            />
                        </div>

                        <div className="form-row">
                            <label>Email *</label>
                            <input value={user.email || ""} disabled />
                        </div>

                        <div className="form-row">
                            <label>Số điện thoại *</label>
                            <input
                                name="phone"
                                value={formData.phone || ""}
                                onChange={handleChange}
                                disabled={!isEditing}
                            />
                        </div>

                        <div className="form-row">
                            <label>Role</label>
                            <input value={user.role || ""} disabled />
                        </div>

                        <div className="form-row">
                            <label>Status</label>
                            <input value={user.isActive ? "Active" : "Inactive"} disabled />
                        </div>

                        <div className="profile-buttons">

                            {!isEditing && (
                                <button className="edit-btn" onClick={handleEdit}>
                                    Edit
                                </button>
                            )}

                            {isEditing && (
                                <>
                                    <button className="save-btn" onClick={handleSave}>
                                        Save
                                    </button>

                                    <button className="cancel-btn" onClick={handleCancel}>
                                        Cancel
                                    </button>
                                </>
                            )}

                        </div>

                    </div>

                </div>

            </div>

        </div>
    );
}

export default UserProfile;