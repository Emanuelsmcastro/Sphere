import axios from "axios";
import { useUserManagerProvider } from "../../providers/userManagerProvider";

function Post({ postUUID, profileName, description }) {
    const { getUser } = useUserManagerProvider();

    const reactionClick = () => {
        getUser(user => {
            axios.post(process.env.REACT_APP_GATEWAY_HOST + "/post/v1/reaction", { postUUID: postUUID, reactionType: 0 }, {
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`,
                }
            }).then( response => {
                console.log(response);
            }).catch( error => {
                console.log(error);
            })
        });
    };

    return (
        <div
            style={{ border: '1px solid #e9e9e9', borderRadius: '8px', margin: '16px 0', padding: '16px', backgroundColor: '#fff' }}>
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
            <div style={{ marginTop: '12px', display: 'flex', justifyContent: 'space-between' }}>
                <button style={{
                    background: 'none',
                    border: 'none',
                    color: '#1877f2',
                    cursor: 'pointer',
                    fontWeight: 'bold'
                }}
                onClick={reactionClick}>
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
    )
}

export default Post;