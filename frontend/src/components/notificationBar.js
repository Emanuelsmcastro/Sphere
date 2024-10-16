import React, { createContext, useContext, useState } from 'react';
import styles from "../static/css/notificationBar.module.css";

const NotificationContext = createContext();

export const useNotification = () => {
    return useContext(NotificationContext);
};

export const NotificationProvider = ({ children }) => {
    const [visible, setVisible] = useState(false);
    const [message, setMessage] = useState('');
    const [title, setTitle] = useState('');

    const showNotification = (title, msg) => {
        setTitle(title);
        setMessage(msg);
        setVisible(true);
        
        setTimeout(() => {
            setVisible(false);
        }, 3000);
    };

    return (
        <NotificationContext.Provider value={{ showNotification }}>
            {children}
            <div className={`${styles.notificationContainer} ${visible ? styles.show : ''}`}>
                <h3 className={styles.notificationTitle}>{title}</h3>
                <hr />
                <p className={styles.notificationContent}>{message}</p>
            </div>
        </NotificationContext.Provider>
    );
};
