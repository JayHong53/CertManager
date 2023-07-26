import { Link } from 'react-router-dom';
import { useEffect, useState } from 'react';

const Welcome = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const userFromLocalStorage = localStorage.getItem('user');
        if (userFromLocalStorage) {
            const parsedUser = JSON.parse(userFromLocalStorage);
            setUser(parsedUser); // Update user state with parsed user data
        }
    });

    return (
    <>
        <div>
            <div className="main-area">
                <div className="main-title">
                     Boost Your Career with CertManager
                </div>
                <div className="main-button">
                    {user && <Link to="/dashboard" className="main-button-link">Get Started</Link>}
                    {!user && <Link to="/login" className="main-button-link">Get Started</Link>}
                </div>
            </div>
        </div>
    </>
    );
}


export default Welcome;