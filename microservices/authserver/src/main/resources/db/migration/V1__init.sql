
    create table ACME_USER (
        id int8 not null,
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
        primary key (id)
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
        references AUTHORITY (name);

    alter table USER_AUTHORITY
        add constraint FK_USER_AUTHORITY_ACME_USER
        foreign key (user_id)
        references ACME_USER (id);

    create table hibernate_sequences (
         sequence_name varchar(255),
         sequence_next_hi_value int4
    );

INSERT INTO AUTHORITY (name) VALUES ('ROLE_USER');
INSERT INTO AUTHORITY (name) VALUES ('ROLE_ADMIN');

INSERT INTO ACME_USER (id, activated, activation_key, created_by, created_date, email, first_name, last_modified_by, last_modified_date, last_name, password) VALUES
    (1, b'1', NULL, 'system', '2015-11-26 10:33:14', 'ttester@test.de', 'Udo', NULL, '2015-11-26 10:33:14', 'Tester', '$2a$04$E7J2pUf9rMKV5Sf6nT4wPOWynxT3vEb9Sy8RpmAS1gt9IqfZJSlFy');

