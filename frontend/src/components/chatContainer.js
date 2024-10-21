import styles from '../static/css/chatContainer.module.css';
import Chat from './chat';

function ChatContainer(){

    return (
        <div className={styles.chatContainer}>
            <ul className={styles.chatsContainer}>
                <li className={styles.chatItem}>
                    <Chat/>
                </li>
                <li className={styles.chatItem}>
                    <Chat/>
                </li>
            </ul>
        </div>
    );
}

export default ChatContainer;