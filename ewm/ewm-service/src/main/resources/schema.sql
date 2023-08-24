drop table if exists event, users, category, event, locations, compilation;

create table if not exists users
(
    id    integer generated always as identity
        primary key,
    name  varchar(255) not null,
    email varchar(255) not null unique
);

create table if not exists category
(
    id   integer generated always as identity
        primary key,
    name varchar(255) not null unique
);

create table if not exists locations
(
    id  integer generated always as identity
        primary key,
    lat double precision not null,
    lon double precision not null
);

create table if not exists event
(
    id                 integer generated always as identity
        primary key,
    title              varchar(255) not null,
    annotation         varchar(255) not null,
    initiator_id       integer      not null
        constraint event_users_id_fk
            references users,
    confirmed_requests integer,
    category_id        integer      not null
        constraint event_category_id_fk
            references category,
    created_on         timestamp,
    description        varchar(255),
    event_date         timestamp    not null,
    location_id        integer      not null
        constraint event_locations_id_fk
            references locations,
    paid               boolean      not null,
    participant_limit  integer,
    published_on       timestamp,
    request_moderation boolean,
    state              varchar(255) not null,
    views              integer
);

create table if not exists compilation
(
    id       integer generated always as identity
        primary key,
    title    varchar(255) not null,
    pinned   boolean      not null,
    event_id integer      not null
        constraint compilation_event_id_fk
            references public.event
);