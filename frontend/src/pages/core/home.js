// src/components/Home.js

import axios from 'axios';
import { jwtDecode } from 'jwt-decode';
import React, { useContext, useEffect } from 'react';
import ChatContainer from '../../components/chatContainer.js';
import Contacts from '../../components/contacts';
import Header from '../../components/header';
import Timeline from '../../components/timeline';
import Trending from '../../components/trending';
import UserManagerContext from '../../components/userManagerContext';
import styles from '../../static/css/home.module.css';


function Home() {
    const userManager = useContext(UserManagerContext);
    useEffect(() => {
        userManager.getUser().then(user => {
            console.log(user);
            const decodedToken = jwtDecode(user.access_token);
            console.log(decodedToken);
        }).catch(error => {
            console.log(error);
        });
        const fetchData = async () => {
            try {
                const response = await axios.get(process.env.REACT_APP_OAUTH_HOST + "/oauth/v1/private", {
                    headers: {
                        'Accept': 'application/json'
                    }
                });
                console.log(response.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchData();
    }, [userManager]);

    return (
        <>
            <Header />
            <div className={styles.mainContainer}>
                <Trending/>
                <Timeline/>
                <Contacts/>
            </div>
                <ChatContainer/>
        </>
    );
}

export default Home;
