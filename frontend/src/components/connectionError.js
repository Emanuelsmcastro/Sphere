import axios from "axios";
import { useLocation, useNavigate } from 'react-router-dom';

const ConnectionExceptionHandler = ({ children }) => {
    const navigate = useNavigate();
    const location = useLocation();

    // Adicionando o interceptor de resposta
    axios.interceptors.response.use(
        response => response,
        error => {
            if (error.response) {
                if (error.response.status === 401) {
                    navigate('/oauth/login', { state: { from: location }, replace: true });
                } else if (error.response.status === 403) {
                    alert("Você não pode acessar esta parte.");
                }
            } else {
                // Se não houver response, é um erro de rede ou outro erro
                navigate('/error', { state: { from: location }, replace: true });
            }
            return Promise.reject(error);
        }
    );

    return children;
};

export default ConnectionExceptionHandler;