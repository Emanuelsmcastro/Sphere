CREATE TABLE loop_video_db (
    id SERIAL PRIMARY KEY,
    uuid UUID,
    creator_uuid UUID,
    creator_name VARCHAR(50),
    description VARCHAR(255),
    video_url VARCHAR(255),
    created_at TIMESTAMP
);