import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import UserManagerContext from '../../components/userManagerContext';

function CallbackPage() {
    const navigate = useNavigate();
    const userManager = useContext(UserManagerContext);
    
    userManager.signinRedirectCallback().then((user) => {
        navigate("/", { replace: true });
    }).catch((err) => {
        console.error('Error during login callback:', err);
    });

    return (
        <div className='container-page'>
            <div className="register-page">
                <div className="loader"></div>
            </div>
        </div>
    );
}

export default CallbackPage;
