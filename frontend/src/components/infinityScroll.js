import { useEffect } from "react";

function InfiniteScroll({ containerRef, loadMore, hasMore, reversed, children}) {

    useEffect(() => {
        const container = containerRef.current;

        const handleScroll = () => {
            
            if (container.scrollHeight - container.scrollTop === container.clientHeight && hasMore && !reversed) {
                loadMore();
            } else if(container.scrollTop === 0 && hasMore){
                loadMore();
            }
        };

        container.addEventListener("scroll", handleScroll);
        return () => container.removeEventListener("scroll", handleScroll);
    }, [loadMore, hasMore, containerRef, reversed]);

    return (
        <>
            {children}
        </>
    );
}

export default InfiniteScroll;