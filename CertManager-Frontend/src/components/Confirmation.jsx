import { Link } from "react-router-dom"

const Confirmation = ({ test }) => {
    return (
        <div className="reservation-container">
            <h1>Confirm Reservation</h1>
            <div className="reservation-card">
                <div className="card-label">Candidate</div>
                <div className="card-text"> {test.candidate.firstName} {test.candidate.lastName}</div>

                <div className="card-label">Certification</div>
                <div className="card-text"> {test.certification.certificationName}</div>

                <div className="card-label">at</div>
                <div className="card-text"> {test.testCentre.testCentreName}</div>

                <div className="card-label">Address</div>
                <div className="card-text"> {test.testCentre.street} {test.testCentre.city}, {test.testCentre.province}</div>

                <div className="card-label">Date</div>
                <div className="card-text"> {test.testDateTime.substring(0, 10)}</div>

                <div className="card-label">Time</div>
                <div className="card-text"> {test.testDateTime.substring(11, 16)} - {test.testEndDateTime.substring(11, 16)} ({test.certification.duration} minutes)</div>
            </div>
            <Link to={"/dashboard"}>
                <div className="card-button">
                    <button className="reservation-button">Back to main</button>
                </div>
            </Link>
        </div>
    )
}

export default Confirmation
