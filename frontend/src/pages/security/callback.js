import React, { useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import UserManagerContext from '../../components/userManagerContext';

function CallbackPage() {
    const navigate = useNavigate();
    const userManager = useContext(UserManagerContext);
    
    React.useEffect(() => {
        userManager.signinPopupCallback().then(() => {
            // Fechar o popup
            window.opener.postMessage("popup_closed", "*");
            window.close();
        }).catch((error) => {
            console.error("Erro ao fechar o popup", error);
            navigate('/oauth/login', { replace: true });
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
