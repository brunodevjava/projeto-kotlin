alter table usuario
    alter column senha type varchar(255) using senha::varchar(255);

