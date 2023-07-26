import React, { createContext, useState, useEffect } from "react";
import axios from 'axios';

const AuthContext = createContext();

const AuthContextProvider = ({ children }) => {
    const [authState, setAuthState] = useState({
        isAuthenticated: false,
        token: null,
    });

    const handleLogin = async (email, password) => {
        try {
            const response = await axios.post('/api/login', {
                email,
                password,
            });
            const { token } = response.data;
            setAuthState(prevState => ({
                ...prevState,
                isAuthenticated: true,
                token,
            }));
        } catch (error) {
            console.log(error);
        }
    };

    const handleLogout = () => {
        setAuthState(prevState => ({
            ...prevState,
            isAuthenticated: false,
            token: null,
        }));
    };

    useEffect(() => {
        const checkAuthStatus = async () => {
            try {
                if (authState.token) {
                    axios.defaults.headers.common['Authorization'] = `Bearer ${authState.token}`;
                } else {
                    delete axios.defaults.headers.common['Authorization'];
                }

                const response = await axios.get('/api/check-auth');
                setAuthState(prevState => ({
                    ...prevState,
                    isAuthenticated: true,
                }));
            } catch (error) {
                setAuthState(prevState => ({
                    ...prevState,
                    isAuthenticated: false,
                    token: null,
                }));
            }
        };
        checkAuthStatus();
    }, [authState.token]);

    return (
        <AuthContext.Provider
            value={{
                isAuthenticated: authState.isAuthenticated,
                token: authState.token,
                login: handleLogin,
                logout: handleLogout,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};

export { AuthContext, AuthContextProvider };
