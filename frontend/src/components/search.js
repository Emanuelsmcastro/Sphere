import axios from "axios";
import debounce from "lodash.debounce";
import { useCallback, useEffect, useState } from "react";
import styles from "../static/css/search.module.css";

function Search(){
    const [inputValue, setInputValue] = useState("");

    const fetchData = async (value) => {
        try {
            const response = await axios.get("http://localhost:9001/oauth/v1/private/search/" + value, {
                headers: {
                    'Accept': 'application/json'
                }
            });
            console.log(response.data);
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
        
        searchInput.addEventListener("focusout", event => {
            resultContainer.classList.remove(styles.show);
        });
        return () => {

        }
    }, []);

    const handleInputChange = (e) => {
        setInputValue(e.target.value);
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
                    <li>
                        <div>
                            <a href="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg" role="link" tabIndex="0" style={{ display: 'flex', alignItems: 'center' }}>
                                <div>
                                    <img
                                        src="https://as2.ftcdn.net/v2/jpg/01/04/70/49/1000_F_104704911_qDKDQEttQEsKpf3dioPxCkKCx30PaPuH.jpg"
                                        alt="Contact"
                                        style={{ height: '36px', width: '36px', borderRadius: '50%' }} 
                                    />
                                </div>
                                <div style={{ display: 'flex', flexDirection: 'column' }}>
                                    <span className={styles.resultTitle} style={{ fontWeight: 'bold' }}>Emanuel Silva de Moraes Castro</span>
                                </div>
                            </a>
                            <button className={styles.btn}>Invite</button>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    );
}

export default Search;