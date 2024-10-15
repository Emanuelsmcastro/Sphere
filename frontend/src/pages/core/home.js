// src/components/Home.js

import axios from 'axios';
import React, { useContext, useEffect } from 'react';
import UserManagerContext from '../../components/userManagerContext';

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
            }
        };

        fetchData();
    }, [userManager]);

    return (
        <h1>Home Page</h1>
    );
}

export default Home;
