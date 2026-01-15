create table if not exists notifications_outbox
(
    id          serial primary key,
    username    text not null,
    text        text not null
);