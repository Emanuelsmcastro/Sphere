// src/components/Home.js

import axios from 'axios';
import React, { useContext, useEffect } from 'react';
import Contacts from '../../components/contacts';
import Header from '../../components/header';
import Timeline from '../../components/timeline';
import Trending from '../../components/trending';
import UserManagerContext from '../../components/userManagerContext';
import styles from '../../static/css/home.module.css';

function Home() {
    const userManager = useContext(UserManagerContext);
    useEffect(() => {
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

    useEffect(() => {
        const fetchData = async () => {
            const user = await userManager.getUser();
            console.log(user);
            if(!user) return;
            const postData = {
                "receiver": "cc18e402-c087-46cc-9005-a0defc39d01c"
            }
            try {
                const response = await axios.post(process.env.REACT_APP_GATEWAY_HOST + "/publisher/v1/friend-request", postData,{
                    headers: {
                        'Accept': 'application/json',
                        'Authorization': `Bearer ${user.access_token}`
                    }
                });
                console.log("Publisher Response: " + response.status);
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
        </>
    );
}

export default Home;
