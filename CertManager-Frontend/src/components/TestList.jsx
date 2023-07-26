import axios from "axios";
import { useState, useEffect } from "react";
import TestDetail from "./TestDetail";

const TestList = ({ user, listType, reset }) => {
    const baseUrl = "http://172.16.254.21:8081/api";
    const [tests, setTests] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [title, setTitle] = useState("");
    const [listCategory, setListCategory] = useState("");
    const [isDetailOpen, setIsDetailOpen] = useState(false);
    const [selectedTest, setSelectedTest] = useState(null);
    
    useEffect(() => { 
        let apiUrl = "";

        if (listType === "active") {
            apiUrl = baseUrl + "/test/" + user._id + "/active";
            setTitle("Scheduled Tests");
            setListCategory("future");
        } else if (listType === "changeEligible") {
            apiUrl = baseUrl + "/test/" + user._id + "/reschedule-eligible";
            setTitle("Reschedule Eligible Tests");
            setListCategory("future");
        } else if (listType === "cancelled") {
            apiUrl = baseUrl + "/test/" + user._id + "/cancelled";
            setTitle("Cancelled Tests");
            setListCategory("future");
        } else if (listType === "passed") {
            apiUrl = baseUrl + "/test/" + user._id + "/passed";
            setTitle("Test Results - Passed");
            setListCategory("past");
        } else if (listType === "failed") {
            apiUrl = baseUrl + "/test/" + user._id + "/failed";
            setTitle("Test Results - Failed");
            setListCategory("past");
        } else {
            apiUrl = baseUrl + "/test/" + user._id + "/past";
            setTitle("Test Results - All");
            setListCategory("past");
        }

        const fetchTests = async () => {
            try {
                const response = await axios.get(apiUrl);
                setTests(response.data);
                setIsLoading(false);
            } catch (error) {
                console.error("Error fetching tests:", error);
                // Handle error, e.g. show error message to user
            }
        }
        fetchTests();
    }, [listType, user._id]);

    if (isDetailOpen) {
        return (
            <TestDetail
                user={user}
                test={selectedTest}
                reset={reset}
                setIsDetailOpen={setIsDetailOpen}
            />
        );
    } else {
        if (listCategory === "past") {
            return (
                <div className="reservation-container">
                    <div className="card-title">{title}</div>
                    {isLoading && <div className="loading">Loading...</div>}
                    {tests.length === 0 && !isLoading && <div className="no-tests">No tests found</div>}
                    {tests.length > 0 && !isLoading &&
                        <div className="table-result">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Certification</th>
                                        <th>Test Centre</th>
                                        <th>Test Date</th>
                                        <th>Passing Score</th>
                                        <th>Scored</th>
                                        <th>Result</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {tests.map((test) => (
                                        <tr key={test._id}
                                            onClick={() => {
                                                setSelectedTest(test);
                                                setIsDetailOpen(true);
                                            }}
                                        >
                                            <td>{test.certification.certificationName}</td>
                                            <td>{test.testCentre.testCentreName}</td>
                                            <td>{test.testDateTime.substring(0, 10)}</td>
                                            <td>{test.certification.passingScore}</td>
                                            <td> {test.score}</td>
                                            <td>{test.result}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    }
                    <button className="dashboard-button" onClick={reset}>Back</button>
                </div>
            )
        } else {
            return (
                <div className="reservation-container">
                    <div className="card-title">{title}</div>
                    {isLoading && <div className="loading">Loading...</div>}
                    {tests.length === 0 && !isLoading && <div className="no-tests">No tests found</div>}

                    {tests.length > 0 && !isLoading &&
                        <div className="table-result">
                            <table>
                                <thead>
                                    <tr>
                                        <th>Certification</th>
                                        <th>Test Centre</th>
                                        <th>Test Date</th>
                                        <th>Status</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {tests
                                        .sort((a, b) => new Date(a.testDateTime) - new Date(b.testDateTime)) // Sort by testDateTime
                                        .map((test) => (
                                            <tr key={test.testDateTime}
                                                onClick={() => {
                                                    setSelectedTest(test);
                                                    setIsDetailOpen(true);
                                                }}
                                            >
                                                <td>{test.certification.certificationName}</td>
                                                <td>{test.testCentre.testCentreName}</td>
                                                <td>{test.testDateTime.substring(0, 10)} {test.testDateTime.substring(11, 16)} </td>
                                                <td>{test.status}</td>
                                            </tr>
                                        ))}
                                </tbody>
                            </table>
                        </div>
                    }
                    <button className="dashboard-button" onClick={reset}>Back</button>
                </div>
            )
        }
    }
}

export default TestList;

