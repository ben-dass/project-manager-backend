create schema if not exists securecapita;

set names 'UTF8MB4';
set time_zone = 'America/Denver';

use securecapita;


-- USERS
drop table if exists users;
create table users
(
    id         bigint unsigned not null auto_increment primary key,
    first_name varchar(50)     not null,
    last_name  varchar(50)     not null,
    email      varchar(100) default null,
    password   varchar(255) default null,
    address    varchar(255) default null,
    phone      varchar(30)  default null,
    title      varchar(50)  default null,
    bio        varchar(255) default null,
    image_url  varchar(255) default 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    create_ts  datetime     default current_timestamp,
    enabled    boolean      default false,
    non_locked boolean      default true,
    using_mfa  boolean      default false,
    constraint uq_users_email unique ( email )
);


-- ROLES
drop table if exists roles;
create table roles
(
    id         bigint unsigned not null auto_increment primary key,
    name       varchar(50)     not null,
    permission varchar(255)    not null,
    constraint uq_roles_name unique ( name )
);

drop table if exists user_roles;
create table user_roles
(
    id      bigint unsigned not null auto_increment primary key,
    user_id bigint unsigned not null,
    role_id bigint unsigned not null,
    foreign key ( user_id ) references users ( id ) on delete cascade on update cascade,
    foreign key ( role_id ) references roles ( id ) on delete restrict on update cascade,
    constraint uq_user_roles_user_id unique ( user_id )
);


-- EVENTS
drop table if exists events;
create table events
(
    id          bigint unsigned not null auto_increment primary key,
    type        varchar(50)     not null check (
        type in (
                  'LOGIN_ATTEMPT',
                  'LOGIN_ATTEMPT_FAILURE',
                  'LOGIN_ATTEMPT_SUCCESS',
                  'PROFILE_UPDATE',
                  'PROFILE_PICTURE_UPDATE',
                  'ROLE_UPDATE',
                  'ACCOUNT_SETTINGS_UPDATE',
                  'PASSWORD_UPDATE',
                  'MFA UPDATE'
            )
        ),
    description varchar(255)    not null,
    constraint uq_type_name unique ( type )
);

drop table if exists user_events;
create table user_events
(
    id         bigint unsigned not null auto_increment primary key,
    user_id    bigint unsigned not null,
    event_id   bigint unsigned not null,
    device     varchar(100) default null,
    ip_address varchar(100) default null,
    create_ts  datetime     default current_timestamp,
    foreign key ( user_id ) references users ( id ) on delete cascade on update cascade,
    foreign key ( event_id ) references events ( id ) on delete restrict on update cascade
);


-- VERIFICATION
drop table if exists account_verifications;
create table account_verifications
(
    id      bigint unsigned not null auto_increment primary key,
    user_id bigint unsigned not null,
    url     varchar(255)    not null,
    --    date       datetime        not null,
    foreign key ( user_id ) references users ( id ) on delete cascade on update cascade,
    constraint uq_account_verifications_user_id unique ( user_id ),
    constraint uq_account_verifications_url unique ( url )
);


-- RESET PASSWORD
drop table if exists reset_account_verifications;
create table reset_account_verifications
(
    id              bigint unsigned not null auto_increment primary key,
    user_id         bigint unsigned not null,
    url             varchar(255)    not null,
    expiration_date datetime        not null,
    foreign key ( user_id ) references users ( id ) on delete cascade on update cascade,
    constraint uq_reset_account_verifications_user_id unique ( user_id ),
    constraint uq_reset_account_verifications_url unique ( url )
);


-- TWO FACTOR VERIFICATION
drop table if exists two_factor_verification;
create table two_factor_verification
(
    id              bigint unsigned not null auto_increment primary key,
    user_id         bigint unsigned not null,
    code            varchar(10)     not null,
    expiration_date datetime        not null,
    foreign key ( user_id ) references users ( id ) on delete cascade on update cascade,
    constraint uq_two_factor_verification_user_id unique ( user_id ),
    constraint uq_two_factor_verification_url unique ( code )
);

