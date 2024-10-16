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
                const response = await axios.get("http://localhost:9001/oauth/v1/private", {
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
            const token = await userManager.getUser();
            try {
                const response = await axios.get("http://localhost:8001/notification/v1", {
                    headers: {
                        'Accept': 'application/json',
                        'Authorization': `Bearer ${token.access_token}`
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
        </>
    );
}

export default Home;
