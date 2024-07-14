-- password: test!1234
INSERT INTO
    users (
        username,
        email,
        password,
        role,
        created_at,
        updated_at
    )
VALUES
    (
        'Admin',
        'admin@mdd.com',
        '$2a$10$OrLIi.J1M2Y19h.QfIp3.e3BW7LxRuunsNHFQm.gnp60hwo.5fCci',
        'admin',
        null,
        null
    ),
    (
        'John Doe',
        'john.doe@example.com',
        '$2a$10$OrLIi.J1M2Y19h.QfIp3.e3BW7LxRuunsNHFQm.gnp60hwo.5fCci',
        'user',
        null,
        null
    );

INSERT INTO
    subjects (name, description)
VALUES
    (
        'Java',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.'
    ),
    (
        'Angular',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.'
    ),
    (
        'Javascript',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.'
    ),
    (
        'React',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.'
    ),
    (
        'Software development',
        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit.'
    );

INSERT INTO
    `posts` (`title`, `content`, `user_id`, `subject_id`)
VALUES
    (
        'JavaScript pour les débutants',
        'JavaScript est un langage de programmation incontournable pour le développement web...',
        2,
        3
    ),
    (
        'React: un des frameworks JavaScript les plus populaires pour le développement web',
        'Découvez React, un des frameworks JavaScript les plus populaires pour le développement web crées et maintenu par Facebook...',
        2,
        4
    ),
    (
        'Angular: un des frameworks JavaScript les plus populaires pour le développement web',
        'Découvez Angular, un des frameworks JavaScript les plus populaires pour le développement web crées et maintenu par Google...',
        2,
        2
    ),
    (
        'Java pour les débutants',
        'Java est un langage de programmation orienté object largement utilisé dans le développement logiciel...',
        1,
        1
    ),
    (
        'Les principes de base de la programmation orientée objet',
        'Les principes de l''orienté objet sont l''encapsulation, l''héritage et le polymorphisme...',
        1,
        5
    );

INSERT INTO
    subscriptions (user_id, subject_id)
VALUES
    (1, 1),
    (1, 5),
    (1, 2),
    (2, 1),
    (2, 2),
    (2, 4),
    (2, 5);

INSERT INTO
    `comments` (content, user_id, post_id)
VALUES
    ('Très bon article !', 1, 1),
    ('Merci pour cet article !', 2, 4),
    ('J''ai appris beaucoup de choses !', 2, 5);