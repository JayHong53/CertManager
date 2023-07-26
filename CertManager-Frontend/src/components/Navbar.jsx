import { Link } from 'react-router-dom';

const Navbar = ({user}) => {
    const handleLogout = () => {
        localStorage.removeItem('user');
        window.location.href = '/';
    } 
     
    return (
        <>
            <nav className="navbar">
                <Link to="/" className="navbar-logo">CertManager</Link>
                <ul className="navbar-menu">
                    {user && <>
                    <li className="navbar-item">
                        <Link to="/dashboard" className="navbar-link">Home</Link>
                    </li>
                    <li className="navbar-item">
                        <Link to="/account" className="navbar-link">Account</Link>
                    </li>
                    <li className="navbar-item">
                        <Link to="/reservation" className="navbar-link">Book a Test</Link>
                    </li>
                    <li className="navbar-item">
                        <Link to="/managetest" className="navbar-link">Manage Test</Link>
                    </li>
                    <li className="navbar-item">
                        <Link to="/testresult" className="navbar-link">Test Results</Link>
                    </li>
                    <li className="navbar-item">
                        <Link onClick={handleLogout} className="navbar-link">Logout</Link>
                    </li>
                    </>} 
                    {!user && <>
                    <li className="navbar-item">
                        <Link to="/login" className="navbar-link">Login</Link>
                    </li>
                    <li className="navbar-item">
                        <Link to="/register" className="navbar-link">Register</Link>
                    </li>
                    </>}              
                </ul>

            </nav>
        </>

    );
}

export default Navbar;
