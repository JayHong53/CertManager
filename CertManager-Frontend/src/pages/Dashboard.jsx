import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

const Dashboard = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {        
        const userFromLocalStorage = localStorage.getItem('user');
        if (userFromLocalStorage) {
            const parsedUser = JSON.parse(userFromLocalStorage);
            setUser(parsedUser); // Update user state with parsed user data
        } else {
            // Redirect to login page if user is not available in localStorage
            window.location.href = '/login';
        }
    }, []);

    return (
        <>
            {/* Dashboard page */}
            <div className="main-area">
             <h1 className="dashboard-title">Hello, {user && user.firstName ? user.firstName : 'User'}</h1>
                <Link to={"/reservation"}>
                    <button className="dashboard-button">
                        Book a Test
                    </button>
                </Link>
                <Link to={"/managetest"}>
                    <button className="dashboard-button">
                        Manage Test Schedule
                    </button>
                </Link>
                <Link to={"/testresult"}>
                    <button className="dashboard-button">
                        Check Tests Results
                    </button>
                </Link>
            </div>
        </>
    );
}

export default Dashboard;
