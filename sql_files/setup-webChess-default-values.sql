insert into `users` values 
(1, 'john', '$2y$10$lDRNsfpMFUDHfs9lV7U4MuMhlLZO7MybPh5lCoUYqdMR1zl7KQO.C', 'john@gmail.com', 'John', 'Smith', true), 
(2, 'mary', '$2y$10$ZoEUsxp/o09HRbBm2IuPoOzHj1popYulktpcxBiVNCnwSOqIcMQX2', 'mary@gmail.com', 'Mary', 'Brown', true), 
(3, 'susan', '$2y$10$sfIQFWm9H4arYgjnZ2f59ubhMyhvobFm3viYN6aLoVq1/AzZGRsrq', 'susan@gmail.com', 'Susan', 'Williams', true);

insert into `roles` values
	(1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');
    
insert into `users_roles` values 
	(1, 1),
    (2, 1),
    (3, 1),
    (3, 2);
    
insert into `user_statistics`() values
	(), (), ();