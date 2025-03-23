import Hls from "hls.js";
import { useEffect, useRef } from "react";
import styles from "../../static/css/loop.module.css";
import _styles from "../../static/css/model.module.css";
import { useModalProvider } from "../providers/modalProvider";
import { useUserManagerProvider } from "../providers/userManagerProvider";
import MaximizedLoopVideo from "./maximizedLoopVideo";

function Loop({loopUUID, creatorUUID, loopImage, loopProfileImage, loopProfileName, videoSrc }) {
    const { getUser } = useUserManagerProvider();
    const videoRef = useRef(null);
    const {setVisible, setComponent, setTitle, setStyles} = useModalProvider();

    const handleClick = () => {
        setComponent(() => () => (
            <MaximizedLoopVideo
                initialLoopUUID={loopUUID}
                creatorUUID={creatorUUID}
                loopProfileImage={loopProfileImage}
                loopProfileName={loopProfileName}
                videoSrc={videoSrc}
            />
        ));
        setStyles(() => _styles);
        setTitle("Loop Video");
        setVisible(true);
    }

    const handleMouseEnter = () => {
        if (videoRef.current) {
            const playPromise = videoRef.current.play();
            if (playPromise !== undefined) {
                playPromise.then(() => {

                }).catch(error => {
                    console.log(error);
                });
            }
        }
    };

    const handleMouseLeave = () => {
        if (videoRef.current) {
            videoRef.current.pause();
            //videoRef.current.currentTime = 0;
        }
    };

    useEffect(() => {
        const video = videoRef.current;

        getUser(async (user) => {
            if (!video || !Hls.isSupported()) return;

            const hls = new Hls({
                xhrSetup: xhr => {
                    xhr.setRequestHeader("Authorization", `Bearer ${user.access_token}`)
                }
            });

            hls.on(Hls.Events.MEDIA_ATTACHED, () => {
                console.log('video and hls.js are now bound together !');
            });
            hls.on(Hls.Events.MANIFEST_PARSED, function (event, data) {
                console.log(
                    'manifest loaded, found ' + data.levels.length + ' quality level',
                );
            });
            hls.loadSource(videoSrc);
            hls.attachMedia(video);
        });

    }, [videoSrc, getUser]);

    return (
        <div
            className={styles.loopContainer}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
            onClick={handleClick}
        >
            <div className={styles.loopContent}>
                <div
                    className={styles.loopLink}
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
                            <video
                                id="video"
                                ref={videoRef}
                                controls
                                autoPlay
                                muted
                                className={styles.loopVideo}
                            >
                                Your browser does not support the video tag.
                            </video>
                        </div>
                        <div className={styles.loopProfileIcon}>
                            <img
                                height="40"
                                width="40"
                                alt={loopProfileName}
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
}

export default Loop;
