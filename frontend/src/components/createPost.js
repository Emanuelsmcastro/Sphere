import axios from "axios";
import { useContext, useEffect, useRef, useState } from "react";
import styles from "../static/css/createPost.module.css";
import UserManagerContext from "./userManagerContext";
import useClickOutside from "./utils/useClickOutside";

function CreatePost() {
    const resultContainerRef = useRef(null);
    const optionsBtnRef = useRef(null);
    const inputOptionsAddHashtag = useRef(null);
    const userManager = useContext(UserManagerContext);
    const [showExtraContainer, setShowExtraContainer] = useState(false);
    const [hashtags, setHashtags] = useState([]);

    const initialFormData = {
        description: '',
        metaInf: {
            hashtags: []
        }
    };

    const [formData, setFormData] = useState(initialFormData);

    useClickOutside(resultContainerRef, optionsBtnRef, () => {
        setShowExtraContainer(false);
    });

    const createPostRequest = async () => {
        const user = await userManager.getUser();
        if(!user) return;
        axios.post(process.env.REACT_APP_GATEWAY_HOST + "/publisher/v1/create-post-request", formData, {
            headers: {
                'Accept': 'application/json',
                'Authorization': `Bearer ${user.access_token}`,
            }
        }).then((response) => {
            console.log(response.status);
        }).catch((error) => {
            console.log(error);
        });
    };

    const handleOnSubmit = (e) => {
        e.preventDefault();
        createPostRequest();
        setFormData(initialFormData);
        setHashtags([]);
    };

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({
            ...prevState,
            [name]: value !== undefined ? value : ''
        }));
    };
    
    const handleOptionsBtnClick = () => {
        setShowExtraContainer(true);
    };

    const handleOptionsAddClick = (e) => {
        e.preventDefault();
        const value = inputOptionsAddHashtag.current.value.trim();
        const isValid = /^#[A-Za-z0-9]+$/.test(value);
        if (!isValid) {
            return;
        }
    
        setHashtags(prevHashtags => {
            if (!prevHashtags.includes(value)) {
                return [...prevHashtags, value];
            }
            return prevHashtags;
        });
    
        inputOptionsAddHashtag.current.value = "";
    };

    const handleHashtagClick = (e) => {
        e.preventDefault();
        e.stopPropagation();
        setHashtags(prevHashtags => prevHashtags.filter(prev => prev !== e.target.innerText));
    };

    useEffect(() => {
        setFormData(prevFormData => ({
            ...prevFormData,
            metaInf: {
                ...prevFormData.metaInf,
                hashtags: hashtags
            }
        }));
    }, [hashtags]);
    
    return (
        <div className={styles.createPostContainer}>
            <div className={styles.formContainer}>
                <form
                    className={styles.form}
                    onSubmit={handleOnSubmit}>
                    <input
                        type="text"
                        name="description"
                        id="description"
                        placeholder="Share your ideas!"
                        value={formData.description}
                        onChange={handleInputChange} />
                    <button>Post</button>
                </form>
            </div>
            <ul className={styles.extraPost}>
                <li>
                    <button ref={optionsBtnRef} onClick={handleOptionsBtnClick}>Options</button>
                </li>
            </ul>
            <div className={`${styles.extraContainer} ${showExtraContainer ? styles.show : ''}`} ref={resultContainerRef}>
                <p style={{ textAlign: "center" }}>Here you can set some meta information for your post.</p>
                <hr />
                <div className={styles.options}>
                    <input
                        ref={inputOptionsAddHashtag}
                        type="text"
                        name="hashtag"
                        id="hashtag"
                        placeholder="Add a hashtag | ex: #Something | #something123"
                    />
                    <button onClick={handleOptionsAddClick} style={{ padding: "2px 40px", height: "40px" }}>
                        Add
                    </button>
                </div>
                <ul className={styles.hashtagList}>
                    {hashtags.map((hashtag, idx) => (
                        <li
                        key={idx}
                        style={{color: "#FF4B2B", cursor: "pointer"}}
                        onClick={handleHashtagClick}>
                            {hashtag}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default CreatePost;
