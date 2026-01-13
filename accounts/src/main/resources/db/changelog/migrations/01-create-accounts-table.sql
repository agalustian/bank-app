create table if not exists accounts
(
    id          serial primary key,
    login       varchar(50) not null,
    fullname    text not null,
    birthdate   timestamp  not null,
    amount      integer  not null
);