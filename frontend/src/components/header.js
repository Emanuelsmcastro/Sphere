import styles from '../static/css/header.module.css';
import Notifications from './notifications';
import Search from './search';

function Header(){
    return (
        <header className={styles.headerContainer}>
            <div className={styles.container}>
                <div className={styles.searchContainer}>
                    <h1 className={styles.headerTitle}>Sphere</h1>
                    <Search />
                </div>
                <ul className={styles.listContainer}>
                    <li><Notifications/></li>
                    <li><a className={styles.link}  href="">Messages</a></li>
                    <li><a className={styles.link}  href="">Account</a></li>
                </ul>
            </div>
        </header>
    );
}

export default Header;