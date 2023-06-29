create sequence if not exists seq_product start with 1;

create table if not exists product
(
    id                 bigint                              not null
        constraint pk_product primary key,
    date_created       timestamp default current_timestamp not null,
    date_updated       timestamp                           not null,
    available_quantity integer                             not null,
    price              numeric                             not null,
    product_name       varchar(255)                        not null,
    description        text
);