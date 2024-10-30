import { useCallback, useContext, useEffect } from 'react';
import styles from '../static/css/chatContainer.module.css';
import Chat from './chat';
import { useChatsContainer } from './chatContainerProvider';
import UserManagerContext from './userManagerContext';


function ChatContainer(){
    const {chats, addMessageToChat, addChat} = useChatsContainer();

    const userManager = useContext(UserManagerContext);
    
    const connectToWS = useCallback(async () => {
        const user = await userManager.getUser();
        if(!user) return;
        
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
    }, [userManager, addMessageToChat, addChat]);
    
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