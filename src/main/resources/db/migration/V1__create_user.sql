CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name varchar(266),
    email varchar(266) UNIQUE,
    password varchar(266),
    document varchar(266) UNIQUE,
    type varchar(266),
    wallet varchar(266)
)