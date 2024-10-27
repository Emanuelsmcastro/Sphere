import { createContext, useCallback, useContext, useState } from "react";

const NotificationContainerContext = createContext();

export const useNotificationContext = () => {
    return useContext(NotificationContainerContext);
};

export const NotificationContainerProvider = ({children}) => {
    
    const [notifications, setNotifications] = useState([]);
    const [notificationCount, setNotificationCount] = useState(0);

    const addNotification = useCallback(async (notification) => {
        setNotifications(prevNotifications => [notification, ...prevNotifications]);
        setNotificationCount(prevNotificationCount => prevNotificationCount + 1);
    }, []);

    const removeNotificationByUUID = useCallback(async (notificationUUID) => {
        setNotifications(prevResult => prevResult.filter(item => item.uuid !== notificationUUID));
        setNotificationCount(prevResult => prevResult - 1);
    }, []);

    return (
        <NotificationContainerContext.Provider value={{ notifications, setNotifications, notificationCount, setNotificationCount, addNotification, removeNotificationByUUID }}>
            {children}
        </NotificationContainerContext.Provider>
    )
};