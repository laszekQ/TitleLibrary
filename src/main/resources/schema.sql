CREATE TABLE TYPE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT
);

CREATE TABLE STATUS (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL
);

CREATE TABLE ROLE(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE "user" (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    login TEXT NOT NULL,
    password TEXT NOT NULL,
    role_id INT,
    CONSTRAINT fk_role FOREIGN KEY (role_id)
                    REFERENCES ROLE(id)
                    ON DELETE RESTRICT
);

CREATE TABLE TITLE (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    poster TEXT,
    description TEXT,
    year INT NOT NULL,
    type_id INT NOT NULL,
    CONSTRAINT fk_type FOREIGN KEY (type_id)
                   REFERENCES TYPE(id)
                   ON DELETE RESTRICT
);

CREATE TABLE NAME (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    title_id INT NOT NULL,
    CONSTRAINT fk_title FOREIGN KEY (title_id)
                  REFERENCES TITLE(id)
                  ON DELETE RESTRICT,
    ts TSVECTOR GENERATED ALWAYS AS (to_tsvector('simple', name)) STORED
);

CREATE INDEX name_ts_idx ON name USING GIN (ts);

CREATE TABLE TAG (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT
);

CREATE TABLE TAG_TITLE(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title_id INT,
    CONSTRAINT fk_title FOREIGN KEY (title_id)
                      REFERENCES TITLE(id)
                      ON DELETE RESTRICT,
    tag_id INT,
    CONSTRAINT fk_tag FOREIGN KEY (tag_id)
                      REFERENCES TAG(id)
                      ON DELETE RESTRICT
);

CREATE TABLE ENTRY(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title_id INT,
    CONSTRAINT fk_title FOREIGN KEY (title_id)
                  REFERENCES TITLE(id)
                  ON DELETE RESTRICT,
    user_id INT,
    CONSTRAINT fk_user FOREIGN KEY (user_id)
                  REFERENCES "user"(id)
                  ON DELETE RESTRICT,
    status_id INT,
    CONSTRAINT fk_status FOREIGN KEY (status_id)
                  REFERENCES STATUS(id)
                  ON DELETE RESTRICT,
    date DATE,
    rating INT
);

