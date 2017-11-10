CREATE TABLE task (
  id               UUID        NOT NULL,
  dtype            VARCHAR(31) NOT NULL,
  start_timestamp  TIMESTAMP   NOT NULL,
  status_timestamp TIMESTAMP   NOT NULL,
  task_status      VARCHAR(255),
  account          VARCHAR(255),
  service_type     VARCHAR(255),
  command_counter  INTEGER,
  first_command_id UUID,
  version          BIGINT,
  PRIMARY KEY (id)
);
CREATE TABLE command (
  id               UUID        NOT NULL,
  task_id          UUID,
  dtype            VARCHAR(31) NOT NULL,
  start_timestamp  TIMESTAMP   NOT NULL,
  status_timestamp TIMESTAMP   NOT NULL,
  command_status   VARCHAR(255),
  body             VARCHAR(255),
  address          VARCHAR(255),
  next_command_id  UUID,
  version          BIGINT,
  PRIMARY KEY (id)
);
