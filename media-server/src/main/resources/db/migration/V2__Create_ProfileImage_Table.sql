CREATE TABLE profile_image_db (
    id SERIAL PRIMARY KEY,
    uuid UUID,
    filename VARCHAR(255),
    profile_uuid UUID,
    profile_image_url VARCHAR(255),
    is_main_profile_image BOOLEAN,
    created_at TIMESTAMP
);