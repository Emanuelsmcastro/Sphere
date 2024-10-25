import { createContext, useCallback, useContext, useEffect, useState } from "react";
import { useContacts } from "./contactsProvider";
import { useNotificationContext } from "./notificationContainerProvider";
import UserManagerContext from "./userManagerContext";


const WSNotificationConnectionContext = createContext(null);

export const useNotificationConnection = () => {
    return useContext(WSNotificationConnectionContext);
};

export const WSNotificationConnectionProvider = ({children}) => {
    const userManager = useContext(UserManagerContext);
    const [ws, setWS] = useState(null);
    const {addContact} = useContacts();
    const {addNotification} = useNotificationContext();

    const connectToWS = useCallback(async () => {
        const user = await userManager.getUser();
        if(!user) return;
        const ws = new WebSocket(`ws://localhost:8765/ws/notification/v1?token=${user.access_token}`);
        ws.onopen = () => {
            console.log("Connection OPEN.")
        };

        ws.onmessage = (event) => {
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

        ws.onerror = (error) => {
            console.log(error);
        };

        setWS(ws);
    }, [userManager, addContact, addNotification]);

    useEffect(() => {
        connectToWS();
    
    }, [connectToWS]);

    return (
        <WSNotificationConnectionContext.Provider value={ws}>
            {children}
        </WSNotificationConnectionContext.Provider>
    )
};