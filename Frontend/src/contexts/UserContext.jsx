import React, { createContext, useState } from "react";

// Tạo context
export const UserContext = createContext(null);

// Provider bọc toàn bộ app
export const UserProvider = ({ children }) => {
    // user = null nếu chưa login, object { id, fullName, email } nếu login
    const [user, setUser] = useState(null);

    return (
        <UserContext.Provider value={{ user, setUser }}>
            {children}
        </UserContext.Provider>
    );
};