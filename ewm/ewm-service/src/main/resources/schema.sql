drop table if exists users;

create table if not exists users
(
    id    integer
        primary key GENERATED ALWAYS AS IDENTITY,
    name  varchar(255) not null,
    email varchar(255) not null unique
);

drop table if exists category;

create table if not exists category
(
    id   integer
        primary key GENERATED ALWAYS AS IDENTITY,
    name varchar not null unique
);

drop table if exists state;

create table if not exists state
(
    id         integer
        primary key GENERATED ALWAYS AS IDENTITY,
    state_name integer not null unique
);

drop table if exists locations;

create table if not exists locations
(
    id  integer
        primary key GENERATED ALWAYS AS IDENTITY,
    lat double precision not null,
    lon double precision not null
);

drop table if exists event;

create table if not exists event
(
    id                 integer
        primary key GENERATED ALWAYS AS IDENTITY,
    title              varchar   not null,
    annotation         varchar   not null,
    initiator_id       integer   not null,
    confirmed_requests integer,
    category_id        integer   not null,
    created_on         timestamp,
    description        varchar,
    event_date         timestamp not null,
    location_id        integer   not null,
    paid               boolean   not null,
    participant_limit  integer,
    published_on       timestamp,
    request_moderation boolean,
    state_id           integer,
    views              integer
);