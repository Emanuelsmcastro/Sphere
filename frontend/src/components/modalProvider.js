import React, { createContext, useContext, useRef, useState } from 'react';
import _styles from "../static/css/model.module.css";

const ModalContext = createContext();

export const useModalProvider = () => {
    return useContext(ModalContext);
};

export const ModalProvider = ({ children }) => {
    const [visible, setVisible] = useState(false);
    const [styles, setStyles] = useState(_styles);
    const [Component, setComponent] = useState(null);
    const [title, setTitle] = useState("");
    const modalRef = useRef(null);

    
    const handleCloseClick = () => {
        setVisible(false);
    }

    return (
        <ModalContext.Provider value={{ modalRef, setVisible, setTitle, setComponent, setStyles }}>
            {children}
            {visible && (
                <div
                    ref={modalRef}
                    className={styles.modelContainer}
                >
                    <div className={styles.modelHeader}>
                        <h3 className={styles.modelTitle}>{title}</h3>
                        <span
                            className={styles.modelClose}
                            onClick={handleCloseClick}
                        >X</span>
                    </div>
                    <hr />
                    {Component && (
                        <Component />
                    )}
                </div>
            )}
        </ModalContext.Provider>
    );
};
