import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";
import { AiOutlineLike } from "react-icons/ai";
import styles from "../../../static/css/maximizedPost.module.css";
import { useUserManagerProvider } from "../../providers/userManagerProvider";


function MaximizedPost({ postUUID, profileName, description, reactionsCount }) {
    const [comments, setComments] = useState([]);
    const { getUser } = useUserManagerProvider();

    const initialInputMessage = {
        value: ''
    };

    const [inputMessage, setInputMessage] = useState(initialInputMessage);

    const handleInputChange = (e) => {
        const { value } = e.target;
        setInputMessage(prevState => ({
            ...prevState,
            value: value
        }));
    };

    const handleReactionClick = () => {
        getUser(user => {
            axios.post(process.env.REACT_APP_GATEWAY_HOST + "/post/v1/reaction", { postUUID: postUUID, reactionType: 0 }, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                }
            }).then(response => {
            }).catch(error => {
                console.log(error);
            })
        });
    };

    const handleSendCommentClick = () => {
        getUser(user => {
            axios.post(process.env.REACT_APP_GATEWAY_HOST + "/post/v1/comment", { postUUID: postUUID, content: inputMessage.value }, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                }
            }).then(response => {
                const decodedToken = jwtDecode(user.access_token);
                console.log(response);
                setComments(prevArray => [...prevArray, { creatorUUID: decodedToken.profile.uuid, profileName: decodedToken.profile.name, content: inputMessage.value, createdAt: new Date().toISOString() }])
                setInputMessage(initialInputMessage);
            }).catch(error => {
                console.log(error);
            })
        });
    };

    useEffect(() => {
        getUser(user => {
            axios.get(process.env.REACT_APP_GATEWAY_HOST + "/post/v1/comment/search/" + postUUID, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                }
            }).then(response => {
                console.log(response.data.content)
                setComments(response.data.content);
            }).catch(error => {
                console.log(error);
            })
        });
    }, [getUser, postUUID, setComments]);

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
            <div className={styles.commentContainer}>
                {comments && comments.length > 0 ? (
                    comments.map((comment, idx) => (
                        <div
                            key={idx}
                            className={styles.comment}>
                            <div className={styles.profileIcon}>
                                <img
                                    height="40"
                                    width="40"
                                    alt={"Test"}
                                    className={styles.profileImage}
                                    src={`${process.env.REACT_APP_GATEWAY_HOST}/image/v1/get-profile-image/${comment.creatorUUID}`}
                                />
                                <div className={styles.commentContentContainer}>
                                    <span className={styles.profileName}>{comment.profileName}</span>
                                    <div className={styles.commentContent}>{comment.content}</div>
                                </div>
                            </div>
                        </div>
                    ))
                ) : (
                    <div>
                        <p>Create the first comment on this post.</p>
                    </div>
                )}
            </div>
            <div className={styles.createCommentContainer}>
                <textarea
                    placeholder="Write a comment."
                    value={inputMessage.value}
                    onChange={handleInputChange}></textarea>
                <button onClick={handleSendCommentClick}>Send</button>
            </div>
        </div>
    )
}

export default MaximizedPost;