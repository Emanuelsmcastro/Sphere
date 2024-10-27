import { useEffect } from "react";

function InfiniteScroll({ containerRef, loadMore, hasMore, children}) {

    useEffect(() => {
        const container = containerRef.current;

        const handleScroll = () => {
            if (container.scrollHeight - container.scrollTop === container.clientHeight && hasMore) {
                loadMore();
            }
        };

        container.addEventListener("scroll", handleScroll);
        return () => container.removeEventListener("scroll", handleScroll);
    }, [loadMore, hasMore, containerRef]);

    return (
        <>
            {children}
        </>
    );
}

export default InfiniteScroll;