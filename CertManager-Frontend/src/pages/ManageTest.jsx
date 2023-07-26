import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import TestList from "../components/TestList";

const ManageTest = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [listType, setListType] = useState("");
    const [user, setUser] = useState(null);

    const reset = () => {
        setIsOpen(false);
        setListType("");
    }

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
                {isOpen && <TestList user={user} listType={listType} reset={reset} />}
                {!isOpen && <>
                    <h1 className="dashboard-title">Manage Test Schedule</h1>
                    <button className="dashboard-button"
                        onClick={() => {
                            setListType("active");
                            setIsOpen(true);
                        }}
                    >
                        View All Scheduled Tests
                    </button>
                    <button className="dashboard-button"
                        onClick={() => {
                            setListType("changeEligible");
                            setIsOpen(true);
                        }}
                    >
                        Change Eligible Tests                    
                        </button>
                    <button className="dashboard-button"
                        onClick={() => {
                            setListType("cancelled");
                            setIsOpen(true);
                        }}
                    >
                        View Cancelled Tests
                    </button>
                </>}
            </div>
        </>

    );
}

export default ManageTest;
