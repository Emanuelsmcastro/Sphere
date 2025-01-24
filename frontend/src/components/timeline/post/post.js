import axios from "axios";
import { useEffect, useState } from "react";
import { AiOutlineLike } from "react-icons/ai";
import _styles from "../../../static/css/model.module.css";
import styles from "../../../static/css/post.module.css";
import { useModalProvider } from "../../providers/modalProvider";
import { useUserManagerProvider } from "../../providers/userManagerProvider";
import MaximizedPost from "./maximizedPost";


function Post({ postUUID, profileName, description }) {
    const { getUser } = useUserManagerProvider();
    const {setVisible, setComponent, setTitle, setStyles} = useModalProvider();
    const [reactionsCount, setReactionsCount] = useState(0);

    const handleCommentClick = () => {
        setComponent(() => () => (
            <MaximizedPost
                postUUID={postUUID}
                profileName={profileName}
                description={description}
                reactionsCount={reactionsCount}
            />
        ));
        setStyles(() => _styles);
        setTitle(profileName);
        setVisible(true);
    }

    const handleReactionClick = () => {
        getUser(user => {
            axios.post(process.env.REACT_APP_GATEWAY_HOST + "/post/v1/reaction", { postUUID: postUUID, reactionType: 0 }, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                }
            }).then( response => {
                if(response.status === 200)
                    setReactionsCount((prevCount) => prevCount + 1);
            }).catch( error => {
                console.log(error);
            })
        });
    };

    useEffect(() => {
        getUser(user => {
            axios.get(process.env.REACT_APP_GATEWAY_HOST + "/post/v1/reaction/search/" + postUUID, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                }
            }).then( response => {
                setReactionsCount(response.data.page.totalElements);
            }).catch( error => {
                console.log(error);
            })
        });
    }, [getUser, postUUID, setReactionsCount]);

    return (
        <div className={styles.postContainer}>
            <div style={{ display: 'flex', alignItems: 'flex-start', marginBottom: '12px' }}>
                <img
                    src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                    alt="User"
                    style={{ height: '40px', width: '40px', borderRadius: '50%', marginRight: '12px' }}
                    width={592}
                    height={333}
                />
                <div>
                    <div style={{ fontWeight: 'bold' }}>{profileName}</div>
                    <div style={{ color: '#65676b', fontSize: '12px' }}></div>
                </div>
            </div>
            <div style={{ marginBottom: '12px' }}>
                <p style={{ lineHeight: '1.5' }}>
                    {description}
                </p>
            </div>
            <div>
                <img
                    src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                    alt="Post content"
                    style={{ width: '100%', borderRadius: '8px', marginTop: '12px' }}
                />
            </div>
            <div className={styles.statisticPost}>
                <div className={styles.reactionIcons}>
                    <AiOutlineLike />
                </div>
                <span className={styles.reactionCount}>{reactionsCount}</span>
            </div>
            <div className={styles.functionsPost}>
                <button style={{
                    background: 'none',
                    border: 'none',
                    color: '#1877f2',
                    cursor: 'pointer',
                    fontWeight: 'bold'
                }}
                onClick={handleReactionClick}>
                    Like
                </button>
                <button style={{
                    background: 'none',
                    border: 'none',
                    color: '#65676b',
                    cursor: 'pointer',
                    marginRight: '16px'
                }}
                onClick={handleCommentClick}>
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
    )
}

export default Post;