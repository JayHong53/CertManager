import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import TimeAndDatePicker from './TimeAndDatePicker';
import Confirmation from './Confirmation';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from 'axios';


const TestDetail = ({ user, test, setIsDetailOpen }) => {
    const baseUrl = "http://172.16.254.21:8081/api";
    const [isRescheduleOpen, setIsRescheduleOpen] = useState(false);
    const [isConfirmationOpen, setIsConfirmationOpen] = useState(false);
    const [isCancelOpen, setIsCancelOpen] = useState(false);
    const [rescheduledTest, setRescheduledTest] = useState();
    const navigate = useNavigate();

    const handleBackToListClick = () => {
        setIsDetailOpen(false);
    };

    const handleProceedClick = () => {
        setIsRescheduleOpen(false);
        setIsConfirmationOpen(true);
    };

    const handleCancelTest = async () => {
        try {
            const response = await axios.put(baseUrl + "/test/" + user._id + "/cancel/" + test._id);
            if (response.status === 200) {
                console.log("Test cancelled");
                setIsCancelOpen(false);
                setIsDetailOpen(false);
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

    return (
        <>
            {isCancelOpen &&
                <div className="cancel-modal-container">
                    <div className="modal-background">
                        <div className="modal-message">Do you want to cancel the test?</div>
                        <div className="modal-detail-1">{test.certification.certificationName}</div>
                        <div className="modal-detail-2">Date: {test.testDateTime.substring(0, 10)} {test.testDateTime.substring(11, 16)} </div>
                        <button className='dashboard-button' onClick={handleCancelTest}>Yes</button>
                        <button className='dashboard-button' onClick={() => { setIsCancelOpen(false) }}>No</button>
                    </div>
                </div>
            }
            {isConfirmationOpen && <Confirmation test={rescheduledTest} />}
            {isRescheduleOpen && !isConfirmationOpen &&
                <TimeAndDatePicker
                    user={user}
                    reset={() => { setIsRescheduleOpen(false) }}
                    proceed={handleProceedClick}
                    candidate={test.candidate}
                    certification={test.certification}
                    testCentre={test.testCentre}
                    setIsRescheduleOpen={setIsRescheduleOpen}
                    setTest={setRescheduledTest}
                />}
            {!isRescheduleOpen && !isConfirmationOpen && <>
                <div className='detail-container'>
                    <h1 className="detail-title">Test Detail</h1>
                    <div className='reservation-detail'>
                        <table>
                            <tbody>
                                <tr>
                                    <td className="td-label">Test ID</td>
                                    <td className="td-text">{test._id}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Candidate</td>
                                    <td className="td-text">{test.candidate.firstName} {test.candidate.lastName}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Certification</td>
                                    <td className="td-text">{test.certification.certificationName} ({test.certification.certificationCode})</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Test Format</td>
                                    <td className="td-text">{test.certification.format} ({test.certification.numberOfQuestions} Questions)</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Duration</td>
                                    <td className="td-text">{test.certification.duration} minutes</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Passing Score</td>
                                    <td className="td-text">{test.certification.passingScore}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Score</td>
                                    <td className="td-text">{test.score === 0 ? "N/A" : test.score}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Result</td>
                                    <td className="td-text">{test.result}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Test Centre</td>
                                    <td className="td-text">{test.testCentre.testCentreName}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Address</td>
                                    <td className="td-text">{test.testCentre.street}, {test.testCentre.city}, {test.testCentre.province}</td>
                                </tr>
                                <tr>
                                    <td className="td-label">Time & Date</td>
                                    <td className="td-text">{test.testDateTime.substring(0, 10)} {test.testDateTime.substring(11, 16)} </td>
                                </tr>
                                <tr>
                                    <td className="td-label">Status</td>
                                    <td className="td-text">{test.status}</td>
                                </tr>
                            </tbody>
                        </table>

                    </div>
                    {test.result === "Pass" && <button className="dashboard-button"
                        onClick={() => window.print()}
                    >Print Certificate</button>}
                    {test.result === "Fail" &&
                            <button className="dashboard-button"
                            onClick={() => {
                                navigate('/reservation');
                            }}
                            >Retake Test            
                            </button>
                    }
                    {new Date(test.testDateTime) > new Date(new Date().getTime() + (7 * 24 * 60 * 60 * 1000)) &&
                        <button className="dashboard-button"
                            onClick={() => setIsRescheduleOpen(true)}
                        >Reschedule</button>
                    }
                    {test.status === "Scheduled" && new Date(test.testDateTime) > new Date(new Date().getTime()) &&
                        <button className="dashboard-button"
                            onClick={() => setIsCancelOpen(true)}
                        >Cancel Booking</button>}
                    <button className="dashboard-button" onClick={handleBackToListClick}>Back to list</button>
                </div>
            </>}
            <ToastContainer />
        </>
    );
};

export default TestDetail;