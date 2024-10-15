import axios from 'axios';
import { UserManager } from 'oidc-client';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import ConnectionExceptionHandler from './components/connectionError';
import UserManagerContext from './components/userManagerContext';
import Home from './pages/core/home';
import CallbackPage from './pages/security/callback';
import Login from './pages/security/login';

function App() {

  const config = {
    authority: "http://localhost:9001",
    client_id: "front-client",
    client_secret: "secret",
    redirect_uri: "http://localhost:3000/oauth/callback",
    response_type: "code",
    scope: "profile",
    state: 1234
  };

  axios.defaults.withCredentials = true;
  
  const userManager = new UserManager(config);

  return (
    <UserManagerContext.Provider value={userManager}>
      <Router>
        <ConnectionExceptionHandler>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/oauth/login" element={<Login />}/>
            <Route path="/oauth/callback" element={<CallbackPage />} />
          </Routes>
        </ConnectionExceptionHandler>
      </Router>
    </UserManagerContext.Provider>
  );
}

export default App;
