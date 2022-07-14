INSERT INTO roles (name)
VALUES ("ROLE_USER");

INSERT INTO roles (name)
VALUES ("ROLE_EDITOR");

INSERT INTO users (email, enabled, login_method, password)
VALUES ("testemail@test.com", 1, "LOCAL", "$2a$12$PLZ119P9NdhMQJ/yXQB4S.oZSIdAGEASQF9OiQSHPb7hJ33pMxnU6");

INSERT INTO users (email, enabled, login_method, password, verification_code)
VALUES ("testemail10@test.com", 0, "LOCAL", "$2a$12$PLZ119P9NdhMQJ/yXQB4S.oZSIdAGEASQF9OiQSHPb7hJ33pMxnU6", 123456);

INSERT INTO users (email, enabled, login_method, password, verification_code)
VALUES ("testemail20@test.com", 1, "LOCAL", "$2a$12$PLZ119P9NdhMQJ/yXQB4S.oZSIdAGEASQF9OiQSHPb7hJ33pMxnU6", 444444);

INSERT INTO refresh_tokens (expiration_date, token, user_id)
VALUES (DATE_ADD(NOW(), INTERVAL 1 HOUR), "testToken", 1);

INSERT INTO refresh_tokens (expiration_date, token, user_id)
VALUES (NOW(), "expiredToken", 1);