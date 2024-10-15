import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import UserManagerContext from "../../components/userManagerContext";
import styles from '../../static/css/login.module.css';

import axios from 'axios';
import { removeSignInSignUpListeners, setupSignInSignUpListeners } from '../../static/js/login.js';

function Login() {
    const navigate = useNavigate();
    const userManager = useContext(UserManagerContext);
    const [formData, setFormData] = useState({
        name: '',
        password: '',
        password2: ''
    });

    const login = () => {
        userManager.signinPopup().catch((error) => {
            console.error("Login popup failed", error);
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


    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            // Fazendo o POST com axios
            const response = await axios.post('http://localhost:9001/oauth/v1/public/register', formData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            console.log('Resposta do servidor:', response.data);
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
        <div className={styles.body}>
            <div className={styles.container} id={styles.container}>
                <div className={`${styles.formContainer} ${styles.signUpContainer}`}>
                    <form action="#" onSubmit={handleSubmit}>
                        <h1>Create Account</h1>
                        <span style={{color: "red"}} id='error'></span>
                        <input type="text" placeholder="Name" />
                        <input type="password" placeholder="Password" />
                        <input type="password" placeholder="Confirm Password" />
                        <button>Sign Up</button>
                    </form>
                </div>
                <div className={`${styles.formContainer} ${styles.signInContainer}`}>
                    <form action="#">
                        <a href="#">Forgot your password?</a>
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
    );
}

export default Login;
