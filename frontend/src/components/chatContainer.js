import { useCallback, useContext, useEffect } from 'react';
import styles from '../static/css/chatContainer.module.css';
import Chat from './chat';
import { useChatsContainer } from './chatContainerProvider';
import UserManagerContext from './userManagerContext';


function ChatContainer(){
    const {chats} = useChatsContainer();

    const userManager = useContext(UserManagerContext);
    
    const connectToWS = useCallback(async () => {
        const user = await userManager.getUser();
        console.log(user);
    }, [userManager]);
    
    useEffect(() => {
        connectToWS();
    }, [connectToWS]);

    return (
        <div className={styles.chatContainer}>
            <ul className={styles.chatsContainer}>
                {chats.map((chat) => (
                    <li
                        key={chat.friendUUID}
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