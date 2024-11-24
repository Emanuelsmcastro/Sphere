import { UserManager } from 'oidc-client';
import React, { useCallback, useContext, useEffect, useState } from 'react';

const UserManagerContext = React.createContext(null);

export const useUserManagerProvider = () => {
    return useContext(UserManagerContext);
};

export const UserManagerProvider = ({ config, children }) => {
    const [userManager, setUserManager] = useState(null);

    const getUser = useCallback(async (callback) => {
        if (!userManager) return;
        try {
            const user = await userManager.getUser();

            if(!user) {
                window.location.href = "/oauth/login";
                return;
            }

            if (callback && typeof callback === 'function') {
                return callback(user);
            }
        } catch (error) {
            console.error("Failed to fetch user:", error);
        }
    }, [userManager]);

    useEffect(() => {
        if(!config) return;
        const userManager = new UserManager(config);
        setUserManager(userManager);
    }, [config, setUserManager]);

    return (
        <UserManagerContext.Provider value={ { getUser, userManager} }>
            {children}
        </UserManagerContext.Provider>
    );
};

export default UserManagerContext;