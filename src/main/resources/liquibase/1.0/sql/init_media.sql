create sequence if not exists seq_media start with 1;

create table if not exists media
(
    id           bigint                              not null
        constraint pk_media primary key,
    date_created timestamp default current_timestamp not null,
    date_updated timestamp                           not null,
    review_id    bigint                              not null
        constraint fk_review_id references review (id),
    url          varchar(255),
    deleted      boolean   default false
);

create index if not exists indx_media_review_id_deleted
    on media (review_id, deleted);