import { useRef } from "react";
import __styles from "../static/css/createLoop.module.css";
import styles from "../static/css/loop.module.css";
import miniModelStyles from "../static/css/miniModel.module.css";
import CreateLoopForm from "./forms/createLoopForm";
import { useModalProvider } from "./modalProvider";
import useClickOutside from "./utils/useClickOutside";

function CreateLoop({ loopImage, loopProfileImage, loopProfileName }) {
    const containerRef = useRef(null);
    const { modalRef, setVisible, setComponent, setTitle, setStyles} = useModalProvider();

    useClickOutside(modalRef, containerRef, () => {
        setVisible(false);
    });


    const handleContainerClick = () => {
        setComponent(() => CreateLoopForm);
        setStyles(() => miniModelStyles);
        setTitle("Add a Loop.");
        setVisible(true);
    };

    return (
        <div
            ref={containerRef}
            className={styles.loopContainer}
            onClick={handleContainerClick}
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
                                className={__styles.mainLoopImage}
                                src={loopImage}
                            />
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
                        <div className={__styles.iconWrapper}>
                            <svg
                                viewBox="0 0 20 20"
                                width="20"
                                height="20"
                                fill="currentColor"
                                className={styles.icon}>
                                <g fillRule="evenodd" transform="translate(-446 -350)">
                                    <g fillRule="nonzero">
                                        <path d="M95 201.5h13a1 1 0 1 0 0-2H95a1 1 0 1 0 0 2z" transform="translate(354.5 159.5)"></path>
                                        <path d="M102.5 207v-13a1 1 0 1 0-2 0v13a1 1 0 1 0 2 0z" transform="translate(354.5 159.5)"> </path>
                                    </g>
                                </g>
                            </svg>
                        </div>
                    </div>
                    <span className={styles.loopTitle}>{loopProfileName}</span>
                </div>
            </div>
        </div>
    );
}


export default CreateLoop;

