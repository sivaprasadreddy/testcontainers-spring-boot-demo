create table bookmarks
(
    id         bigserial primary key,
    url        varchar   not null,
    title      varchar   not null,
    created_at timestamp not null
);