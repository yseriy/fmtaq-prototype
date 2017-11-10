INSERT INTO task (account, service_type, start_timestamp, status_timestamp, task_status, version, dtype, id)
VALUES ('account', 'service_type', '2017-11-08T14:21:04.330', '2017-11-08T14:21:04.330', 'REGISTERED', 0, 'SingleTask',
        '92354e79-560c-4bbb-8786-9087b82782e3');
INSERT INTO task (account, service_type, start_timestamp, status_timestamp, task_status, version, dtype, id)
VALUES ('account', 'service_type', '2017-11-08T14:22:04.330', '2017-11-08T14:22:04.330', 'REGISTERED', 0, 'SingleTask',
        '05e81414-bcb9-47de-a55f-e41ae421006f');
INSERT INTO command (address, body, command_status, start_timestamp, status_timestamp, task_id, version, dtype, id)
VALUES ('address', 'body', 'REGISTERED', '2017-11-08T14:21:04.330', '2017-11-08T14:21:04.330',
        '92354e79-560c-4bbb-8786-9087b82782e3', 0, 'SingleCommand', 'dc70c77b-0360-4482-bf40-545343effcac');
INSERT INTO command (address, body, command_status, start_timestamp, status_timestamp, task_id, version, dtype, id)
VALUES ('address1', 'body1', 'REGISTERED', '2017-11-08T14:22:04.330', '2017-11-08T14:22:04.330',
        '05e81414-bcb9-47de-a55f-e41ae421006f', 0, 'SingleCommand', '61cea09a-a79d-4d58-80e3-db5e1ca14d4b');