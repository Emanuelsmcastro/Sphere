import React, { useEffect, useRef, useState } from 'react';
import styles from "../static/css/carousel.module.css";

const Carousel = ({ children }) => {
    const carouselRef = useRef(null);
    const [isAtStart, setIsAtStart] = useState(true);
    const [isAtEnd, setIsAtEnd] = useState(false);

    const scrollLeft = () => {
        if (carouselRef.current) {
            carouselRef.current.scrollBy({ left: -200, behavior: 'smooth' });
        }
    };

    const scrollRight = () => {
        if (carouselRef.current) {
            carouselRef.current.scrollBy({ left: 200, behavior: 'smooth' });
        }
    };

    const checkScrollPosition = () => {
        const { scrollLeft, scrollWidth, clientWidth } = carouselRef.current;
        setIsAtStart(scrollLeft === 0);
        setIsAtEnd(scrollLeft + clientWidth >= scrollWidth);
    };

    useEffect(() => {
        const carouselElement = carouselRef.current;
        carouselElement.addEventListener('scroll', checkScrollPosition);

        checkScrollPosition();

        return () => {
            carouselElement.removeEventListener('scroll', checkScrollPosition);
        };
    }, []);

    return (
        <div className={styles.carouselContainer}>
            <button className={styles.carouselButton} onClick={scrollLeft} aria-label="Previous" aria-hidden={isAtStart}>
                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor">
                    <path d="M14.791 5.207L8 12l6.793 6.793a1 1 0 1 1-1.415 1.414l-7.5-7.5a1 1 0 0 1 0-1.414l7.5-7.5a1 1 0 1 1 1.415 1.414z" />
                </svg>
            </button>
            <div className={styles.carousel} ref={carouselRef}>
                {children}
            </div>
            <button className={styles.carouselButton} onClick={scrollRight} aria-label="Next" aria-hidden={isAtEnd}>
                <svg viewBox="0 0 24 24" width="24" height="24" fill="currentColor">
                    <path d="M9.209 5.207L16 12l-6.791 6.793a1 1 0 1 0 1.415 1.414l7.5-7.5a1 1 0 0 0 0-1.414l-7.5-7.5a1 1 0 1 0-1.415 1.414z" />
                </svg>
            </button>
        </div>
    );
};

export default Carousel;