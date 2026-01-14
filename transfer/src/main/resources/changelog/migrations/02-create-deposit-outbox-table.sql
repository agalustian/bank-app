create table if not exists notifications_outbox
(
    id            serial primary key,
    from          text not null,
    to            text not null
    amount        integer not null
);