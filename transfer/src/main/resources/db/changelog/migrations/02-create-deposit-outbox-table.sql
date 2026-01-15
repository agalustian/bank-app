create table if not exists deposit_outbox
(
    id            serial primary key,
    from_account  text not null,
    to_account    text not null,
    amount        integer not null
);