drop table if exists event, users, category, event, locations, compilation, participation_requests;

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
    title              varchar(255)          not null
        constraint check_title
            check (length((title)::text) >= 3),
    annotation         varchar(255)          not null
        constraint check_annotation
            check (length((annotation)::text) >= 20),
    initiator_id       integer               not null
        constraint event_users_id_fk
            references public.users,
    confirmed_requests integer default 0,
    category_id        integer               not null
        constraint event_category_id_fk
            references public.category,
    created_on         timestamp,
    description        varchar(255)
        constraint check_description
            check (length((description)::text) >= 20),
    event_date         timestamp             not null,
    location_id        integer               not null
        constraint event_locations_id_fk
            references public.locations,
    paid               boolean default false not null,
    participant_limit  integer default 0,
    published_on       timestamp,
    request_moderation boolean default true,
    state              varchar(255)          not null,
    views              integer default 0,
    constraint check_limit
        check (participant_limit >= confirmed_requests)
);

create table if not exists compilation
(
    id       integer generated always as identity
        primary key,
    title    varchar(255) not null,
    pinned   boolean      not null,
    event_id integer
        constraint compilation_event_id_fk
            references public.event
);

create table if not exists participation_requests
(
    id        integer generated always as identity
        primary key,
    event_id  integer                  not null
        constraint participation_requests_event_id_fk
            references public.event,
    requester integer                  not null
        constraint participation_requests_users_id_fk
            references public.users,
    created   timestamp with time zone not null,
    status    varchar(10)              not null
);