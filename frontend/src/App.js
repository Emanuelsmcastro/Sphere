import axios from 'axios';
import { UserManager } from 'oidc-client';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import { ChatContainerProvider } from './components/chatContainerProvider';
import ConnectionExceptionHandler from './components/connectionError';
import { ContactsProvider } from './components/contactsProvider';
import { NotificationProvider } from './components/notificationBar';
import { NotificationContainerProvider } from './components/notificationContainerProvider';
import UserManagerContext from './components/userManagerContext';
import { WSNotificationConnectionProvider } from './components/wsNotificationProvider';
import Home from './pages/core/home';
import CallbackPage from './pages/security/callback';
import Login from './pages/security/login';

function App() {

  const config = {
    authority: process.env.REACT_APP_OAUTH_HOST,
    client_id: "front-client",
    client_secret: "secret",
    redirect_uri: "http://localhost:3000/oauth/callback",
    response_type: "code",
    scope: "profile",
    state: 1234,
    automaticSilentRenew: true,
  };

  axios.defaults.withCredentials = true;
  
  const userManager = new UserManager(config);

  return (
    <UserManagerContext.Provider value={userManager}>
      <Router>
        <ConnectionExceptionHandler>
          <NotificationProvider>
            <Routes>
              <Route path="/" element={
                <NotificationContainerProvider>
                  <ContactsProvider>
                    <ChatContainerProvider>
                      <WSNotificationConnectionProvider>
                        <Home />
                      </WSNotificationConnectionProvider>
                    </ChatContainerProvider>
                  </ContactsProvider>
                </NotificationContainerProvider>
              } />
              <Route path="/oauth/login" element={<Login />}/>
              <Route path="/oauth/callback" element={<CallbackPage />} />
            </Routes>
          </NotificationProvider>
        </ConnectionExceptionHandler>
      </Router>
    </UserManagerContext.Provider>
  );
}

export default App;
