import styles from '../static/css/header.module.css';

function Header(){
    return (
        <header className={styles.headerContainer}>
            <div className={styles.container}>
                <div className={styles.searchContainer}>
                    <h1 className={styles.headerTitle}>Sphere</h1>
                    <input className={styles.searchInput} type="search" name="search" id="headerSearch" placeholder='Pesquise na Sphere.'/>
                </div>
                <ul className={styles.listContainer}>
                    <li><button>Notifications</button></li>
                    <li><a className={styles.link} href="">Messages</a></li>
                    <li><a className={styles.link}  href="">Account</a></li>
                </ul>
            </div>
        </header>
    );
}

export default Header;