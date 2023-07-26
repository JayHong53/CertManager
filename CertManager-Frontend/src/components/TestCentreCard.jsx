const CertificationCard = ({ testCentre, proceed, reset }) => {

    return (
        <div className="reservation-container">
            <div className="card-title">{testCentre.testCentreName}</div>
            <div className="reservation-card">
                <div className="card-label">Address</div>
                <div className="card-text">{testCentre.street} {testCentre.city}, {testCentre.province}</div>
                <div className="card-label">Phone</div>
                <div className="card-text">{testCentre.phone}</div>
                <div className="card-label">Website</div>
                <div className="card-text">{testCentre.website}</div>
            </div>
            <div className="card-button">
                <button className="reservation-button" onClick={proceed}>Confirm</button>
            </div>
            <div className="card-button">
                <button className="reservation-button-cancel" onClick={reset}>Cancel</button>
            </div>
        </div>
    );
}

export default CertificationCard;
