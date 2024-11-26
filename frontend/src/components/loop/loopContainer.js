import axios from "axios";
import { jwtDecode } from "jwt-decode";
import { useCallback, useEffect, useState } from "react";
import styles from "../../static/css/loopContainer.module.css";
import { useUserManagerProvider } from "../providers/userManagerProvider";
import Carousel from "../utils/carousel";
import CreateLoop from "./createLoop";
import Loop from "./loop";

function LoopContainer(){
    const [loops, setLoops] = useState([]);
    const { getUser } = useUserManagerProvider();
    const [profile, setProfile] = useState({});

    const fetchLoops = useCallback(async () => {
        getUser((user) => {
            axios.get(`${process.env.REACT_APP_GATEWAY_HOST}/video/v1/get-friend-loops`, {
                headers: {
                    "Authorization" : `Bearer ${user.access_token}`
                }
            }).then(response => {
                console.log(response.data);
                setLoops(response.data.content);
            }).catch(error => {
                console.log(error);
            });
        });
    }, [getUser, setLoops]);

    useEffect(() => {
        fetchLoops();
    }, [fetchLoops]);

    useEffect(() => {
        getUser(user => {
            const decodedToken = jwtDecode(user.access_token);
            setProfile(decodedToken.profile);
        });
    }, [getUser, setProfile]);

    return (
        <div className={styles.loopContainer}>
            <Carousel>
                {profile ? (
                    <CreateLoop
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={`${process.env.REACT_APP_GATEWAY_HOST}/image/v1/get-profile-image/${profile.uuid}`}
                        loopProfileName={"Add a Loop"}
                    />
                ) : (
                    <div>Loading profile...</div>
                )}
                <Loop
                    loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                    loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                    loopProfileName={"Demo Loop"}
                    videoSrc={`${process.env.REACT_APP_GATEWAY_HOST}/video/v1/search/65a630db-4839-4a40-8ca7-f0fb2c5115ee/demo.m3u8`}
                />
                {loops.map((loop, idx) => (
                    <Loop
                        key={loop.uuid || idx}
                        loopUUID={loop.uuid}
                        creatorUUID={loop.creatorUUID}
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={`${process.env.REACT_APP_GATEWAY_HOST}/image/v1/get-profile-image/${loop.creatorUUID}`}
                        loopProfileName={loop.creatorName}
                        videoSrc={loop.fileURL}
                    />
                ))}
            </Carousel>
        </div>
    );
}

export default LoopContainer;