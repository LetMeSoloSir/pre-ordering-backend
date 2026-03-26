// auth.js
const KEYCLOAK_ISSUER_URL = 'http://localhost:8081/realms/master';

function getClientIdOrThrow() {
    const clientId = process.env.REACT_APP_KEYCLOAK_CLIENT_ID;
    if (!clientId) {
        throw new Error('Missing REACT_APP_KEYCLOAK_CLIENT_ID');
    }
    return clientId;
}

function decodeJwtPayload(token) {
    try {
        const parts = token.split('.');
        if (parts.length < 2) return null;
        const payload = parts[1].replace(/-/g, '+').replace(/_/g, '/');
        const json = decodeURIComponent(
            atob(payload)
                .split('')
                .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
                .join('')
        );
        return JSON.parse(json);
    } catch {
        return null;
    }
}

export function saveAuth(auth) {
    localStorage.setItem('auth', JSON.stringify(auth));
}

export function clearAuth() {
    localStorage.removeItem('auth');
}

export function getAuth() {
    try {
        const raw = localStorage.getItem('auth');
        return raw ? JSON.parse(raw) : null;
    } catch {
        return null;
    }
}

export async function loginWithPassword({ usernameOrEmail, password }) {
    const clientId = getClientIdOrThrow();
    const tokenUrl = `${KEYCLOAK_ISSUER_URL}/protocol/openid-connect/token`;

    const body = new URLSearchParams();
    body.set('grant_type', 'password');
    body.set('client_id', clientId);
    body.set('username', usernameOrEmail);
    body.set('password', password);

    const res = await fetch(tokenUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: body.toString()
    });

    const data = await res.json();

    if (!res.ok) {
        throw new Error(data?.error_description || 'Login failed');
    }

    const accessToken = data.access_token;
    const payload = accessToken ? decodeJwtPayload(accessToken) : null;

    // Lấy role admin từ token JWT
    let role = "USER"; // default
    if (payload?.realm_access?.roles?.includes("admin")) {
        role = "ADMIN"; // chuẩn hóa chữ hoa
    }

    // fetch thêm thông tin user từ backend
    const userRes = await fetch('http://localhost:8080/ordering/api/account/me', {
        headers: {
            'Authorization': `Bearer ${accessToken}`
        }
    });
    const userData = await userRes.json();
    const user = {
        ...userData.data,
        role // gán role từ token
    };

    const auth = {
        accessToken: data.access_token,
        refreshToken: data.refresh_token,
        tokenType: data.token_type,
        expiresIn: data.expires_in,
        user
    };

    saveAuth(auth);
    return auth;
}