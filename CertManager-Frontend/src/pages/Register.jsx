import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import axios from 'axios';

const Register = () => {
    const baseUrl = 'http://172.16.254.21:8081/api';
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [street, setStreet] = useState('');
    const [city, setCity] = useState('');
    const [province, setProvince] = useState('');
    const [phone, setPhone] = useState('');
    const [isContactOpen, setIsContactOpen] = useState(false);

    const navigate = useNavigate()

    const handleFirstStep = async (e) => {
        e.preventDefault();
        if (email === '' || password === '' || firstName === '' || lastName === '') {
            toast.error("Please fill in all the fields", {
                position: "bottom-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: false
            });
            return;
        }

        try {
            const response = await axios.post(baseUrl + "/auth/checkemail", { email: email });
            if (response.status === 200 && response.data === false) {
                toast.error("Email already exists", {
                    position: "bottom-center",
                    autoClose: 3000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: false
                });
                return;
            }
        } catch (error) {
            if (error.response.status === 404) {
                toast.error(error.response.data.message, {
                    position: "bottom-center",
                    autoClose: 3000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: false
                });
            }
        }
        setIsContactOpen(true);
    };

    const handleSecondStep = async (e) => {
        e.preventDefault();

        if (street === '' || city === '' || province === '' || phone === '') {
            toast.error("Please fill in all the fields", {
                position: "bottom-center",
                autoClose: 3000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: false
            });
            return;
        }

        try {
            const newUser = {
                email: email,
                password: password,
                firstName: firstName,
                lastName: lastName,
                street: street,
                city: city,
                province: province,
                phone: phone
            };

            const response = await axios.post(baseUrl + "/auth/register", newUser);
            if (response.status === 200) {
                console.log("User registered");
                navigate('/login');
            }
        } catch (error) {
            toast.error("Could not complete the process", {
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
            {/* Login form with email and password, and a link to register */}
            <div className="main-area">
                {!isContactOpen &&
                    <div className="login-form">
                        <form onSubmit={handleFirstStep}>
                            <div className="login-form-item">
                                {email === '' &&
                                    <label className='login-form-label' htmlFor="email">Email</label>
                                }
                                <input
                                    className="login-form-input"
                                    type="email"
                                    id="email"
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                {password === '' &&
                                    <label className='login-form-label' htmlFor="password">Password</label>
                                }
                                <input
                                    className="login-form-input"
                                    type="password"
                                    id="password"
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                {firstName === '' &&
                                    <label className='login-form-label' htmlFor="firstName">FirstName</label>
                                }
                                <input

                                    className="login-form-input"
                                    type="text"
                                    id="firstName"
                                    onChange={(e) => setFirstName(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                {lastName === '' &&
                                    <label className='login-form-label' htmlFor="lastName">LastName</label>
                                }
                                <input
                                    className="login-form-input"
                                    type="text"
                                    id="lastName"
                                    onChange={(e) => setLastName(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                <button className="login-form-button" type="submit">Proceed</button>
                            </div>
                        </form>
                    </div>}
                {isContactOpen &&
                    <div className="login-form">
                        <form onSubmit={handleSecondStep}>
                            <div className="login-form-item">
                                {street === '' &&
                                    <label className='login-form-label' htmlFor="street">Street</label>
                                }
                                <input
                                    className="login-form-input"
                                    type="text"
                                    id="street"
                                    onChange={(e) => setStreet(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                {city === '' &&
                                    <label className='login-form-label' htmlFor="city">City</label>
                                }
                                <input
                                    className="login-form-input"
                                    type="text"
                                    id="city"
                                    onChange={(e) => setCity(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                <select className="login-form-input" id="province" onChange={(e) => setProvince(e.target.value)}>
                                    <option value="">Province</option>
                                    <option value="AB">Alberta</option>
                                    <option value="BC">British Columbia</option>
                                    <option value="MB">Manitoba</option>
                                    <option value="NB">New Brunswick</option>
                                    <option value="NL">Newfoundland and Labrador</option>
                                    <option value="NS">Nova Scotia</option>
                                    <option value="NT">Northwest Territories</option>
                                    <option value="NU">Nunavut</option>
                                    <option value="ON">Ontario</option>
                                    <option value="PE">Prince Edward Island</option>
                                    <option value="QC">Quebec</option>
                                    <option value="SK">Saskatchewan</option>
                                    <option value="YT">Yukon</option>
                                </select>
                            </div>
                            <div className="login-form-item">
                                {phone === '' &&

                                    <label className='login-form-label' htmlFor="phone">Phone</label>
                                }
                                <input
                                    className="login-form-input"
                                    type="text"
                                    id="phone"
                                    onChange={(e) => setPhone(e.target.value)}
                                />
                            </div>
                            <div className="login-form-item">
                                <button className="login-form-button" type="submit">Finish Register</button>
                            </div>
                        </form>
                    </div>
                }
                <div className="login-register-message">
                    <p>Already a member? <a href="/login">login</a></p>
                </div>
            </div>
            <ToastContainer />
        </>
    );
}

export default Register;

