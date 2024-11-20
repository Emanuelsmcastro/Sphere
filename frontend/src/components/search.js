import axios from "axios";
import debounce from "lodash.debounce";
import { useCallback, useEffect, useRef, useState } from "react";
import styles from "../static/css/search.module.css";
import InfiniteScroll from "./infinityScroll";
import { useUserManagerProvider } from "./userManagerContext";
import useClickOutside from "./utils/useClickOutside";

function Search() {
    const { getUser } = useUserManagerProvider();
    const [showResults, setShowResults] = useState(false);
    const [users, setUsers] = useState([]);
    const [inputValue, setInputValue] = useState("");
    const [page, setPage] = useState(0);
    const [hasMore, setHasMore] = useState(true);
    const searchInputRef = useRef(null);
    const resultContainerRef = useRef(null);

    useClickOutside(resultContainerRef, searchInputRef, () => {
        setShowResults(false);
    });

    const fetchData = useCallback(async (value, page) => {
        try {
            const url = `${process.env.REACT_APP_OAUTH_HOST}/oauth/v1/private/search/${value}?size=8&page=${page}`;
            const response = await axios.get(url, {
                headers: {
                    'Accept': 'application/json'
                }
            });
            console.log(response.data);
            setUsers(prevUsers => [...prevUsers, ...response.data.content]);
            setHasMore(response.data.page.number < response.data.page.totalPages - 1);
        } catch (error) {
            console.log(error);
        }
    }, [setUsers, setHasMore]);

    const invite = async (uuid) => {
        getUser(async (user) => {
            const postData = { "receiver": uuid };
            try {
                const response = await axios.post(process.env.REACT_APP_GATEWAY_HOST + "/publisher/v1/friend-request", postData, {
                    headers: {
                        'Accept': 'application/json',
                        'Authorization': `Bearer ${user.access_token}`
                    }
                });
                console.log("Publisher Response: " + response.status);
            } catch (error) {
                console.log(error);
            }
        });
    };

    const handleInputClick = () => {
        setShowResults(true);
    };

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleInvite = (e) => {
        const searchItem = e.target.parentNode.parentNode;
        invite(searchItem.getAttribute("data-uuid"));
    };

    const loadMoreUsers = useCallback(() => {
        if (hasMore) {
            const nextPage = page + 1;
            setPage(nextPage);
            fetchData(inputValue, nextPage);
        }
    }, [hasMore, inputValue, page, fetchData]);

    useEffect(() => {
        const searchInput = document.querySelector(`.${styles.searchInput}`);
        const resultContainer = document.querySelector(`.${styles.searchResult}`);
        searchInput.addEventListener("click", () => {
            resultContainer.classList.add(styles.show);
        });
        return () => {
            searchInput.removeEventListener("click", () => { });
        };
    }, []);

    useEffect(() => {
        const debouncedRequest = debounce((value) => {
            fetchData(value, 0);
        }, 500);

        if (inputValue) {
            debouncedRequest(inputValue);
            setUsers([]);
            setPage(0);
        }

        return () => {
            debouncedRequest.cancel();
        };
    }, [inputValue, fetchData]);

    return (
        <div className={styles.searchContainer}>
            <input
                ref={searchInputRef}
                className={styles.searchInput}
                type="text"
                name="search"
                id="headerSearch"
                placeholder='Search the Sphere.'
                onChange={handleInputChange}
                onClick={handleInputClick}
                value={inputValue}
            />
            <InfiniteScroll
                containerRef={resultContainerRef}
                loadMore={loadMoreUsers}
                hasMore={hasMore}>
                <div
                    ref={resultContainerRef}
                    className={`${styles.searchResult} ${showResults ? styles.show : ''}`}
                >
                    <ul>
                        {users && users.length > 0 ? (
                            users.map((user) => (
                                <li key={user.uuid} data-uuid={user.uuid}>
                                    <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
                                        <a
                                            href="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                            role="link"
                                            style={{ display: "flex", alignItems: "center" }}
                                        >
                                            <div>
                                                <img
                                                    src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                                    alt={user.name}
                                                    style={{ height: "36px", width: "36px", borderRadius: "50%" }}
                                                />
                                            </div>
                                            <div style={{ display: "flex", flexDirection: "column", marginLeft: "10px" }}>
                                                <span className={styles.resultTitle} style={{ fontWeight: "bold" }}>
                                                    {user.name}
                                                </span>
                                            </div>
                                        </a>
                                        <button onClick={handleInvite} className={styles.btn}>Invite</button>
                                    </div>
                                </li>
                            ))
                        ) : (
                            <p>Nenhum usuário encontrado.</p>
                        )}
                    </ul>
                </div>
            </InfiniteScroll>
        </div>
    );
}

export default Search;
