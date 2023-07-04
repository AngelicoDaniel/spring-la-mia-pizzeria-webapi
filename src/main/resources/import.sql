INSERT INTO pizzas (name, description, image, price) VALUES ('Margherita', 'Classic pizza with tomato sauce, mozzarella, and basil', 'margherita.jpg', 3.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Diavola', 'Spicy pizza with tomato sauce, mozzarella, and spicy salami', 'diavola.jpg', 10.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Vegetariana', 'Pizza loaded with assorted vegetables and cheese', 'vegetariana.jpg', 10.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Quattro Formaggi', 'Pizza topped with four different types of cheese', 'quattro_formaggi.jpg', 12.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Pizza al Pistacchio', 'Pizza with creamy pistachio sauce and mozzarella', 'pistacchio.jpg', 11.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Capricciosa', 'Pizza with tomato sauce, mozzarella, ham, mushrooms, artichokes, olives, and oregano', 'capricciosa.jpg', 12.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Bufalina', 'Pizza with tomato sauce, buffalo mozzarella, and fresh basil', 'bufalina.jpg', 11.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Prosciutto e Funghi', 'Pizza with tomato sauce, mozzarella, ham, and mushrooms', 'prosciutto_funghi.jpg', 10.99);

INSERT INTO pizzas (name, description, image, price) VALUES ('Pizza Melanzana', 'Pizza with yellow tomato sauce, donkey milk mozzarella, smoked herring, silphium and cumin', 'pizza_melanzana.jpg', 869.51);
INSERT INTO `special_offers` (`end_date`, `id`, `pizza_id`, `start_date`, `title`) VALUES ('2023-06-20', '1', '2', '2023-06-05', 'offerta speciale1');
INSERT INTO `special_offers` (`end_date`, `id`, `pizza_id`, `start_date`, `title`) VALUES ('2023-05-15', '2', '3', '2023-09-10', 'offerta speciale2');
INSERT INTO `special_offers` (`end_date`, `id`, `pizza_id`, `start_date`, `title`) VALUES ('2024-04-10', '5', '4', '2024-08-08', 'offerta speciale3');
INSERT INTO ingredients (name) VALUES ('pomodoro');
INSERT INTO ingredients (name) VALUES ('mozzarella');
INSERT INTO ingredients (name) VALUES ('basilico');

INSERT INTO `pizza_ingredients` (`ingredient_id`, `pizza_id`) VALUES ('1', '1');
INSERT INTO `pizza_ingredients` (`ingredient_id`, `pizza_id`) VALUES ('2', '2');
INSERT INTO `pizza_ingredients` (`ingredient_id`, `pizza_id`) VALUES ('1', '3');

INSERT INTO roles (id, name) VALUES(1,'ADMIN');
INSERT INTO roles (id, name) VALUES(2,'USER');
INSERT INTO users (id, email, first_name, last_name, password) VALUES(1, 'john@email.com', 'John', 'Doe', '{noop}john');
INSERT INTO users (id, email, first_name, last_name, password) VALUES(2, 'jane@email.com', 'Jane', 'Doe', '{noop}jane');
INSERT INTO users_roles (roles_id, user_id) VALUES(1, 1);
INSERT INTO users_roles (roles_id, user_id) VALUES(2, 2);

