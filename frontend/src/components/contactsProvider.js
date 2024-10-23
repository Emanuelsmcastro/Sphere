import React, { createContext, useCallback, useContext, useState } from 'react';

const ContactsContext = createContext();

export const useContacts = () => {
    return useContext(ContactsContext);
};

export const ContactsProvider = ({ children }) => {
    const [contacts, setContacts] = useState([]);

    const getContactByFriendUUID = useCallback(async (friendUUID) => {
        return contacts.find(contact => contact.uuid === friendUUID);
    }, [contacts])

    const addContact = useCallback(async (contact) => {
        setContacts(prevContacts => {
            const existsContact = contacts.some(c => c.uuid === contact.uuid);
            if(!existsContact){
                return [...prevContacts, contact];
            }
            return prevContacts;
        });
    }, [contacts])

    return (
        <ContactsContext.Provider value={{ contacts, setContacts, getContactByFriendUUID, addContact}}>
            {children}
        </ContactsContext.Provider>
    );
};
