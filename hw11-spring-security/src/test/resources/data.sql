insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3'),
       ('Genre_4'), ('Genre_5'), ('Genre_6');

insert into books(title, author_id)
values ('BookTitle_1', 1), ('BookTitle_2', 2), ('BookTitle_3', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 4),
       (3, 5),   (3, 6);

insert into book_comments(book_id, text)
values (1, 'Book_1__Comment_1'), (1, 'Book_1__Comment_2'),
       (2, 'Book_2__Comment_1'), (2, 'Book_2__Comment_2'),
       (3, 'Book_3__Comment_1'), (3, 'Book_3__Comment_2');