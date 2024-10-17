import styles from '../static/css/timeline.module.css';

function Timeline() {
    return (
        <div className={styles.timelineContainer}>
            <ul className={styles.postList}>
                <li>
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
                                <div style={{ fontWeight: 'bold' }}>Someone</div>
                                <div style={{ color: '#65676b', fontSize: '12px' }}>5 horas atrás</div>
                            </div>
                        </div>
                        <div style={{ marginBottom: '12px' }}>
                            <p style={{ lineHeight: '1.5' }}>
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque volutpat venenatis est eu feugiat. Curabitur scelerisque, odio vel facilisis pellentesque, libero magna rutrum ante, in porttitor eros arcu quis risus. Etiam efficitur risus est, a convallis nisl interdum et. Mauris molestie purus posuere augue porta luctus. Integer efficitur, turpis eu dignissim lobortis, ex nisi lobortis nulla, malesuada dictum diam sem in magna. Curabitur eleifend id elit ac pulvinar. Donec venenatis maximus nisi, sed pretium tellus congue nec. Pellentesque in augue eget augue accumsan sodales a sit amet metus. Donec facilisis dolor arcu, sit amet condimentum risus pellentesque vitae. Duis elementum ex et libero auctor tincidunt a nec magna. Nulla facilisi. Phasellus tempor, sapien in bibendum gravida, velit enim varius eros, vel blandit tellus quam vitae nisi. Praesent sed ipsum non nisi pellentesque viverra in id velit. Duis placerat hendrerit tortor, ac venenatis eros laoreet eu.
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
                <li>
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
                                <div style={{ fontWeight: 'bold' }}>Someone</div>
                                <div style={{ color: '#65676b', fontSize: '12px' }}>5 horas atrás</div>
                            </div>
                        </div>
                        <div style={{ marginBottom: '12px' }}>
                            <p style={{ lineHeight: '1.5' }}>
                                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque volutpat venenatis est eu feugiat. Curabitur scelerisque, odio vel facilisis pellentesque, libero magna rutrum ante, in porttitor eros arcu quis risus. Etiam efficitur risus est, a convallis nisl interdum et. Mauris molestie purus posuere augue porta luctus. Integer efficitur, turpis eu dignissim lobortis, ex nisi lobortis nulla, malesuada dictum diam sem in magna. Curabitur eleifend id elit ac pulvinar. Donec venenatis maximus nisi, sed pretium tellus congue nec. Pellentesque in augue eget augue accumsan sodales a sit amet metus. Donec facilisis dolor arcu, sit amet condimentum risus pellentesque vitae. Duis elementum ex et libero auctor tincidunt a nec magna. Nulla facilisi. Phasellus tempor, sapien in bibendum gravida, velit enim varius eros, vel blandit tellus quam vitae nisi. Praesent sed ipsum non nisi pellentesque viverra in id velit. Duis placerat hendrerit tortor, ac venenatis eros laoreet eu.
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
            </ul>
        </div>
    );
}

export default Timeline;
