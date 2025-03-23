import React from 'react';
import ChatContainer from '../../components/chat/chatContainer';
import Contacts from '../../components/contact/contacts';
import Timeline from '../../components/timeline/timeline';
import Trending from '../../components/timeline/trending';
import Header from '../../components/utils/header';
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
