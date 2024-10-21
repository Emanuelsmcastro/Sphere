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

    return (
        <ContactsContext.Provider value={{ contacts, setContacts, getContactByFriendUUID}}>
            {children}
        </ContactsContext.Provider>
    );
};
