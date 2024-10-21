import styles from '../static/css/chatContainer.module.css';
import Chat from './chat';
import { useChatsContainer } from './chatContainerProvider';


function ChatContainer(){
    const {chats} = useChatsContainer();

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