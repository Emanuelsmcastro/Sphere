import styles from '../../static/css/trending.module.css';

function Trending(){
    return (
        <div className={styles.trendingContainer}>
            <h3 className={styles.trendingTitle}>Trending Topics</h3>
            <hr />
            <ul className={styles.trendingList}>
                <li><a href="">#Something</a></li>
                <li><a href="">#Something</a></li>
                <li><a href="">#Something</a></li>
            </ul>
        </div>
    );
}

export default Trending;