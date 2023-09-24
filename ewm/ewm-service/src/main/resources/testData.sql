INSERT INTO users (name, email) VALUES ('USER1', 'email1@mail.ru');
INSERT INTO users (name, email) VALUES ('USER2', 'email2@mail.ru');
INSERT INTO users (name, email) VALUES ('USER3', 'email3@mail.ru');
INSERT INTO users (name, email) VALUES ('USER4', 'email4@mail.ru');
INSERT INTO users (name, email) VALUES ('USER5', 'email5@mail.ru');
INSERT INTO users (name, email) VALUES ('USER6', 'email6@mail.ru');

INSERT INTO category (name) VALUES ('category1');
INSERT INTO category (name) VALUES ('category2');
INSERT INTO category (name) VALUES ('category3');

INSERT INTO locations (lat, lon) VALUES (10.10, 20.20);
INSERT INTO locations (lat, lon) VALUES (20.20, 30.30);
INSERT INTO locations (lat, lon) VALUES (30.30, 40.40);

INSERT INTO event (title, annotation, initiator_id, category_id, created_on, description, event_date, location_id, paid, participant_limit, published_on, request_moderation, state)
VALUES ('title1', 'annotation of event 1', 1, 1, '2001-01-01 10:00', 'description of event1', '2001-10-01 10:00', 1, false, 100, null, true, 'PENDING');
INSERT INTO event (title, annotation, initiator_id, category_id, created_on, description, event_date, location_id, paid, participant_limit, published_on, request_moderation, state)
VALUES ('title2', 'annotation of event 2', 1, 1, '2001-01-02 10:00', 'description of event2', '2001-10-02 10:00', 1, false, 50, '2001-01-02 11:00', true, 'PUBLISHED');
INSERT INTO event (title, annotation, initiator_id, category_id, created_on, description, event_date, location_id, paid, participant_limit, published_on, request_moderation, state)
VALUES ('title3', 'annotation of event 3', 3, 2, '2001-01-03 10:00', 'description of event3', '2001-10-03 10:00', 2, false, 0, '2001-01-03 11:00', true, 'PUBLISHED');
INSERT INTO event (title, annotation, initiator_id, category_id, created_on, description, event_date, location_id, paid, participant_limit, published_on, request_moderation, state)
VALUES ('title4', 'annotation of event 4', 4, 2, '2001-01-04 10:00', 'description of event4', '2024-10-04 10:00', 2, true, 10, '2001-01-04 11:00', false, 'PUBLISHED');
INSERT INTO event (title, annotation, initiator_id, category_id, created_on, description, event_date, location_id, paid, participant_limit, published_on, request_moderation, state)
VALUES ('title5', 'annotation of event 5', 5, 3, '2001-01-05 10:00', 'description of event5', '2001-10-05 10:00', 3, true, 100, null, true, 'PENDING');

INSERT INTO compilation (title, pinned) VALUES ('Compilation title 1', true);
INSERT INTO compilation (title, pinned) VALUES ('Compilation title 2', false);

INSERT INTO compilation_events (compilation_id, event_id) VALUES (1, 2);

INSERT INTO participation_requests (event_id, requester, created, status) VALUES (2, 2, '2001-01-04 10:00', 'PENDING');
INSERT INTO participation_requests (event_id, requester, created, status) VALUES (2, 3, '2001-01-04 10:00', 'CONFIRMED');
INSERT INTO participation_requests (event_id, requester, created, status) VALUES (2, 6, '2001-01-04 10:00', 'CONFIRMED');

INSERT INTO marks (user_id, event_id, mark) VALUES (3, 2, 5);