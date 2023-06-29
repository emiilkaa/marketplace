create sequence if not exists seq_review start with 1;

create table if not exists review
(
    id           bigint                              not null
        constraint pk_review primary key,
    date_created timestamp default current_timestamp not null,
    date_updated timestamp                           not null,
    product_id   bigint                              not null
        constraint fk_product_id references product (id),
    post_message text,
    rating       int                                 not null
);

create index if not exists indx_review_product_id
    on review (product_id);