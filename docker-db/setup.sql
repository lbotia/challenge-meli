USE bd_meli;

create table IP_INFO
(
    ip           varchar(20)  not null,
    country_name varchar(200) null,
    code_iso     char(3)      null,
    local_money  char(5)          null,
    trm          double       null,
    trm_date     timestamp default CURRENT_TIMESTAMP not null,
    constraint IP_INFO_pk
        primary key (ip)
);

create table BLACK_LIST
(
    ip       varchar(20) not null,
    ban_date timestamp   null,
    constraint BLACK_LIST_pk
        primary key (ip)
);