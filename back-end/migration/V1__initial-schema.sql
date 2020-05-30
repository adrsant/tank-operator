CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE queue_aws
(
    id         uuid                                       NOT NULL,
    arn        text                                       NOT NULL,
    url        text                                       NOT NULL,
    queue_type text CHECK (char_length(queue_type) <= 10) NOT NULL,
    name       text CHECK (char_length(name) <= 200)      NOT NULL,
    enabled    boolean                                    NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE                   NOT NULL DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE
);

ALTER TABLE ONLY queue_aws
    ADD CONSTRAINT queue_pk PRIMARY KEY (id);

CREATE INDEX IF NOT EXISTS "queue_name_ix" ON queue_aws USING btree (name, enabled);
CREATE UNIQUE INDEX queue_aws_uq ON queue_aws (arn);
