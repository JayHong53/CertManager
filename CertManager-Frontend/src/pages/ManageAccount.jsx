import React, { useState, useEffect } from 'react';
import { Link, Navigate } from 'react-router-dom';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from 'axios';

const ManageAccount = () => {
    const baseUrl = "http://172.16.254.21:8081/api";
    const [user, setUser] = useState(null);
    const [candidate, setCandidate] = useState(null);
    const [isLoading, setIsLoading] = useState(true);

    useEffect(() => {
        const userFromLocalStorage = localStorage.getItem('user');
        if (userFromLocalStorage) {
            const parsedUser = JSON.parse(userFromLocalStorage);
            setUser(parsedUser); // Update user state with parsed user data
        }
        else {
            // Redirect to login page if user is not available in localStorage
            window.location.href = '/login';
        }
    }, []);

    useEffect(() => {
        if (user) {
            const fetchCandidate = async () => {
                try {
                    const response = await axios.get(baseUrl + "/candidate/" + user._id);
                    if (response.status === 200) {
                        setCandidate(response.data);
                        setIsLoading(false);
                    }
                } catch (error) {
                    toast.error(error.response.data.message, {
                        position: "bottom-center",
                        autoClose: 3000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: false
                    });
                }
            };
            fetchCandidate();
        }
    }, [user]);

    return (
        <div className='main-area'>
            {!candidate && <div className="loading">Loading...</div>}
            {candidate &&
            <div className='detail-container'>
                <h1 className="detail-title">User Profile</h1>
                <div className='reservation-detail'>
                    <table>
                        <tbody>
                            <tr>
                                <td className="td-label">User ID</td>
                                <td className="td-text">{candidate._id}</td>
                            </tr>
                            <tr>
                                <td className="td-label">Full Name</td>
                                <td className="td-text">{candidate.firstName} {candidate.lastName}</td>
                            </tr>
                            <tr>
                                <td className="td-label">Address 1</td>
                                <td className="td-text">{candidate.street}</td>
                            </tr>

                            <tr>
                                <td className="td-label">Address 2</td>
                                <td className="td-text">{candidate.city}, {candidate.province}</td>
                            </tr>
                            <tr>
                                <td className="td-label">Phone</td>
                                <td className="td-text">{candidate?.phone}</td>
                            </tr>
                        </tbody>
                    </table>
                </div>
                <Link to={'/dashboard'}>
                    <button className="dashboard-button">Back to main</button>
                </Link>
            </div>
            }
            <ToastContainer />
        </div>
    );
};

export default ManageAccount;
