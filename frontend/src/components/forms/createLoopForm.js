import { useRef, useState } from "react";
import styles from "../../static/css/createLoopForm.module.css";
import { useUserManagerProvider } from "../providers/userManagerProvider";

function CreateLoopForm(){
    const inputFileRef = useRef(null);
    const { getUser } = useUserManagerProvider();
    const [uploadProgress, setUploadProgress] = useState(0);
    const [fileName, setFileName] = useState(null);
    const [file, setFile] = useState(null);

    const handleLabelClick = () => {
        const input = inputFileRef.current;
        input.click();
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        getUser((user) => {
            if(!file) return;
    
            const formData = new FormData();
            formData.append('file', file);
    
            const xhr = new XMLHttpRequest();
            xhr.open("POST", `${process.env.REACT_APP_GATEWAY_HOST}/video/v1/upload-stream`, true);
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

    return(
        <div className={styles.formContainer}>
            {uploadProgress > 0 && (
                <div
                    className={styles.progressBar}
                    style={{ width: '100%'}}>
                    <div
                        style={{
                            width: `${uploadProgress}%`,
                            height: '20px',
                            backgroundColor: '#4caf50',
                            textAlign: 'center',
                            color: 'white',
                        }}
                    >
                        {uploadProgress}%
                    </div>
                </div>
            )}
            <form
                onSubmit={handleSubmit}
                className={styles.form}>
                <div className={styles.fileInf}>
                    <label
                        htmlFor="video"
                        onClick={handleLabelClick}
                    >Choose File</label>
                    <span className={styles.fileName}>{fileName ? (fileName) : ("Select a file.")}</span>
                </div>
                <input
                    ref={inputFileRef}
                    type="file"
                    name="video"
                    id="video"
                    onChange={handleFileChange}/>
                <button>Add</button>
            </form>
        </div>
    );
}

export default CreateLoopForm;