import axios from 'axios';
import { useContext, useEffect, useState } from 'react';
import styles from '../static/css/contacts.module.css';
import UserManagerContext from './userManagerContext';

function Contacts(){
    const userManager = useContext(UserManagerContext);
    const [contacts, setContacts] = useState([]);

    const fetchData = async () => {
        const user = await userManager.getUser();
        if(!user) return;
        try {
            const response = await axios.get(process.env.REACT_APP_OAUTH_HOST + "/oauth/v1/private/get-friends", {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`
                }
            });
            console.log(response.data.content);
            setContacts(response.data.content);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        fetchData();
        return () => {

        };
    }, []);

    return (
        <div className={styles.contactContainer}>
            <h3 className={styles.contactTitle}>Contacts</h3>
            <hr />
            <ul className={styles.contactList}>
                {contacts && contacts.length > 0 ? (
                    contacts.map(contact => (
                        <li key={contact.uuid} data-uuid={contact.uuid}>
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
                                        <span style={{ fontWeight: 'bold' }}>{contact.name}</span>
                                        <span style={{ color: 'green' }}>Online</span>
                                    </div>
                                </div>
                            </div>
                        </li>
                    ))
                ) : (
                    <span>All your friends will appear here.</span>
                )}
            </ul>
        </div>
    );
}

export default Contacts;