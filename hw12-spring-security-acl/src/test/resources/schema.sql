create table users (
    id bigserial,
    displayed_name varchar(255),
    username varchar(255),
    password varchar(255),
    primary key (id)
);

create table user_roles (
    id bigserial,
    user_id bigint references users (id) on delete cascade,
    name varchar(255),
    primary key (id)
);

create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    primary key (id)
);

create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

create table book_comments (
    id bigserial,
    book_id bigint references books(id) on delete cascade,
    text varchar(255),
    primary key (id)
);