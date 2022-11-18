create table account
(
    id            bigint auto_increment
        primary key,
    create_by     varchar(255)      null,
    create_date   datetime(6)       null,
    modified_by   varchar(255)      null,
    modified_date datetime(6)       null,
    address       varchar(100)      null,
    dob           date              null,
    email         varchar(255)      null,
    fullname      varchar(100)      null,
    gender        tinyint default 1 null,
    password      varchar(130)      not null,
    phone_number  varchar(12)       null,
    status        tinyint default 1 null,
    username      varchar(20)       not null,
    constraint UK_gex1lmaqpg0ir5g1f5eftyaa1
        unique (username)
);

create table category
(
    id            bigint auto_increment
        primary key,
    create_by     varchar(255)                null,
    create_date   datetime(6)                 null,
    modified_by   varchar(255)                null,
    modified_date datetime(6)                 null,
    code          varchar(20)                 not null,
    name          varchar(30) charset utf8mb3 null,
    constraint UK_acatplu22q5d1andql2jbvjy7
        unique (code)
);

create table `order`
(
    id            bigint auto_increment
        primary key,
    address       varchar(255) null,
    phone_number  varchar(12)  null,
    create_by     varchar(45)  null,
    create_date   datetime     null,
    modified_by   varchar(45)  null,
    modified_date datetime     null
);

create table product
(
    id            bigint auto_increment
        primary key,
    create_by     varchar(255)      null,
    create_date   datetime(6)       null,
    modified_by   varchar(255)      null,
    modified_date datetime(6)       null,
    description   varchar(50)       null,
    available     tinyint default 1 not null,
    name          varchar(100)      null,
    price         decimal(20)       not null,
    quantity      int               not null,
    image         varchar(45)       null,
    category_id   bigint            not null,
    constraint FK1mtsbur82frn64de7balymq9s
        foreign key (category_id) references category (id)
);

create table order_detail
(
    id         bigint auto_increment
        primary key,
    quantity   int     not null,
    price      decimal null,
    order_id   bigint  not null,
    product_id bigint  not null,
    constraint order_oder_detail
        foreign key (order_id) references `order` (id)
            on update cascade,
    constraint product_order_detail
        foreign key (product_id) references product (id)
            on update cascade
);

create index order_oder_detail_idx
    on order_detail (order_id);

create index product_order_detail_idx
    on order_detail (product_id);

create table role
(
    id            bigint auto_increment
        primary key,
    create_by     varchar(255) null,
    create_date   datetime(6)  null,
    modified_by   varchar(255) null,
    modified_date datetime(6)  null,
    name          varchar(20)  null
);

create table account_role
(
    account_id bigint not null,
    role_id    bigint not null,
    primary key (account_id, role_id),
    constraint FK1f8y4iy71kb1arff79s71j0dh
        foreign key (account_id) references account (id),
    constraint FKrs2s3m3039h0xt8d5yhwbuyam
        foreign key (role_id) references role (id)
);


