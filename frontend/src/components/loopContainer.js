import styles from "../static/css/loopContainer.module.css";
import Carousel from "./carosel";
import Loop from "./loop";

function LoopContainer(){

    return (
        <div className={styles.loopContainer}>
            <Carousel>
                <Loop
                    loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                    loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                    loopProfileName={"Emanuel Castro"}
                    videoSrc={"https://www.youtube.com/watch?v=_PEPaWFs064&t=612s"}
                />
            </Carousel>
            {/* <ul>
                <li>
                    <Loop
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileName={"Emanuel Castro"}
                    />
                </li>
                <hr />
                <li>
                    <Loop
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileName={"Emanuel Castro"}
                    />
                </li>
                <hr />
                <li>
                    <Loop
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileName={"Emanuel Castro"}
                    />
                </li>
                <hr />
                <li>
                    <Loop
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileName={"Emanuel Castro"}
                    />
                </li>
                <hr />
                <li>
                    <Loop
                        loopImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileImage={"https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"}
                        loopProfileName={"Emanuel Castro"}
                    />
                </li>
                <hr />
            </ul>
            <div className={styles.btnContainer}>
                <button type="prev">
                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor">
                    <path d="M14.791 5.207 8 12l6.793 6.793a1 1 0 1 1-1.415 1.414l-7.5-7.5a1 1 0 0 1 0-1.414l7.5-7.5a1 1 0 1 1 1.415 1.414z">
                    </path>
                </svg>
                </button>
                <button type="next">
                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor">
                    <path d="M9.209 5.207 16 12l-6.791 6.793a1 1 0 1 0 1.415 1.414l7.5-7.5a1 1 0 0 0 0-1.414l-7.5-7.5a1 1 0 1 0-1.415 1.414z">
                    </path>
                </svg>
                </button>
            </div> */}
        </div>
    );
}

export default LoopContainer;