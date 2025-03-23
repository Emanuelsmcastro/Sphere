import axios from 'axios';
import { useCallback, useEffect, useState } from 'react';
import styles from '../../static/css/timeline.module.css';
import LoopContainer from '../loop/loopContainer';
import { useUserManagerProvider } from '../providers/userManagerProvider';
import CreatePost from './post/createPost';
import Post from './post/post';

function Timeline() {
    const [posts, setPosts] = useState([]);
    const { getUser } = useUserManagerProvider();
    
    const fetchData = useCallback(async () => {
        getUser(async (user) => {
            try {
                const response = await axios.get(`${process.env.REACT_APP_GATEWAY_HOST}/post/v1/get-friend-posts`, {
                    headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                    },
                });
                console.log(response.data.content);
                setPosts(response.data.content);
            } catch (error) {
                console.log(error);
            }
        });
    
    }, [getUser]);

    useEffect(() => {
        fetchData();
    }, [fetchData])

    return (
        <div className={styles.timelineContainer}>
            <ul className={styles.postList}>
                <li>
                    <CreatePost/>
                </li>
                <li>
                    <LoopContainer/>
                </li>
                {posts && posts.length > 0 ? (
                    posts.map(post => (
                        <li key={post.postUUID}>
                            <Post
                                postUUID={post.postUUID}
                                profileName={post.profileName}
                                description={post.description}
                            />
                        </li>
                    ))
                ) : (
                    <li className={styles.notfoundPosts}>
                        <div>
                            <p>
                            Posts were not found, try loading them manually.</p>
                        </div>
                            <button onClick={fetchData}>Reload Posts</button>
                    </li>
                )}
            </ul>
        </div>
    );
}

export default Timeline;
