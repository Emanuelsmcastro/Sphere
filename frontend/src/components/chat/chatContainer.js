import { useCallback, useEffect } from 'react';
import styles from '../../static/css/chatContainer.module.css';
import { useChatsContainer } from '../providers/chatContainerProvider';
import { useUserManagerProvider } from '../providers/userManagerProvider';
import Chat from './chat';


function ChatContainer(){
    const {chats, addMessageToChat, addChat} = useChatsContainer();

    const { getUser } = useUserManagerProvider();
    
    const connectToWS = useCallback(async () => {
        getUser((user) => {
            const ws = new WebSocket(`${process.env.REACT_APP_GATEWAY_WS_HOST}/ws/chat/v1?token=${user.access_token}`);
    
            ws.onopen = () => {
                console.log("foi");
            };
    
            ws.onmessage = (event) => {
                try {
                    const message = JSON.parse(event.data);
                    addChat(message);
                    console.log(message);
                    addMessageToChat(message.chatUUID, message);
                } catch (error) {
                    console.error("Failed to parse message:", error);
                
                }
            };
            
            ws.onerror = (error) => {
                console.log(error);
            }
        });
        
    }, [getUser, addMessageToChat, addChat]);
    
    useEffect(() => {
        connectToWS();
    }, [connectToWS]);

    return (
        <div className={styles.chatContainer}>
            <ul className={styles.chatsContainer}>
                {chats.map((chat, idx) => (
                    <li
                        key={idx}
                        className={styles.chatItem}>
                        <Chat
                            chat={chat}
                        />
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ChatContainer;