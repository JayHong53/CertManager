import { Navigate } from 'react-router-dom';

const ProtectedRoute = ({user, children}) => {

    if (user === null) {
        return <Navigate to="/login" />;
    }
    return children;
};

export default ProtectedRoute;
