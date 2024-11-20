import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useUserManagerProvider } from '../../components/userManagerContext';

function CallbackPage() {
    const navigate = useNavigate();
    const { userManager } = useUserManagerProvider();
    
    useEffect(() => {
        if(!userManager) return;
        userManager.signinRedirectCallback().then((user) => {
            navigate("/", { replace: true });
        }).catch((err) => {
            console.error('Error during login callback:', err);
        });
    }, [userManager, navigate]);

    return (
        <div className='container-page'>
            <div className="register-page">
                <div className="loader"></div>
            </div>
        </div>
    );
}

export default CallbackPage;
