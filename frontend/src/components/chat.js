import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useCallback, useContext, useEffect, useState } from "react";
import styles from "../static/css/chat.module.css";
import { useChatsContainer } from "./chatContainerProvider";
import { useContacts } from "./contactsProvider";
import UserManagerContext from "./userManagerContext";

function Chat({ chat }){
    const {removeChat, chatMessages, setChatMessages, addMessageToChat} = useChatsContainer();
    const {getContactByFriendUUID} = useContacts();
    const userManager = useContext(UserManagerContext);
    const [currentChatContact, setCurrentChatContact] = useState({
        name: ''
    });

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

    const getHistory = useCallback(async () => {
        const user = await userManager.getUser();
        if(!user && chat.chatUUID) return;
        const decodedToken = jwtDecode(user.access_token);
        const url =  `${process.env.REACT_APP_GATEWAY_HOST}/chat/v1/get-chat-messages/${chat.chatUUID}`;
        axios.get(url, {
            headers: {
                'Authorization': `Bearer ${user.access_token}`,
            }
        }).then(response => {
            const modifiedMessages = response.data.content.map(message => {
                if(message.senderUUID === decodedToken.profile.uuid){
                    message = {
                        ...message,
                        my: true
                    };
                }
                return message;
            });
            console.log(modifiedMessages, chat.chatUUID);
            setChatMessages((prevMessages) => ({
                ...prevMessages,
                [chat.chatUUID]: [...(prevMessages[chat.chatUUID] || []), ...modifiedMessages]
            }));
        }).catch(error => {
            console.log(error);
        });
    }, [setChatMessages]);

    const sendMessage = async (message) => {
        const user = await userManager.getUser();
        if(!user) return;
        const url =  `${process.env.REACT_APP_GATEWAY_HOST}/chat/v1/send-message`;
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
        if(event.key === "Enter" && value){
            sendMessage(value);
        }
    };
    
    useEffect(() => {
        setCurrentChatContact(getContactByFriendUUID(chat.friendUUID));
    }, [getContactByFriendUUID, chat.friendUUID]);

    useEffect(() => {
        getHistory();
    }, [getHistory]);

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
                <div className={styles.chatMessages}>
                    <ul>
                        {chatMessages[chat.chatUUID]?.map((msg, idx) => (
                            <li
                                key={idx}
                                className={`${msg.my ? styles.myMessage : ''}`}
                                >
                                <p>{msg.message}</p>
                            </li>
                        ))}
                    </ul>
                </div>
                <div className={styles.chatFunctions}>
                    <ul>
                        <li className={styles.functionItem}>
                            <input
                            className={styles.inputMessage}
                            type="text"
                            placeholder="Write a message."
                            onKeyDown={handleKeyPress}
                            value={inputMessage.value}
                            onChange={handleInputChange }
                            />
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Chat;