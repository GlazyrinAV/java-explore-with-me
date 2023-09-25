drop table if exists stats;

create table if not exists stats
(
    id        integer generated always as identity
        constraint stats_pk
            primary key,
    app varchar not null,
    timestamp timestamp not null,
    additional_props jsonb
);