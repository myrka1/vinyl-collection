BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, records, users_records, collections, users_collections;

CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	is_premium boolean NOT NULL,
	CONSTRAINT PK_users PRIMARY KEY (user_id)
);

CREATE TABLE records (
    record_id SERIAL,
    record_artist varchar(100) NOT NULL,
    record_title varchar(100) NOT NULL,
    musicbrainz_id varchar(100),
    record_front varchar(200),
    record_back varchar(200),
    CONSTRAINT PK_records PRIMARY KEY (record_id)
);

CREATE TABLE collections (
    collection_id SERIAL,
    user_id int NOT NULL,
    CONSTRAINT PK_collections PRIMARY KEY (collection_id),
    CONSTRAINT FK_collections_users_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE users_records (
    user_id int NOT NULL,
    record_id int NOT NULL,
    CONSTRAINT FK_users_records FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_users_records_id FOREIGN KEY (record_id) REFERENCES records (record_id)
);

CREATE TABLE users_collections (
    user_id int NOT NULL,
    collection_id int NOT NULL,
    collection_title varchar(100) NOT NULL,
    collection_notes VARCHAR(150),
    CONSTRAINT FK_users_collections_user_id FOREIGN KEY (user_id) REFERENCES users (user_id),
    CONSTRAINT FK_users_collections_collection_id FOREIGN KEY (collection_id) REFERENCES collections (collection_id)
);

CREATE TABLE collections_records (
    collection_id int NOT NULL,
    record_id int NOT NULL,
    CONSTRAINT FK_users_records FOREIGN KEY (collection_id) REFERENCES collections (collection_id),
    CONSTRAINT FK_users_records_id FOREIGN KEY (record_id) REFERENCES records (record_id)
);

COMMIT TRANSACTION;
