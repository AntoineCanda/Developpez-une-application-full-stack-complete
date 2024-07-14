CREATE DATABASE mdd;

USE mdd;

CREATE TABLE
    `subjects` (
        `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
        `name` VARCHAR(255),
        `description` TEXT (65535)
    );

CREATE TABLE
    `users` (
        `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
        `username` VARCHAR(255),
        `email` VARCHAR(255),
        `password` VARCHAR(255),
        `role` VARCHAR(255),
        `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE TABLE
    `subscriptions` (
        `user_id` BIGINT,
        `subject_id` BIGINT,
        PRIMARY KEY (`user_id`, `subject_id`),
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
        FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`)
    );

CREATE TABLE
    `posts` (
        `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
        `title` VARCHAR(255),
        `content` TEXT (65535),
        `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `subject_id` BIGINT,
        `user_id` BIGINT,
        FOREIGN KEY (`subject_id`) REFERENCES `subjects` (`id`),
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    );

CREATE TABLE
    `comments` (
        `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
        `content` TEXT (65535),
        `created_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        `post_id` BIGINT,
        `user_id` BIGINT,
        FOREIGN KEY (`post_id`) REFERENCES `posts` (`id`),
        FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    );