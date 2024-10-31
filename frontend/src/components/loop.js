import styles from "../static/css/loop.module.css";

function Loop({loopImage, loopProfileImage, loopProfileName}) {

    return (
        <div className={styles.loopContainer}>
            <div className={styles.loopContent}>
                <div
                    className={styles.loopLink}
                    href="/stories/169630221189567/UzpfSVNDOjkyNzk3OTQ5NTg1NzA3OQ==/?bucket_count=9&source=story_tray"
                    role="link"
                    tabIndex="0"
                >
                    <div className={styles.loopImageWrapper}>
                        <div className={styles.loopImageContainer}>
                            <img
                                height="100%"
                                alt=""
                                className={styles.mainLoopImage}
                                src={loopImage}
                            />
                        </div>
                        <div className={styles.loopProfileIcon}>
                            <img
                                height="40"
                                width="40"
                                alt="League of Legends"
                                className={styles.profileImage}
                                src={loopProfileImage}
                            />
                        </div>
                    </div>
                    <span className={styles.loopTitle}>{loopProfileName}</span>
                </div>
            </div>
        </div>
    );
};

export default Loop;

