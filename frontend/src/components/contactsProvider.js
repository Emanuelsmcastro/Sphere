import React, { createContext, useCallback, useContext, useState } from 'react';

const ContactsContext = createContext();

export const useContacts = () => {
    return useContext(ContactsContext);
};

export const ContactsProvider = ({ children }) => {
    const [contacts, setContacts] = useState([]);

    const getContactByFriendUUID = useCallback((friendUUID) => {
        return contacts.find(contact => contact.uuid === friendUUID);
    }, [contacts])

    const addContact = useCallback(async (contact) => {
        setContacts(prevContacts => {
            const existsContact = contacts.some(c => c.uuid === contact.uuid);
            if(!existsContact){
                return [contact, ...prevContacts];
            }
            return prevContacts;
        });
    }, [contacts])

    const addContacts = useCallback((contacts) => {
        setContacts(prevContacts => {
            const newContacts = contacts.filter(newNotification => {
                return !prevContacts.some(prevContact => prevContact.uuid === newNotification.uuid);
            });
            return [...prevContacts, ...newContacts];
        });
    }, [setContacts]);

    return (
        <ContactsContext.Provider value={{ contacts, setContacts, getContactByFriendUUID, addContact, addContacts}}>
            {children}
        </ContactsContext.Provider>
    );
};
