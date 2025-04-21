create materialized view books_per_author as
    select a.id as id, a.surname as surname, count(*) as num_of_books
    from author as a
    join public.book as b
        on a.id = b.author_id
    group by a.id
    order by num_of_books desc;

create materialized view authors_per_country as
    select c.name as name, count(*) as num_of_authors
    from author as a
    join country as c
    on a.country_of_origin_id = c.id
    group by c.id
    order by num_of_authors desc
