import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserManagerContext from "../../components/userManagerContext";
import styles from '../../static/css/login.module.css';

import axios from 'axios';
import { useNotification } from '../../components/notificationBar.js';
import { goToDefaultPanel, removeSignInSignUpListeners, setupSignInSignUpListeners } from '../../static/js/login.js';

function Login() {
    const navigate = useNavigate();
    const userManager = useContext(UserManagerContext);
    const {showNotification}  = useNotification();

    const initialFormData = {
        username: '',
        profileFirstName: '',
        profileLastName: '',
        password: '',
        password2: ''
    };

    const [formData, setFormData] = useState(initialFormData);

    const login = () => {
        userManager.signinPopup().catch((error) => {
            console.error("Login popup failed");
        });
    };

    useEffect(() => {
        const handleMessage = (event) => {
            if (event.data === "popup_closed") {
                navigate('/', { replace: true });
            }
        };

        window.addEventListener("message", handleMessage);
        return () => {
            window.removeEventListener("message", handleMessage);
        };
    }, [navigate]);

    useEffect(() => {
        setupSignInSignUpListeners(styles);
        return () => {
            removeSignInSignUpListeners();
        };
    }, []);

    const resetForm = () => {
        setFormData(initialFormData);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:9001/oauth/v1/public/register', formData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if(response.status === 201){
                resetForm();
                goToDefaultPanel(styles);
                showNotification("Account Created", "Now just log in to enter the sphere!");
            }
        } catch (error) {
            if (error.response) {
                const errorSpan = document.getElementById("error");
                errorSpan.innerText = error.response.data.message;
                console.error('Erro na resposta da API:', error.response.data);
            } else if (error.request) {
                console.error('Erro de rede:', error.request);
            } else {
                console.error('Erro inesperado:', error.message);
            }
        }
    };

    return (
        <>
            <div className={styles.body}>
                <div className={styles.container} id={styles.container}>
                    <div className={`${styles.formContainer} ${styles.signUpContainer}`}>
                        <form action="" onSubmit={handleSubmit}>
                            <h1>Create Account</h1>
                            <span style={{color: "red"}} id='error'></span>
                            <input
                                type="text"
                                name="username"
                                placeholder="Username"
                                value={formData.username}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="text"
                                name="profileFirstName"
                                placeholder="First Name"
                                value={formData.profileFirstName}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="text"
                                name="profileLastName"
                                placeholder="Last Name"
                                value={formData.profileLastName}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="password"
                                name="password"
                                placeholder="Password"
                                value={formData.password}
                                onChange={handleInputChange}
                                required
                            />
                            <input
                                type="password"
                                name="password2"
                                placeholder="Confirm Password"
                                value={formData.password2}
                                onChange={handleInputChange}
                                required
                            />
                            <button>Sign Up</button>
                        </form>
                    </div>
                    <div className={`${styles.formContainer} ${styles.signInContainer}`}>
                        <form action="#">
                            <button type="button" onClick={() => { }} style={{ background: 'none', border: 'none', color: 'gray', cursor: 'pointer', textDecoration: 'underline' }}>
                                Forgot your password?
                            </button>
                            <button onClick={login}>Sign In</button>
                        </form>
                    </div>
                    <div className={styles.overlayContainer}>
                        <div className={styles.overlay}>
                            <div className={`${styles.overlayPanel} ${styles.overlayLeft}`}>
                                <h1>Welcome Back!</h1>
                                <p>To keep connected with us please login with your personal info</p>
                                <button className={styles.ghost} id="signIn">Sign In</button>
                            </div>
                            <div className={`${styles.overlayPanel} ${styles.overlayRight}`}>
                                <h1>Hello, Friend!</h1>
                                <p>Enter your personal details and start your journey with us</p>
                                <button className={styles.ghost} id="signUp">Sign Up</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}

export default Login;
