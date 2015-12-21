
    create table ACME_USER (
        user_id int8 not null,
        activated boolean not null,
        activation_key varchar(20),
        created_by varchar(50) not null,
        created_date timestamp not null,
        email varchar(100),
        first_name varchar(50),
        last_modified_by varchar(50),
        last_modified_date timestamp,
        last_name varchar(50),
        password varchar(100),
        primary key (user_id)
    );

    create table AUTHORITY (
        name varchar(50) not null,
        primary key (name)
    );

    create table USER_AUTHORITY (
        user_id int8 not null,
        name varchar(50) not null,
        primary key (user_id, name)
    );

    alter table ACME_USER
        add constraint UK1_ACME_USER  unique (email);

    alter table USER_AUTHORITY
        add constraint FK_USER_AUTHORITY_AUTHORITY
        foreign key (name)
        references AUTHORITY;

    alter table USER_AUTHORITY
        add constraint FK_USER_AUTHORITY_ACME_USER
        foreign key (user_id)
        references ACME_USER;

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value int4
    );

INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');
