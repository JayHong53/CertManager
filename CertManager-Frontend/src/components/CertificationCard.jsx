const CertificationCard = ({ certification, proceed, reset }) => {

    return (
        <div className="reservation-container">
            <div className="card-title">{certification.certificationName}</div>
            <div className="reservation-card">
                <div className="card-label">Format</div>
                <div className="card-text">{certification.format}</div>
                <div className="card-label">Number of Questions</div>
                <div className="card-text">{certification.numberOfQuestions}</div>
                <div className="card-label">Duration</div>
                <div className="card-text">{certification.duration} minutes</div>
                <div className="card-label">Passing Score</div>
                <div className="card-text">{certification.passingScore}</div>
                <div className="card-label">Fee</div>
                <div className="card-text">CAD {certification.fee}</div>
            </div>
            <div className="card-button">
                <button className="reservation-button" onClick={proceed}>Confirm</button>
            </div>
            <div className="card-button-cancel">
                <button className="reservation-button-cancel" onClick={reset}>Cancel</button>
            </div>
        </div>
    );
}

export default CertificationCard;
