// import styles from "../static/css/loop.module.css";

// function Loop({loopImage, loopProfileImage, loopProfileName}) {

//     return (
//         <div className={styles.loopContainer}>
//             <div className={styles.loopContent}>
//                 <div
//                     className={styles.loopLink}
//                     role="link"
//                     tabIndex="0"
//                 >
//                     <div className={styles.loopImageWrapper}>
//                         <div className={styles.loopImageContainer}>
//                             <img
//                                 height="100%"
//                                 alt=""
//                                 className={styles.mainLoopImage}
//                                 src={loopImage}
//                             />
//                         </div>
//                         <div className={styles.loopProfileIcon}>
//                             <img
//                                 height="40"
//                                 width="40"
//                                 alt="League of Legends"
//                                 className={styles.profileImage}
//                                 src={loopProfileImage}
//                             />
//                         </div>
//                     </div>
//                     <span className={styles.loopTitle}>{loopProfileName}</span>
//                 </div>
//             </div>
//         </div>
//     );
// };

// export default Loop;

import { useContext, useRef } from "react";
import styles from "../static/css/loop.module.css";
import UserManagerContext from "./userManagerContext";

function Loop({ loopImage, loopProfileImage, loopProfileName, videoSrc }) {
    const videoRef = useRef(null);
    const userManager = useContext(UserManagerContext);

    const handleMouseEnter = () => {
        if (videoRef.current) {
            const playPromisse = videoRef.current.play();
            if(playPromisse !== undefined) {
                playPromisse.then(() => {

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

    // useEffect(() => {
    //     userManager.getUser().then(user => {
    //         if(!videoRef.current) return;

    //         const mediaSource = new MediaSource();
    //         videoRef.current.src = URL.createObjectURL(mediaSource);

    //         mediaSource.addEventListener('sourceopen', () => {
    //             const sourceBuffer = mediaSource.addSourceBuffer('video/mp4; codecs="avc1.64001E, mp4a.40.2"');

    //             const xhr = new XMLHttpRequest();
    //             xhr.open('GET', `${process.env.REACT_APP_GATEWAY_HOST}/video/v1`, true);
    //             xhr.setRequestHeader('Authorization', `Bearer ${user.access_token}`);
    //             xhr.responseType = 'arraybuffer';

    //             xhr.onload = () => {
    //                 if(xhr.status === 200){
    //                     sourceBuffer.appendBuffer(new Uint8Array(xhr.response));
    //                 }
    //                 console.log(xhr.response);
    //             };

    //             xhr.send();
    //     });
    //     }).catch(error => {
    //         console.log(error);
    //     })
    // }, [userManager, videoRef]);

    return (
        <div
            className={styles.loopContainer}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
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
                            {/* <video
                                ref={videoRef}
                                className={styles.loopVideo}
                                muted
                            /> */}
                            <video
                                ref={videoRef}
                                src={`${process.env.REACT_APP_GATEWAY_HOST}/video/v1`}
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
