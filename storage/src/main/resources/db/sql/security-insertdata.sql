INSERT INTO users(username,password,enabled)
VALUES ('user','$2a$10$wmKzlyQjvkRlC4GwWbONtuEVMO3KZNo2Y0QlDxKbuvl2w1qkOgJvm', true);

INSERT INTO authorities (username, authority)
VALUES ('user', 'ROLE_SERVICE');

INSERT INTO users(username,password,enabled)
VALUES ('user1','$2a$10$wmKzlyQjvkRlC4GwWbONtuEVMO3KZNo2Y0QlDxKbuvl2w1qkOgJvm', true);

INSERT INTO authorities (username, authority)
VALUES ('user1', 'ROLE_SERVICE');

INSERT INTO users(username,password,enabled)
VALUES ('user2','$2a$10$wmKzlyQjvkRlC4GwWbONtuEVMO3KZNo2Y0QlDxKbuvl2w1qkOgJvm', true);

INSERT INTO authorities (username, authority)
VALUES ('user2', 'ROLE_SERVICE');