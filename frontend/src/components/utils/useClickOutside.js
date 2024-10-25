import { useEffect } from "react";

function useClickOutside(containerRef, triggerRef, onOutsideClick) {
    useEffect(() => {
        const handleClickOutside = (event) => {
        if (
            containerRef.current &&
            triggerRef.current &&
            !containerRef.current.contains(event.target) &&
            !triggerRef.current.contains(event.target)
        ) {
            onOutsideClick();
        }
        };

        document.addEventListener("click", handleClickOutside);

        return () => {
            document.removeEventListener("click", handleClickOutside);
        };
    }, [containerRef, triggerRef, onOutsideClick]);
}

export default useClickOutside;
