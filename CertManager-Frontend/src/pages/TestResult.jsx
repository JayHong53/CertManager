import { Link } from "react-router-dom";
import { useState, useEffect } from "react";
import TestList from "../components/TestList";

const TestResult = () => {
    const [isOpen, setIsOpen] = useState(false);
    const [listType, setListType] = useState("");
    const [user, setUser] = useState(null);

    const reset = () => {
        setIsOpen(false);
        setListType("");
    }

    useEffect (() => {
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

    return (
        <>
            <div className="main-area">
                {/* Dashboard page */}
                {isOpen && <TestList user={user} listType={listType} reset={reset}/>}
                {!isOpen && <>
                    <h1 className="dashboard-title">Test Results</h1>
                    <button className="dashboard-button"
                        onClick={() => {
                            setListType("all");
                            setIsOpen(true);
                        }}
                    >
                        View All Results
                    </button>
                    <button className="dashboard-button"
                        onClick={() => {
                            setListType("passed");
                            setIsOpen(true);
                        }}
                    >
                        View Passed Tests
                    </button>
                    <button className="dashboard-button"
                        onClick={() => {
                            setListType("failed");
                            setIsOpen(true);
                        }}
                    >
                        View Failed Tests
                    </button>
                </>
                }
            </div>
        </>
    );
}

export default TestResult;
