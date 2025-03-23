import { useRef } from "react";
import miniModelStyles from "../../static/css/miniModel.module.css";
import AccountSettingsForm from "../forms/accountSettingsForm";
import { useModalProvider } from "../providers/modalProvider";
import useClickOutside from "../utils/useClickOutside";

function Account () {
    const containerRef = useRef(null);
    const { modalRef, setVisible, setComponent, setTitle, setStyles} = useModalProvider();

    useClickOutside(modalRef, containerRef, () => {
        setVisible(false);
    });

    const handleContainerClick = () => {
        setComponent(() => AccountSettingsForm);
        setStyles(() => miniModelStyles);
        setTitle("Account Settings");
        setVisible(true);
    };


    return (
        <div
            ref={containerRef}
            onClick={handleContainerClick}>
            <button>Account</button>
        </div>
    )
}

export default Account;