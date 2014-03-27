# --- First database schema

# --- !Ups

CREATE TABLE link(
token                         VARCHAR(255) NOT NULL PRIMARY KEY,
url_target                    VARCHAR(255) NOT NULL,
url_short                     VARCHAR(255) NOT NULL
);

# --- !Downs

drop table if exists link;

