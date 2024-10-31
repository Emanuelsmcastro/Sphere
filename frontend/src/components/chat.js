import axios from "axios";
import { format, parseISO } from "date-fns";
import { jwtDecode } from "jwt-decode";
import { useCallback, useContext, useEffect, useRef, useState } from "react";
import styles from "../static/css/chat.module.css";
import { useChatsContainer } from "./chatContainerProvider";
import { useContacts } from "./contactsProvider";
import InfiniteScroll from "./infinityScroll";
import UserManagerContext from "./userManagerContext";

function Chat({ chat }) {
    const { removeChat, chatMessages, setChatMessages, addMessageToChat } = useChatsContainer();
    const { getContactByFriendUUID } = useContacts();
    const userManager = useContext(UserManagerContext);
    const [currentChatContact, setCurrentChatContact] = useState({
        name: ''
    });
    const endOfMessageList = useRef(null);
    const containerRef = useRef(null);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);

    const initialInputMessage = {
        value: ''
    };

    const [inputMessage, setInputMessage] = useState(initialInputMessage);

    const handleInputChange = (e) => {
        const { value } = e.target;
        setInputMessage(prevState => ({
            ...prevState,
            value: value
        }));
    };

    const getHistory = useCallback(async (page, loadAtTheBeginningOfMessages) => {
        const user = await userManager.getUser();
        if (!user && chat.chatUUID) return;
        const decodedToken = jwtDecode(user.access_token);
        const url = `${process.env.REACT_APP_GATEWAY_HOST}/chat/v1/get-chat-messages/${chat.chatUUID}?size=10&page=${page ? page : 0}&sort=createdAt,desc`;
        axios.get(url, {
            headers: {
                'Authorization': `Bearer ${user.access_token}`,
            }
        }).then(response => {
            let modifiedMessages = response.data.content.map(message => {
                if (message.senderUUID === decodedToken.profile.uuid) {
                    message = {
                        ...message,
                        my: true
                    };
                }
                return message;
            });
            modifiedMessages = modifiedMessages.reverse();
            // Needs urgent optimization //
            // But I'm only going to do this towards the end of the project :) //
            setChatMessages((prevMessages) => {
                const existingMessages = prevMessages[chat.chatUUID] || [];
                const filteredExistingMessages = existingMessages.reduce((acc, msg) => {
                    if (!modifiedMessages.some(m => m.messageUUID === msg.messageUUID)) {
                        acc.push(msg);
                    } return acc;
                }, []);
                if (loadAtTheBeginningOfMessages) {
                    return {
                        ...prevMessages, [chat.chatUUID]: [...modifiedMessages, ...filteredExistingMessages]
                    };
                } else {
                    return {
                        ...prevMessages, [chat.chatUUID]: [...filteredExistingMessages, ...modifiedMessages]
                    };
                }
            });
            ///////////////////////////////////////////////////////////////////////////////////////////////////
            setHasMore(response.data.page.number < response.data.page.totalPages - 1);
        }).catch(error => {
            console.log(error);
        });
    }, [setChatMessages, userManager, chat.chatUUID, setHasMore]);

    const sendMessage = async (message) => {
        const user = await userManager.getUser();
        if (!user) return;
        const url = `${process.env.REACT_APP_GATEWAY_HOST}/chat/v1/send-message`;
        axios.post(url, {
            chatUUID: chat.chatUUID,
            sender: null,
            message: message
        }, {
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${user.access_token}`,
            }
        }).then((response) => {
            console.log(response.status);
            addMessageToChat(chat.chatUUID, {
                message: message,
                my: true
            });
            setInputMessage(initialInputMessage);
        }).catch((error) => {
            console.log(error);
        })
    }

    const handleKeyPress = (event) => {
        const value = event.target.value;
        if (event.key === "Enter" && value) {
            sendMessage(value);
        }
    };

    const loadMoreMessages = useCallback(() => {
        if (hasMore) {
            const nextPage = page + 1;
            setPage(nextPage);
            getHistory(nextPage, true);
        }
    }, [hasMore, page, getHistory]);

    const convertDate = (timestamp, my) => {
        if (my && !timestamp) {
            timestamp = new Date().toISOString();
        } if (typeof timestamp !== 'string') {
            timestamp = new Date(timestamp).toISOString();
        }
        const formattedDate = format(parseISO(timestamp), "dd/MM/yyyy HH:mm");
        return formattedDate;
    };

    useEffect(() => {
        setCurrentChatContact(getContactByFriendUUID(chat.friendUUID));
    }, [getContactByFriendUUID, chat.friendUUID]);

    useEffect(() => {
        getHistory();
    }, [getHistory]);

    useEffect(() => {
        const endOfMessages = endOfMessageList.current;
        if (endOfMessages) {
            endOfMessages.scrollIntoView({ behavior: "smooth" });
        }
    }, [chatMessages]);

    return (
        <div className={styles.chatContainer} chat-uuid={chat.chatUUID}>
            <div className={styles.chatHeader}>
                <div style={{ paddingLeft: '8px', paddingRight: '8px' }}>
                    <div
                        href="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                        style={{ display: 'flex', alignItems: 'center' }}
                    >
                        <div style={{ marginRight: '8px' }}>
                            <img
                                src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                alt="Contact"
                                style={{ height: '36px', width: '36px', borderRadius: '50%' }}
                            />
                        </div>
                        <div style={{ display: 'flex', flexDirection: 'column' }}>
                            <span style={{ fontWeight: 'bold' }}>{chat.senderName ? chat.senderName : currentChatContact.name}</span>
                            <span style={{ color: 'green' }}>Online</span>
                        </div>
                    </div>
                </div>
                <div className={styles.chatHeaderBtnContainer}>
                    <button>_</button>
                    <button
                        onClick={() => removeChat(chat)}
                    >x</button>
                </div>
            </div>
            <hr />
            <div className={styles.chatContent}>
                <InfiniteScroll
                    containerRef={containerRef}
                    loadMore={loadMoreMessages}
                    hasMore={hasMore}
                    reversed={true}>
                    <div
                        ref={containerRef}
                        className={styles.chatMessages}>
                        <ul>
                            {chatMessages[chat.chatUUID]?.map((msg, idx) => (
                                <li
                                    key={idx}
                                    className={`${msg.my ? styles.myMessage : ''}`}
                                >
                                    <p>{msg.message}</p>
                                    <span className={styles.date}>{convertDate(msg.createdAt, msg.my)}</span>
                                </li>
                            ))}
                            <div ref={endOfMessageList}></div>
                        </ul>
                    </div>
                </InfiniteScroll>
                <div className={styles.chatFunctions}>
                    <ul>
                        <li className={styles.functionItem}>
                            <input
                                className={styles.inputMessage}
                                type="text"
                                placeholder="Write a message."
                                onKeyDown={handleKeyPress}
                                value={inputMessage.value}
                                onChange={handleInputChange}
                            />
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Chat;