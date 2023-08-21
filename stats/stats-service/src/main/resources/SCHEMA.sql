drop table if exists stats;

create table if not exists stats
(
    id        integer generated always as identity
        constraint stats_pk
            primary key,
    app_name  varchar   not null,
    uri       varchar   not null,
    ip        varchar   not null,
    timestamp timestamp not null
);