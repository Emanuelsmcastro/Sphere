import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import UserManagerContext from "../../components/userManagerContext";

function Login(){
    const navigate = useNavigate();
    const userManager = useContext(UserManagerContext);
    
    const login = () => {
        userManager.signinPopup().catch((error) => {
            console.error("Login popup failed", error);
        });
    };

    React.useEffect(() => {
        const handleMessage = (event) => {
            if (event.data === "popup_closed") {
                navigate('/', { replace: true });
            }
        };
        window.addEventListener("message", handleMessage);
        return () => window.removeEventListener("message", handleMessage);
    }, [navigate]);

    return (
        <div>
            <h1>Login page</h1>
            <button onClick={login}>Login</button>
        </div>
    );
}

export default Login;
