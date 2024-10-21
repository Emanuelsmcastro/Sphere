import axios from "axios";
import { useContext, useEffect, useRef, useState } from "react";
import styles from "../static/css/notifications.module.css";
import UserManagerContext from "./userManagerContext";
import useClickOutside from "./utils/useClickOutside";

function Notifications(){
    const userManager = useContext(UserManagerContext);
    const btnRef = useRef(null);
    const resultContainerRef = useRef(null);
    const [showResults, setShowResults] = useState(false);
    const [notifications, setNotifications] = useState([]);

    useClickOutside(resultContainerRef, btnRef, () => {
        setShowResults(false);
    });

    const handleBtnClick = () => {
        setShowResults(true);
    }

    const handleAcceptOrReject = (e) => {
        e.stopPropagation();
        const container = e.target.parentNode.parentNode.parentNode;
        const typeData = e.target.getAttribute("type-data");
        const uuid = container.getAttribute("data-uuid");
        updateFriendNotification({
            uuid: uuid,
            status: typeData
        });
        setNotifications(prevResult => prevResult.filter(item => item.uuid !== uuid));
    }

    const fetchData = async () => {
        const user = await userManager.getUser();
        if(!user) return;
        try {
            const response = await axios.get(process.env.REACT_APP_GATEWAY_HOST + "/notification/v1/get-friend-request-notifications", {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`
                }
            });
            console.log(response.data.content)
            setNotifications(response.data.content);
        } catch (error) {
            console.log(error);
        }
    };

    const updateFriendNotification = async (data) => {
        const user = await userManager.getUser();
        if(!user) return;
    
        try {
            const response = await axios.patch(process.env.REACT_APP_GATEWAY_HOST + "/notification/v1/update", data,{
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`
                }
            });
            console.log(response.status);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchData();
        return () => {

        }
    }, []);

    const getNotificationsWithWebSocket = async () => {
        const user = await userManager.getUser();
        console.log(user);
        if(!user) return;
        const ws = new WebSocket(`ws://localhost:8765/ws/notification/v1?token=${user.access_token}`);

        ws.onmessage = (event) => {
            try {
                const message = JSON.parse(event.data);
                setNotifications(prevResult => [...prevResult, message]);
            } catch (error) {
                console.error("Failed to parse message:", error);
            
            }
        };
        
        ws.onerror = (error) => {
            console.log(error);
        }
    }

    useEffect(() => {
        getNotificationsWithWebSocket();

        return () => {
            
        };
    }, []);

    return (
        <div className={styles.notificationsContainer}>
            <button
                ref={btnRef}
                onClick={handleBtnClick}
            >Notifications</button>
            <div
                ref={resultContainerRef}
                className={`${styles.resultContainer} ${showResults ? styles.show : ''}`}
            >
                <ul>
                    {notifications && notifications.length > 0 ? (
                        notifications.map(notification => (
                            <li key={notification.uuid} data-uuid={notification.uuid} >
                                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                                    <a
                                        href="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                        role="link"
                                        style={{ display: "flex", alignItems: "center" }}
                                    >
                                        <div>
                                            <img
                                                src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                                alt=""
                                                style={{ height: "36px", width: "36px", borderRadius: "50%" }}
                                            />
                                        </div>
                                        <div style={{ display: "flex", flexDirection: "column", marginLeft: "10px" }}>
                                            <span className={styles.resultTitle} style={{ fontWeight: "bold" }}>
                                                {notification.senderName}
                                            </span>
                                        </div>
                                    </a>
                                    <div className={styles.btnContainer}>
                                        <button type-data={1} className={styles.btn} onClick={handleAcceptOrReject}>Reject</button>
                                        <button type-data={2} className={styles.btn} onClick={handleAcceptOrReject}>Accept</button>
                                    </div>
                                </div>
                            </li>
                        ))
                    ) : (
                        <div><span>Test</span></div>
                    )}
                </ul>
            </div>
        </div>
    );
}

export default Notifications;