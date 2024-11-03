import { useRef, useState } from "react";
import styles from "../../static/css/createLoopForm.module.css";

function CreateLoopForm(){
    const inputFileRef = useRef(null);
    const [fileName, setFileName] = useState(null);

    const handleLabelClick = () => {
        const input = inputFileRef.current;
        input.click();
    };

    const handleFileChange = (event) => {
        if (event.target.files.length > 0) {
            let name = event.target.files[0].name;
            if(name.length > 15) {
                name = "..." + name.slice(-15);
            }
            setFileName(name);
        }
    };

    return(
        <div className={styles.formContainer}>
            <form className={styles.form}>
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