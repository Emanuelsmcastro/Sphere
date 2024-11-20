import axios from "axios";
import { useCallback, useContext, useEffect, useState } from "react";
import styles from "../static/css/loopContainer.module.css";
import CreateLoop from "./createLoop";
import Loop from "./loop";
import UserManagerContext from "./userManagerContext";
import Carousel from "./utils/carosel";

function LoopContainer(){
    const [loops, setLoops] = useState([]);
    const userManager = useContext(UserManagerContext);

    const fetchLoops = useCallback(async () => {
        const user = await userManager.getUser();
        if(!user) return;

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
    }, [userManager, setLoops]);

    useEffect(() => {
        fetchLoops();
    }, [fetchLoops]);
    return (
        <div className={styles.loopContainer}>
            <Carousel>
                <CreateLoop
                    loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                    loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                    loopProfileName={"Add a Loop"}
                />
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
                        loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileName={loop.creatorName}
                        videoSrc={loop.fileURL}
                    />
                ))}
            </Carousel>
        </div>
    );
}

export default LoopContainer;