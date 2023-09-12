create schema `web_chess`;

use `web_chess`;

create table `users` (
	`id` int primary key auto_increment,
    `user_name` varchar(50) not null,
    `password` varchar(100) not null,
    `email` varchar(100) not null,
    `first_name` varchar(50) not null,
    `last_name` varchar(50) not null,
    `enabled` boolean not null
);

create table `roles` (
	`id` int primary key,
    `name` varchar(50) not null
);

create table `users_roles` (
	`id_user` int,
    `id_role` int,
    primary key (`id_user`, `id_role`),
    foreign key (`id_user`) references `users`(`id`),
    foreign key (`id_role`) references `roles`(`id`)
);

create table `user_statistics` (
	`id` int auto_increment,
    `games_won` int not null default 0,
    `gammes_losses` int not null default 0,
    `games_drawn` int not null default 0,
    `points` int not null default 1000,
    primary key (`id`),
    foreign key (`id`) references `users`(`id`)
);