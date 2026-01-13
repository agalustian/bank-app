create table if not exists accounts
(
    id          UUID primary key,
    login       varchar(50) not null,
    fullname    text not null,
    birthdate   date not null,
    amount      integer not null
);