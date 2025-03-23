import { useEffect, useState } from "react";
import styles from "../../static/css/account.module.css";
import { useUserManagerProvider } from "../providers/userManagerProvider";

function AccountSettingsForm () {

    const { getUser } = useUserManagerProvider();
    const [uploadProgress, setUploadProgress] = useState(0);
    const [fileName, setFileName] = useState(null);
    const [file, setFile] = useState(null);

    

    const handleSubmit = (event) => {
        event.preventDefault();
        getUser((user) => {
            if(!file) return;

            const formData = new FormData();
            formData.append('file', file);
    
            const xhr = new XMLHttpRequest();
            xhr.open("POST", `${process.env.REACT_APP_GATEWAY_HOST}/image/v1/upload-stream`, true);
            xhr.setRequestHeader("Authorization", `Bearer ${user.access_token}`)
    
            xhr.upload.onprogress = event => {
                if(event.lengthComputable){
                    const percentCompleted = Math.round((event.loaded * 100) / event.total);
                    setUploadProgress(percentCompleted);
                }
            };
    
            xhr.onload = () => {
                console.log(xhr.response);
            };
    
            xhr.onerror = () => {
            };
    
            xhr.send(formData);
        });
    };

    const handleFileChange = (event) => {
        if (event.target.files.length > 0) {
            const file = event.target.files[0];
            let name = file.name;
            if(name.length > 15) {
                name = "..." + name.slice(-15);
            }
            setFileName(name);
            setFile(file);
            setUploadProgress(0);
            console.log(file);
        }
    };

    useEffect(() => {
        console.log(uploadProgress);
    }, [uploadProgress]);

    return (
        <div className={styles.formContainer}>
            <form
                className={styles.accountForm}
                onSubmit={handleSubmit}>
                <fieldset className={styles.formItem}>
                    <label htmlFor="image">Profile Image</label>
                    <input
                        type="file"
                        accept="image/*"
                        name="image"
                        id="image"
                        onChange={handleFileChange}/>
                </fieldset>
                <button>Update</button>
            </form>
        </div>
    )
}

export default AccountSettingsForm;