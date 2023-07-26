import { Link } from 'react-router-dom';

const NotFound = () => {
    return (
        <div className="main-area">
            <h1>Invalid Approach</h1>
            <div className="main-button-noanimation">
                    <Link to="/" className="main-button-link">Back to main</Link>
            </div>
        </div> 
    )
}

export default NotFound;