import { useCallback, useEffect } from "react";

function useClickOutside(containerRef, triggerRef, onOutsideClick) {
    const memoizedCallback = useCallback(onOutsideClick, [onOutsideClick]);

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (
                containerRef?.current &&
                triggerRef?.current &&
                !containerRef.current.contains(event.target) &&
                !triggerRef.current.contains(event.target)
            ) {
                memoizedCallback();
            }
        };

        document.addEventListener("click", handleClickOutside, true);

        return () => {
            document.removeEventListener("click", handleClickOutside, true);
        };
    }, [containerRef, triggerRef, memoizedCallback]);
}

export default useClickOutside;
