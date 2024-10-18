import axios from "axios";
import debounce from "lodash.debounce";
import { useCallback, useContext, useEffect, useState } from "react";
import styles from "../static/css/search.module.css";
import UserManagerContext from "./userManagerContext";

function Search(){
    const userManager = useContext(UserManagerContext);
    const [users, setUsers] = useState([]);
    const [inputValue, setInputValue] = useState("");

    const fetchData = async (value) => {
        try {
            const response = await axios.get(process.env.REACT_APP_OAUTH_HOST + "/oauth/v1/private/search/" + value, {
                headers: {
                    'Accept': 'application/json'
                }
            });
            setUsers(response.data.content);
        } catch (error) {
            console.log(error);
        }
    };

    const invite = async (uuid) => {
        const user = await userManager.getUser();
        if(!user) return;
        const postData = {
            "receiver": uuid
        }
        try {
            const response = await axios.post(process.env.REACT_APP_GATEWAY_HOST + "/publisher/v1/friend-request", postData,{
                headers: {
                    'Accept': 'application/json',
                    'Authorization': `Bearer ${user.access_token}`
                }
            });
            console.log("Publisher Response: " + response.status);
        } catch (error) {
            console.log(error);
        }
    };

    useEffect(() => {
        const searchInput = document.querySelector(`.${styles.searchInput}`);
        const resultContainer = document.querySelector(`.${styles.searchResult}`)

        searchInput.addEventListener("click", event => {
            resultContainer.classList.add(styles.show);
        });

        const handleClickOutside = event => {
            if(
                searchInput && resultContainer &&
                !searchInput.contains(event.target) &&
                !resultContainer.contains(event.target)
            ) {
                resultContainer.classList.remove(styles.show);
            }
        };

        document.addEventListener("click", handleClickOutside);

        return () => {
            searchInput.removeEventListener("click", () => {});
            document.removeEventListener("click", handleClickOutside);
        };

    }, []);

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
    };

    const handleInvite = (e) => {
        const searchItem = e.target.parentNode.parentNode;
        invite(searchItem.getAttribute("data-uuid"));
    };

    const debouncedRequest = useCallback(debounce((value) => {
        fetchData(value);
    }, 500), []);
    
    useEffect(() => {
        if (inputValue) {
            debouncedRequest(inputValue);
        }
    }, [inputValue, debouncedRequest]);

    return (
        <div className={styles.searchContainer}>
            <input className={styles.searchInput} type="text" name="search" id="headerSearch" placeholder='Search the Sphere.' onChange={handleInputChange} value={inputValue}/>
            <div className={`${styles.searchResult}`}>
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
        </div>
    );
}

export default Search;