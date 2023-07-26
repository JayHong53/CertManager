import axios from "axios";
import { useState } from "react";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const TimeAndDatePicker = ({ proceed, reset, candidate, certification, testCentre, setTest }) => {
    const baseUrl = "http://172.16.254.21:8081/api";
    const [errorMessage, setErrorMessage] = useState(null);

    const handleSubmit = async () => {
        const testDate = document.querySelector(".datePicker").value;
        const testTime = document.querySelector(".timePicker").value;
        const testDateTime = testDate + "T" + testTime + ":00";

        if (testDate === "" || testTime === "") {
            toast.error("Please select a date and time", {
                position: "bottom-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: false
            });
            setErrorMessage("Please select a date and time")
            return;
        }

        if (testDate < new Date().toISOString().substring(0, 10)) {
            toast.error("Please select a date in the future", {
                position: "bottom-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: false
            });
            setErrorMessage("Please select a date in the future")
            return;
        }
        
        const test = {
            candidate: candidate,
            certification: certification,
            testCentre: testCentre,
            testDateTime: testDateTime
        }
        
        try {
            const response = await axios.post(baseUrl + "/test/" + candidate._id + "/booktest", test);
            if (response.status === 200) {
                console.log("Test created");
                setTest(response.data);
                proceed();
            }
        } catch (error) {
            toast.error(error.response.data.message, {
                position: "bottom-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: false
            });
            setErrorMessage(error.response.data.message);
        }    
    }

    return (
        <div className="reservation-container">
            <div className="card-title">Select Test Date</div>
            <div className="reservation-card">

                <div className="card-label">Date and Time</div>
                <div className="card-text">
                    <input className="datePicker" type="date" />
                </div>
                <div className="card-text">
                    <select className="timePicker">
                        <option value="08:00">08:00</option>
                        <option value="09:00">09:00</option>
                        <option value="10:00">10:00</option>
                        <option value="11:00">11:00</option>
                        <option value="12:00">12:00</option>
                        <option value="13:00">13:00</option>
                        <option value="14:00">14:00</option>
                        <option value="15:00">15:00</option>
                        <option value="16:00">16:00</option>
                        <option value="17:00">17:00</option>
                        <option value="18:00">18:00</option>
                    </select>

                </div>
            </div>
            <div className="card-button">
                <button className="reservation-button" onClick={handleSubmit}>Confirm Reservation</button>
            </div>
            <div className="card-button">
                <button className="reservation-button-cancel" onClick={reset}>Return</button>
            </div>
            {errorMessage && 
            <ToastContainer/>         
            }
        </div>
    );
}

export default TimeAndDatePicker;