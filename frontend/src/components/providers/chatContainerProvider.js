import axios from "axios";
import { createContext, useCallback, useContext, useState } from "react";
import { useUserManagerProvider } from "./userManagerProvider";

const ChatsContainerContext = createContext();

export const useChatsContainer = () => {
    return useContext(ChatsContainerContext);
};

export const ChatContainerProvider = ({ children }) => {
    const [chats, setChats] = useState([]);
    const [chatMessages, setChatMessages] = useState({});
    const { getUser } = useUserManagerProvider();

    const fetchData = useCallback(async (chat) => {
        return getUser(async (user) => {
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
        });
    }, [getUser]);

    const removeChat = useCallback((chat) => {
        console.log(chats);
        setChats((prevChats) => prevChats.filter(chatRef => chatRef.chatUUID !== chat.chatUUID));
        setChatMessages((prevMessages) => {
            const newMessages = { ...prevMessages };
            delete newMessages[chat.chatUUID];
            return newMessages;
        });
    }, [chats]);

    const addChat = useCallback(async (chat) => {
        const updatedChat = chat.chatUUID ? chat : await fetchData(chat);
        console.log(updatedChat);
        setChats((prevChats) => {
            const chatExists = prevChats.some(existingChat => existingChat.chatUUID === updatedChat.chatUUID);
            if (!chatExists && updatedChat) {
                if(prevChats.length > 2) {
                    const removedChat = prevChats.shift();
                    setChatMessages(prevMessages => {
                        const newMessages = { ...prevMessages };
                        delete newMessages[removedChat.chatUUID];
                        return newMessages;
                    });
                }
                return [...prevChats, updatedChat];
            }
            return prevChats;
        });
    }, [fetchData]);

    const getChatByFriendUUID = useCallback((friendUUID) => {
        return chats.find(chat => chat.friendUUID === friendUUID);
    }, [chats]);

    const addMessageToChat = useCallback((chatUUID, message) => {
        setChatMessages((prevMessages) => {
            const messagesRef = prevMessages[chatUUID] || [];
            const existsMessage = messagesRef.some(msg => msg.messageUUID === message.messageUUID);
            if(!existsMessage || !message.messageUUID){
                return {
                    ...prevMessages,
                    [chatUUID]: [...(prevMessages[chatUUID] || []), message]
                }
            }
            return prevMessages;
        });
    }, []);

    return (
        <ChatsContainerContext.Provider value={{chats, addChat, removeChat, getChatByFriendUUID, chatMessages, setChatMessages, addMessageToChat}}>
            {children}
        </ChatsContainerContext.Provider>
    )
};