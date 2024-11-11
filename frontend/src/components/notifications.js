import axios from "axios";
import { useCallback, useContext, useEffect, useRef, useState } from "react";
import styles from "../static/css/notifications.module.css";
import InfiniteScroll from "./infinityScroll";
import { useNotificationContext } from "./notificationContainerProvider";
import UserManagerContext from "./userManagerContext";
import useClickOutside from "./utils/useClickOutside";

function Notifications(){
    const userManager = useContext(UserManagerContext);
    const btnRef = useRef(null);
    const resultContainerRef = useRef(null);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const [showResults, setShowResults] = useState(false);
    const {notifications, setNotifications, notificationCount, setNotificationCount, removeNotificationByUUID} = useNotificationContext();

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
        removeNotificationByUUID(uuid);
    }

    const fetchData = useCallback(async (page) => {
        const user = await userManager.getUser();
        if(!user) return;
        try {
            const url = `${process.env.REACT_APP_GATEWAY_HOST}/notification/v1/get-friend-request-notifications?size=5&page=${page ? page : 0}&sort=createdAt,desc`
            const response = await axios.get(url, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`
                }
            });
            setNotifications(prevNotifications => {
                const newNotifications = response.data.content.filter(newNotification => {
                    return !prevNotifications.some(prevNotification => prevNotification.uuid === newNotification.uuid);
                });
                return [...prevNotifications, ...newNotifications];
            });
            setNotificationCount(prevCount => prevCount > 0 ? prevCount : response.data.page.totalElements);
            setHasMore(response.data.page.number < response.data.page.totalPages - 1);
        } catch (error) {
            console.log(error);
        }
    }, [userManager, setNotifications, setNotificationCount, setHasMore]);

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

    const loadMoreNotifications = useCallback(() => {
        if (hasMore) {
            const nextPage = page + 1;
            setPage(nextPage);
            fetchData(nextPage);
        }
    }, [hasMore, page, fetchData]);

    useEffect(() => {
        fetchData();

    }, [fetchData]);

    return (
        <div className={styles.notificationsContainer}>
            <button
                className={`${styles.btnNotification} ${notifications.length > 0 ? styles.wasNotified: ''}`}
                ref={btnRef}
                onClick={handleBtnClick}
            >Notifications {notificationCount}</button>
            <InfiniteScroll
                containerRef={resultContainerRef}
                loadMore={loadMoreNotifications}
                hasMore={hasMore}>
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
                            <div><span>...</span></div>
                        )}
                    </ul>
                </div>
            </InfiniteScroll>
        </div>
    );
}

export default Notifications;