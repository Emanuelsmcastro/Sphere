import { createContext, useCallback, useContext, useState } from "react";

const ChatsContainerContext = createContext();

export const useChatsContainer = () => {
    return useContext(ChatsContainerContext);
};

export const ChatContainerProvider = ({ children }) => {
    const [chats, setChats] = useState([]);

    const addChat = useCallback((chat) => {
        setChats((prevChats) => {
            const chatExists = prevChats.some(existingChat => existingChat.friendUUID === chat.friendUUID);
            if (!chatExists) {
                return [...prevChats, chat];
            }
            return prevChats;
        });
    }, []);

    const removeChat = useCallback((chat) => {
        setChats((prevChats) => prevChats.filter(chatRef => chatRef.friendUUID !== chat.friendUUID));
    }, []);

    const getChatByFriendUUID = useCallback((friendUUID) => {
        return chats.find(chat => chat.friendUUID === friendUUID);
    }, [chats])

    return (
        <ChatsContainerContext.Provider value={{chats, addChat, removeChat, getChatByFriendUUID}}>
            {children}
        </ChatsContainerContext.Provider>
    )
};