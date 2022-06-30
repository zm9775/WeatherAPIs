DROP TABLE IF EXISTS weathers;
CREATE TABLE weathers
(
    id                 INT AUTO_INCREMENT PRIMARY KEY,
    city_name          VARCHAR(50) NOT NULL,
    local_time         DATETIME    NOT NULL,
    last_updated       DATETIME    NOT NULL,
    temp_c             DOUBLE       NOT NULL,
    temp_f             DOUBLE       NOT NULL
);