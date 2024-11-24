import axios from 'axios';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import ConnectionExceptionHandler from './components/utils/connectionError';
import { NotificationProvider } from './components/notification/notificationBar';
import { ChatContainerProvider } from './components/providers/chatContainerProvider';
import { ContactsProvider } from './components/providers/contactsProvider';
import { ModalProvider } from './components/providers/modalProvider';
import { NotificationContainerProvider } from './components/providers/notificationContainerProvider';
import { UserManagerProvider } from './components/providers/userManagerProvider';
import { WSNotificationConnectionProvider } from './components/providers/wsNotificationProvider';
import Home from './pages/core/home';
import CallbackPage from './pages/security/callback';
import Login from './pages/security/login';

function App() {
  const config = {
    authority: process.env.REACT_APP_OAUTH_HOST,
    client_id: process.env.REACT_APP_CLIENT_ID,
    client_secret: "secret",
    redirect_uri: process.env.REACT_APP_REDIRECT_URI,
    response_type: "code",
    scope: "profile",
    state: 1234,
    automaticSilentRenew: true,
  };

  axios.defaults.withCredentials = true;
  
  // const userManager = new UserManager(config);
  
  return (
    <UserManagerProvider config={config}>
        <Router>
          <ConnectionExceptionHandler>
            <NotificationProvider>
              <Routes>
                <Route path="/" element={
                  <NotificationContainerProvider>
                    <ContactsProvider>
                      <ChatContainerProvider>
                        <WSNotificationConnectionProvider>
                          <ModalProvider>
                            <Home />
                          </ModalProvider>
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
    </UserManagerProvider>
  );
}

export default App;
