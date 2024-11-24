import { createContext, useCallback, useContext, useEffect, useRef } from "react";
import { useContacts } from "./contactsProvider";
import { useNotificationContext } from "./notificationContainerProvider";
import { useUserManagerProvider } from "./userManagerProvider";


const WSNotificationConnectionContext = createContext(null);

export const useNotificationConnection = () => {
    return useContext(WSNotificationConnectionContext);
};

export const WSNotificationConnectionProvider = ({children}) => {
    const { getUser } = useUserManagerProvider();
    const ws = useRef(null);
    const {addContact} = useContacts();
    const {addNotification} = useNotificationContext();

    const connectToWS = useCallback(async () => {
        if(ws.current) return;
        getUser((user) => {
            ws.current = new WebSocket(`${process.env.REACT_APP_GATEWAY_WS_HOST}/ws/notification/v1?token=${user.access_token}`);
            ws.current.onopen = () => {
                console.log("Connection OPEN.")
            };
    
            ws.current.onmessage = (event) => {
                try {
                    const message = JSON.parse(event.data);
                    if(!message && !message.type) return;
                    console.log(message);
                    if(message.type === "FRIEND_REQUEST"){
                        addNotification(message.content);
                    } else if(message.type === "ADDED_FRIEND"){
                        addContact(message.content);
                    }
                } catch (error) {
                    console.error("Failed to parse message:", error);
                
                }
            };
    
            ws.current.onerror = (error) => {
                console.log(error);
            };
        });

    }, [getUser, addContact, addNotification]);

    useEffect(() => {
        connectToWS();
    
    }, [connectToWS]);

    return (
        <WSNotificationConnectionContext.Provider value={ws}>
            {children}
        </WSNotificationConnectionContext.Provider>
    )
};