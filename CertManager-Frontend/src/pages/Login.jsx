import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const Login = ({setUser}) => {
    const baseUrl = 'http://172.16.254.21:8081/api';
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const navigate = useNavigate();

    const checkLogin = async () => {
        const loginData = {
            email: email,
            password: password
        }
        try {
            const response = await axios.post(baseUrl + "/auth/login", loginData);
            localStorage.setItem('user', JSON.stringify(response.data));
            setUser(response.data);
        } catch (error) {
            toast.error(error.response.data.message, {
                position: "bottom-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: false
            });
        }
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        await checkLogin();

        const userFromLocalStorage = JSON.parse(localStorage.getItem('user'));
        if (userFromLocalStorage) {
            navigate('/dashboard');
        } else {
            console.log('User not logged in');  
        }
    }

    // If user is already logged in, redirect to dashboard
    useEffect (() => {
        const userFromLocalStorage = localStorage.getItem('user');
        if (userFromLocalStorage) {
            navigate('/dashboard');
        }
    },[navigate]);

    return (
        <>
            {/* Login form with email and password, and a link to register */}
            <div className="main-area">
                <div className="login-form">
                    <form onSubmit={handleSubmit}>
                        <div className="login-form-item">
                            {email === '' &&
                                <label className='login-form-label' htmlFor="email">Email</label>
                            }
                            <input
                                className="login-form-input"
                                type="email"
                                id="email" 
                                onChange={(e) => setEmail(e.target.value)}
                                />
                        </div>
                        <div className="login-form-item">
                            {password === '' &&
                                <label className='login-form-label' htmlFor="password">Password</label>
                            }
                            <input
                                className="login-form-input"
                                type="password"
                                id="password"
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>
                        <div className="login-form-item">
                            <button className="login-form-button" type="submit">Login</button>
                        </div>
                    </form>
                </div>
                <div className="login-register-message">
                    <p>Not a member yet? <a href="/register">Register</a></p>
                </div>
            </div>
            <ToastContainer />
        </>
    );
}

export default Login;

