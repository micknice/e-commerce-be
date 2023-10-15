-- Passwords in format of Password<UserLetter>123. Unless specified otherwise.
--encrypted using https://www.javainuse.com/onlineBcrypt 10 rounds
INSERT INTO local_user (email, first_name, last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$N/.83RDW8VKr05rxOs7mi.n3gylmQdMgLj0dQakQtqV/inEc7DoSC', 'UserA', true )
    , ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$4JhRHMUzFWvjfvndShq5u.U3KKKbM9Gtg9QZqr/dIvGSbhjTt8B3a', 'UserB', false);