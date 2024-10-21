import axios from "axios";
import { createContext, useCallback, useContext, useState } from "react";
import UserManagerContext from "./userManagerContext";

const ChatsContainerContext = createContext();

export const useChatsContainer = () => {
    return useContext(ChatsContainerContext);
};

export const ChatContainerProvider = ({ children }) => {
    const [chats, setChats] = useState([]);
    const userManager = useContext(UserManagerContext);

    const fetchData = useCallback(async (chat) => {
        const user = await userManager.getUser();
        if (!user) return;
        try {
            const response = await axios.get(`${process.env.REACT_APP_GATEWAY_HOST}/chat/v1/get-chat/${chat.friendUUID}`, {
                headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${user.access_token}`,
                },
            });
            chat.chatUUID = response.data.uuid;
            return chat;
        } catch (error) {
            console.log(error);
        }
    }, [userManager]);

    const addChat = useCallback(async (chat) => {
        const updatedChat = await fetchData(chat);
        setChats((prevChats) => {
            const chatExists = prevChats.some(existingChat => existingChat.friendUUID === chat.friendUUID);
            console.log(updatedChat);
            if (!chatExists && updatedChat) {
                return [...prevChats, updatedChat];
            }
            return prevChats;
        });
    }, [fetchData]);

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