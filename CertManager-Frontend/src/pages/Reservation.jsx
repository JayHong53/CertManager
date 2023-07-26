import { useState, useEffect } from 'react';
import axios from 'axios';
import CertificationCard from '../components/CertificationCard';
import TestCentreCard from '../components/TestCentreCard';
import TimeAndDatePicker from '../components/TimeAndDatePicker';
import Confirmation from '../components/Confirmation';

const Reservation = () => {
    const baseUrl = "http://172.16.254.21:8081/api";
    const [user, setUser] = useState(null);
    const [certifications, setCertifications] = useState([]);
    const [testCentres, setTestCentres] = useState([]);
    const [test, setTest] = useState(null);

    const [selectedCertification, setSelectedCertification] = useState(null);
    const [selectedTestCentre, setSelectedTestCentre] = useState(null);

    // 0: Nothing Selected
    // 1: Certification Selected
    // 2: Certification Fixed
    // 3: Test Centre Selected
    // 4: Test Centre Fixed
    // 5: Date Selected
    const [pageFlow, setPageFlow] = useState(0);

    const handleReset = () => {
        setSelectedCertification(null);
        setSelectedTestCentre(null);
        setPageFlow(0);
    }

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

        const fetchCertifications = async () => {
            try {
                const response = await axios.get(baseUrl + "/certification");
                setCertifications(response.data);
            } catch (error) {
                console.error("Error fetching certifications:", error);
                // Handle error, e.g. show error message to user
            }
        }

        const fetchTestCentres = async () => {
            try {
                const response = await axios.get(baseUrl + "/testcentre");
                setTestCentres(response.data);
            } catch (error) {
                console.error("Error fetching test centres:", error);
                // Handle error, e.g. show error message to user
            }
        }

        fetchTestCentres();
        fetchCertifications();
    }, [selectedCertification]);

    return (
        <div className="main-area">
            {/* Certification Not Selected */}
            {pageFlow === 0 && <>
                <h1>Choose Certification Type</h1>
                <div className="list-container">
                    {certifications.map((certification) => (
                        <div
                            className="listitem-card"
                            key={certification.certificationCode}
                            onClick={() => {
                                setSelectedCertification(certification);
                                setPageFlow(1);                            }}
                        >
                            <div className="listitem-title">
                                {certification.certificationName}
                            </div>
                        </div>
                    ))}
                </div>
            </>
            }
            {/* Certification Selected */}
            {pageFlow === 1 &&
                <CertificationCard
                    certification={selectedCertification}
                    proceed={() => setPageFlow(2)}
                    reset={handleReset}
                />
            }
            {pageFlow === 2 && <>
                <h1>Choose Test Centre</h1>
                <div className="list-container">
                    {testCentres.map((testCentre) => (
                        <div
                            className="listitem-card"
                            key={testCentre.testCentreCode}
                            onClick={() => {
                                setSelectedTestCentre(testCentre);
                                setPageFlow(3);
                            }}>
                            <div className="listitem-title">
                                {testCentre.testCentreName}
                            </div>
                        </div>
                    ))}
                </div>
            </>
            }
            {pageFlow === 3 &&
                <TestCentreCard
                    testCentre={selectedTestCentre}
                    proceed={() => setPageFlow(4)}
                    reset={handleReset}
                />
            }
            {pageFlow === 4 && <>
                <TimeAndDatePicker
                    proceed={() => setPageFlow(5)}
                    reset={handleReset}
                    candidate={user}
                    certification={selectedCertification}
                    testCentre={selectedTestCentre}
                    setTest={setTest}
                />
            </>}
            {pageFlow === 5 && 
                <Confirmation test={test}/>
            }
        </div>
    )
}


export default Reservation;