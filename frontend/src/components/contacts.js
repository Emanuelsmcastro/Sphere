import axios from 'axios';
import { useCallback, useEffect, useRef, useState } from 'react';
import styles from '../static/css/contacts.module.css';
import { useChatsContainer } from './chatContainerProvider';
import { useContacts } from './contactsProvider';
import InfiniteScroll from './infinityScroll';
import { useUserManagerProvider } from './userManagerContext';

function Contacts(){

    const { getUser } = useUserManagerProvider();
    const containerRef = useRef(null);
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const { contacts, addContacts} = useContacts();
    const { addChat } = useChatsContainer();

    const fetchData = useCallback(async (page) => {
        getUser(async (user) => {
            try {
                const url = `${process.env.REACT_APP_OAUTH_HOST}/oauth/v1/private/get-friends?size=10&page=${page ? page : 0}`
                const response = await axios.get(url, {
                    headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                    },
                });
                console.log(response.data)
                addContacts(response.data.content);
                setHasMore(response.data.page.number < response.data.page.totalPages - 1);
            } catch (error) {
                console.log(error);
            }
        });
    }, [getUser, addContacts, setHasMore]);

    const handleClick = (contact) => {
        addChat({
            friendUUID: contact.uuid,
        });
    }

    const loadMoreContacts= useCallback(() => {
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
        <div className={styles.contactContainer}>
            <h3 className={styles.contactTitle}>Contacts</h3>
            <hr />
            <InfiniteScroll
                containerRef={containerRef}
                loadMore={loadMoreContacts}
                hasMore={hasMore}>
                <div ref={containerRef}
                    className={styles.resultContainer}>
                    <ul
                        className={styles.contactList}>
                        {contacts && contacts.length > 0 ? (
                            contacts.map(contact => (
                                <li
                                    key={contact.uuid}
                                    data-uuid={contact.uuid}
                                    onClick={() => handleClick(contact)}
                                >
                                    <div style={{ paddingLeft: '8px', paddingRight: '8px' }}>
                                        <div
                                            style={{ display: 'flex', alignItems: 'center', cursor: 'pointer'}}
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
            </InfiniteScroll>
        </div>
    );
}

export default Contacts;