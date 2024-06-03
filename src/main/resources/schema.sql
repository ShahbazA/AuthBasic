drop table if exists users;
CREATE TABLE users (
  user_id int,
  username varchar,
  password varchar,
  role varchar,
  enabled boolean
);