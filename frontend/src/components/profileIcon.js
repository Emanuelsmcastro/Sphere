import React from 'react';

const ProfileIcon = (({ loopProfileImage, loopProfileName, styles}) => {
    return (
        <div className={styles.loopProfileIcon}>
            <img
                height="40"
                width="40"
                alt={loopProfileName}
                className={styles.profileImage}
                src={loopProfileImage}
            />
            <span>{loopProfileName}</span>
        </div>
    );
});

export default ProfileIcon;
