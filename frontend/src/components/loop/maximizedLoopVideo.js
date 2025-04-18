import axios from "axios";
import Hls from "hls.js";
import { useCallback, useEffect, useRef, useState } from "react";
import styles from "../../static/css/maximizedLoopVideo.module.css";
import { useUserManagerProvider } from "../providers/userManagerProvider";
import ProfileIcon from "../utils/profileIcon";

function MaximizedLoopVideo({initialLoopUUID, creatorUUID, loopProfileImage, loopProfileName, videoSrc }) {
    const [currentVideoUUID, setCurrentVideoUUID] = useState(null);
    const [loopVideos, setLoopVideos] = useState([]);
    const [videoDuration, setVideoDuration] = useState(0);
    const [currentVideoDuration, setCurrentVideoDuration] = useState(0);
    const videoRef = useRef(null);
    const { getUser } = useUserManagerProvider();

    const loadVideo = useCallback(async (videoURL) => {
        const video = videoRef.current;
        if (!video || !Hls.isSupported()) return;

        getUser((user) => {
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
                video.onloadedmetadata = function() {
                    setVideoDuration(video.duration);
                }
            });
            hls.loadSource(videoURL);
            hls.attachMedia(video);
        });

    }, [getUser, setVideoDuration]);

    const setCurrentVideo = useCallback((videoUUID, videoURL) => {
        setCurrentVideoUUID(videoUUID);
        loadVideo(videoURL);
    }, [setCurrentVideoUUID, loadVideo]);

    const getLoopVideos = useCallback(async () => {
        getUser((user) => {
            axios.get(`${process.env.REACT_APP_GATEWAY_HOST}/video/v1/get-all-videos-by-creator-uuid/${creatorUUID}`, {
                headers: {
                    "Authorization" : `Bearer ${user.access_token}`
                }
            }).then(response => {
                console.log(response.data);
                setLoopVideos(response.data);
            }).catch(error => {
                console.log(error);
            });
        });

    }, [getUser, creatorUUID, setLoopVideos]);

    const handleNextClick = () => {
        const currentVideoIdx = loopVideos.findIndex(video => video.uuid === currentVideoUUID);
        const nextVideoIdx = currentVideoIdx + 1;
        if(nextVideoIdx > loopVideos.length - 1) return;
        const nextLoopVideo = loopVideos[nextVideoIdx];
        console.log(nextLoopVideo);
        setCurrentVideo(nextLoopVideo.uuid, nextLoopVideo.fileURL);
    };

    const handleBackClick = () => {
        const currentVideoIdx = loopVideos.findIndex(video => video.uuid === currentVideoUUID);
        const nextVideoIdx = currentVideoIdx - 1;
        if(nextVideoIdx < 0) return;
        const nextLoopVideo = loopVideos[nextVideoIdx];
        console.log(nextLoopVideo);
        setCurrentVideo(nextLoopVideo.uuid, nextLoopVideo.fileURL);
    }

    useEffect(() => {
        setCurrentVideo(initialLoopUUID, videoSrc);
    }, [setCurrentVideo, videoSrc, initialLoopUUID]);

    useEffect(() => {
        getLoopVideos();
    }, [getLoopVideos]);

    useEffect(() => {
        const video = videoRef.current;
        if(!video || videoDuration === 0) return;
        const playingEvent = () => {
            let videoProgressPercent = Math.round((video.currentTime / videoDuration) * 100);
            if(videoProgressPercent > 100) videoProgressPercent = 100;
            setCurrentVideoDuration(videoProgressPercent);
        };

        video.addEventListener("timeupdate", playingEvent);

        return () => {
            video.removeEventListener("timeupdate", playingEvent);
        };
    }, [videoRef, videoDuration, setCurrentVideoDuration]);

    return (
        <div>
            <div className={styles.loopContent}>
                <div className={styles.videoContent}>
                    <div className={styles.loopVideoIndex}>
                        {loopVideos.map((loopVideo) => (
                            <div
                                key={loopVideo.uuid}
                                className={`${styles.loopVideoBar} ${loopVideo.uuid === currentVideoUUID ? styles.currentLoopVideo : ""}`}>
                                    <div
                                        className={styles.loopVideoBarProgress}
                                        style={{"width": `${currentVideoDuration}%`}}
                                    ></div>
                                </div>
                        ))}
                    </div>
                    <ProfileIcon
                        loopProfileImage={loopProfileImage}
                        loopProfileName={loopProfileName}
                        styles={styles}/>
                    <video
                        id="video"
                        ref={videoRef}
                        controls
                        autoPlay
                        muted
                        loop
                        className={styles.loopVideo}
                        >
                    Your browser does not support the video tag.
                    </video>
                    <div className={styles.btnContainer}>
                        <button
                            onClick={handleBackClick}>
                            <svg viewBox="0 0 24 24" width="48" height="48" fill="currentColor">
                                <path d="M14.791 5.207L8 12l6.793 6.793a1 1 0 1 1-1.415 1.414l-7.5-7.5a1 1 0 0 1 0-1.414l7.5-7.5a1 1 0 1 1 1.415 1.414z"></path>
                            </svg>
                        </button>
                        <button
                            onClick={handleNextClick}>
                            <svg viewBox="0 0 24 24" width="48" height="48" fill="currentColor">
                                <path d="M9.209 5.207L16 12l-6.791 6.793a1 1 0 1 0 1.415 1.414l7.5-7.5a1 1 0 0 0 0-1.414l-7.5-7.5a1 1 0 1 0-1.415 1.414z"></path>
                            </svg>
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MaximizedLoopVideo;
