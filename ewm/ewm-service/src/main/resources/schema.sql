drop table if exists event, users, category, state, event, locations;

create table if not exists users
(
    id    integer
        primary key GENERATED ALWAYS AS IDENTITY,
    name  varchar(255) not null,
    email varchar(255) not null unique
);

create table if not exists category
(
    id   integer
        primary key GENERATED ALWAYS AS IDENTITY,
    name varchar not null unique
);

create table if not exists state
(
    id         integer
        primary key GENERATED ALWAYS AS IDENTITY,
    state_name integer not null unique
);

create table if not exists locations
(
    id  integer
        primary key GENERATED ALWAYS AS IDENTITY,
    lat double precision not null,
    lon double precision not null
);

create table if not exists event
(
    id                 integer
        primary key GENERATED ALWAYS AS IDENTITY,
    title              varchar   not null,
    annotation         varchar   not null,
    initiator_id       integer   not null
        constraint event_users_id_fk
            references users,
    confirmed_requests integer,
    category_id        integer   not null
        constraint event_category_id_fk
            references category,
    created_on         timestamp,
    description        varchar,
    event_date         timestamp not null,
    location_id        integer   not null
        constraint event_locations_id_fk
            references locations,
    paid               boolean   not null,
    participant_limit  integer,
    published_on       timestamp,
    request_moderation boolean,
    state_id           integer
        constraint event_state_id_fk
            references state
);