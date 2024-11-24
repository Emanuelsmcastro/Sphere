import axios from 'axios';
import { useCallback, useEffect, useState } from 'react';
import styles from '../../static/css/timeline.module.css';
import LoopContainer from '../loop/loopContainer';
import { useUserManagerProvider } from '../providers/userManagerProvider';
import CreatePost from './post/createPost';

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
                            <div style={{ border: '1px solid #e9e9e9', borderRadius: '8px', margin: '16px 0', padding: '16px', backgroundColor: '#fff' }}>
                                <div style={{ display: 'flex', alignItems: 'flex-start', marginBottom: '12px' }}>
                                    <img 
                                        src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                        alt="User"
                                        style={{ height: '40px', width: '40px', borderRadius: '50%', marginRight: '12px' }}
                                        width={592}
                                        height={333}
                                    />
                                    <div>
                                        <div style={{ fontWeight: 'bold' }}>{post.profileName}</div>
                                        <div style={{ color: '#65676b', fontSize: '12px' }}></div>
                                    </div>
                                </div>
                                <div style={{ marginBottom: '12px' }}>
                                    <p style={{ lineHeight: '1.5' }}>
                                        {post.description}
                                    </p>
                                </div>
                                <div>
                                    <img 
                                        src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                        alt="Post content"
                                        style={{ width: '100%', borderRadius: '8px', marginTop: '12px' }}
                                    />
                                </div>
                                <div style={{ marginTop: '12px', display: 'flex', justifyContent: 'space-between' }}>
                                    <button style={{
                                        background: 'none',
                                        border: 'none',
                                        color: '#1877f2',
                                        cursor: 'pointer',
                                        fontWeight: 'bold'
                                    }}>
                                        Like
                                    </button>
                                    <div>
                                        <button style={{
                                            background: 'none',
                                            border: 'none',
                                            color: '#65676b',
                                            cursor: 'pointer',
                                            marginRight: '16px'
                                        }}>
                                            Comment
                                        </button>
                                        <button style={{
                                            background: 'none',
                                            border: 'none',
                                            color: '#65676b',
                                            cursor: 'pointer'
                                        }}>
                                            Share
                                        </button>
                                    </div>
                                </div>
                            </div>
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
