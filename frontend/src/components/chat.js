import { useEffect, useState } from "react";
import styles from "../static/css/chat.module.css";
import { useChatsContainer } from "./chatContainerProvider";
import { useContacts } from "./contactsProvider";

function Chat({ chat }){
    const {removeChat} = useChatsContainer();
    const {getContactByFriendUUID} = useContacts();
    const [currentChatContact, setCurrentChatContact] = useState({
        name: ''
    });

    useEffect(() => {
        setCurrentChatContact(getContactByFriendUUID(chat.friendUUID));
    }, [getContactByFriendUUID]);

    return (
        <div className={styles.chatContainer}>
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
                            <span style={{ fontWeight: 'bold' }}>{currentChatContact.name}</span>
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
                        <li className={styles.myMessage}>
                            <p>Lorem ipst</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam nec lacus non lectus rutrum dictum. Morbi ullamcorper ipsum ac rutrum pharetra. Nulla in nulla non nulla vulputate ultrices et id eros. Quisque tempus nisl mauris, vel finibus sem luctus quis. Mauris accumsan arcu et dapibus auctor. Nunc luctus erat ac elit fringilla pulvinar. Etiam consectetur enim urna, nec ultricies nunc efficitur posuere. Curabitur suscipit dolor et turpis bibendum interdum eget eget orci. Sed in ligula non ligula feugiat convallis. Nulla finibus tincidunt tincidunt. Phasellus iaculis mauris lacus, non eleifend orci accumsan eu. Duis et lacus et risus consectetur eleifend in quis ex.</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li className={styles.myMessage}>
                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam nec lacus non lectus rutrum dictum. Morbi ullamcorper ipsum ac rutrum pharetra. Nulla in nulla non nulla vulputate ultrices et id eros. Quisque tempus ni</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                        <li>
                            <p>Lorem ipsum dolor sit amet</p>
                        </li>
                    </ul>
                </div>
                <div className={styles.chatFunctions}>
                    <ul>
                        <li className={styles.functionItem}><input className={styles.inputMessage} type="text" placeholder="Write a message."/></li>
                    </ul>
                </div>
            </div>
        </div>
    );
}

export default Chat;