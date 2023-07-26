import React from 'react';
import { useEffect } from 'react';

// Import components
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import ProtectedRoute from './components/ProtectedRoute';

// Import pages
import Welcome from './pages/Welcome';
import Dashboard from './pages/Dashboard';
import ManageAccount from './pages/ManageAccount';
import Reservation from './pages/Reservation';
import TestResult from './pages/TestResult';
import ManageTest from './pages/ManageTest';
import Login from './pages/Login';
import Register from './pages/Register';
import NotFound from './pages/NotFound';

import './App.css';

import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';

function App() {
  const [user, setUser] = React.useState(null);

  useEffect (() => {
    const localUser = localStorage.getItem('user');
    if (localUser) {
      setUser(JSON.parse(localUser));
    }
  }, []);

  return (
    <>
      <Router>
        {/* Navbar */}
        <Navbar user={user} />
        
        {/* Routes - Public */}
        <Routes>
          <Route path="/" element={<Welcome />} />
          <Route path="/login" element={<Login setUser={setUser} />} />
          <Route path="/register" element={<Register />} />
          <Route path="*" element={<NotFound/>} />

          {/* Routes - Protected */}
          <Route path="/account"
            element={
              <ProtectedRoute user={user}>
                <ManageAccount />
              </ProtectedRoute>
            }
           />
          <Route path="/dashboard"
            element={
              <ProtectedRoute user={user}>
                <Dashboard />
              </ProtectedRoute>
            } 
          />
          <Route path="/reservation"

            element={
              <ProtectedRoute user={user}>
                <Reservation />
              </ProtectedRoute>
            }
          />
          <Route path="/testresult"

            element={
              <ProtectedRoute user={user}>
                <TestResult />
              </ProtectedRoute>
            }
          />
          <Route path="/managetest"

            element={
              <ProtectedRoute user={user}>
                <ManageTest />
              </ProtectedRoute>
            }
          />
        </Routes>
        {/* Footer */}
        <Footer />
      </Router>


    </>
  );
}

export default App;
