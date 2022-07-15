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

INSERT INTO language (language)
VALUES ("ENGLISH");

INSERT INTO creator (type, name)
VALUES ("COMPOSER", "testCreator");

INSERT INTO publisher (name)
VALUES ("Digital Publishing Inc");

INSERT INTO book_pdf_information (dpi_pdf_url, pdf_coupon, third_party_pdf_store_url)
VALUES ("testPdfUrl", "testPdfCoupon", "testThirdPartyStoreUrl");

INSERT INTO theme (primary_color, secondary_color)
VALUES ("tetPrimaryColor", "testSecondaryColor");

INSERT INTO books (book_data_url, cover_url, description, difficulty, external_book_id, sample_video_url, title, pdf_info_id, publisher_id, theme_id_map, price, type)
VALUES ("https://dpi-authoring-image.s3.us-west-1.amazonaws.com/138/bookData", "testCoverUrl", "testDescription", "ADVANCED", 138, "testSampleVideoUrl", "testTitle", 1, 1, 1, 5.99, "GUITAR");

INSERT INTO book_language (book_id, language_id)
VALUES (1, 1);

INSERT INTO book_creators (book_id, creator_id)
VALUES (1, 1);

INSERT INTO user_library (user_id, book_id)
VALUES (1, 1);