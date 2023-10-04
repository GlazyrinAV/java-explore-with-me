drop table if exists event, users, category, event, locations, compilation,
    compilation_events, participation_requests, marks, user_roles;

create table if not exists user_roles
(
    role_name varchar not null
        constraint "User_roles_pk2"
            primary key
);

insert into user_roles (role_name) values ('admin');
insert into user_roles (role_name) values ('user');

create table if not exists users
(
    id    integer generated always as identity
        primary key,
    name  varchar(250) not null
        constraint check_name
            check (length((name)::text) > 1),
    email varchar(254) not null
        unique
        constraint check_email
            check (length((email)::text) > 5),
    role  varchar(255) default 'user'::character varying
        constraint users_user_roles_role_name_fk
            references user_roles,
    password varchar(255)
);

create table if not exists category
(
    id   integer generated always as identity
        primary key,
    name varchar(50) not null unique
        constraint check_name
            check (length((name)::text) > 0)
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
    title              varchar(120)          not null
        constraint check_title
            check (length((title)::text) >= 3),
    annotation         varchar(2000)         not null
        constraint check_annotation
            check (length((annotation)::text) >= 20),
    initiator_id       integer               not null
        constraint event_users_id_fk
            references users,
    category_id        integer               not null
        constraint event_category_id_fk
            references category,
    created_on         timestamp,
    description        varchar(7000)
        constraint check_description
            check (length((description)::text) >= 20),
    event_date         timestamp             not null,
    location_id        integer               not null
        constraint event_locations_id_fk
            references locations,
    paid               boolean default false not null,
    participant_limit  integer default 0,
    published_on       timestamp,
    request_moderation boolean default true,
    state              varchar(255)          not null,
    views              integer default 0
);

create table if not exists compilation
(
    id     integer generated always as identity
        primary key,
    title  varchar(50) not null
        constraint check_title
            check (length((title)::text) > 0),
    pinned boolean     not null
);

create table if not exists participation_requests
(
    id        integer generated always as identity
        primary key,
    event_id  integer                  not null
        constraint participation_requests_event_id_fk
            references event,
    requester integer                  not null
        constraint participation_requests_users_id_fk
            references users,
    created   timestamp with time zone not null,
    status    varchar(20)              not null
);

create table if not exists compilation_events
(
    compilation_id integer not null
        constraint compilation_events_compilation_id_fk
            references compilation,
    event_id       integer not null
        constraint compilation_events_event_id_fk
            references event,
    constraint compilation_events_pk
        primary key (event_id, compilation_id)
);

create table if not exists marks
(
    user_id  integer not null
        constraint marks_users_id_fk
            references users,
    event_id integer not null
        constraint marks_event_id_fk
            references event,
    mark     integer not null
        constraint check_mark
            check ((mark > 0) AND (mark < 6)),
    constraint marks_pk
        primary key (user_id, event_id)
);

insert into users (name, email, role, password) VALUES ('admin','admin@admin.ru', 'admin',
                                                        '$2y$10$df2iys6d9EKkj6UneWI34egpQdvKYI8G77OvZE6FeKC07fYsG/pmC');