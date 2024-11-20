import React from 'react';
import ChatContainer from '../../components/chatContainer.js';
import Contacts from '../../components/contacts';
import Header from '../../components/header';
import Timeline from '../../components/timeline';
import Trending from '../../components/trending';
import styles from '../../static/css/home.module.css';


function Home() {
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
