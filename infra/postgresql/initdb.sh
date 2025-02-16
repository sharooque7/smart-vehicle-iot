#!/bin/bash -e
echo "Creating dblink extension"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE SCHEMA dblink;
    CREATE EXTENSION dblink SCHEMA dblink;
EOSQL

echo "Creating jobmon extension"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE SCHEMA jobmon;
    CREATE EXTENSION pg_jobmon SCHEMA jobmon;
    INSERT INTO jobmon.dblink_mapping_jobmon (username, pwd) VALUES ('$POSTGRES_USER', '$POSTGRES_PASSWORD');
EOSQL

echo "Creating partman extension"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE SCHEMA partman;
    CREATE EXTENSION pg_partman SCHEMA partman;
EOSQL

echo "Adding jobmon permissions"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    GRANT USAGE ON SCHEMA jobmon TO $POSTGRES_USER;
    GRANT USAGE ON SCHEMA dblink TO $POSTGRES_USER;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA jobmon TO $POSTGRES_USER;
    GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA jobmon TO $POSTGRES_USER;
    GRANT ALL ON ALL SEQUENCES IN SCHEMA jobmon TO $POSTGRES_USER;
EOSQL

echo "ADDING pg_partman_bgw TO postgresql.conf"
echo "shared_preload_libraries = 'pg_partman_bgw'" >> $PGDATA/postgresql.conf
echo "pg_partman_bgw.interval = 3600" >> $PGDATA/postgresql.conf
echo "pg_partman_bgw.role = '$POSTGRES_USER'" >> $PGDATA/postgresql.conf
echo "pg_partman_bgw.dbname = '$POSTGRES_DB'" >> $PGDATA/postgresql.conf

echo "ADDING autovacuum config TO postgresql.conf"
echo "autovacuum = on" >> $PGDATA/postgresql.conf
echo "autovacuum_vacuum_scale_factor = 0.1" >> $PGDATA/postgresql.conf
echo "autovacuum_analyze_scale_factor = 0.1" >> $PGDATA/postgresql.conf
echo "autovacuum_vacuum_cost_limit = 1000" >> $PGDATA/postgresql.conf

# echo "Adding cron to postgresql.conf"
# echo "shared_preload_libraries = 'pg_cron'" >> $PGDATA/postgresql.conf
# # echo "shared_preload_libraries = 'pg_partman_bgw, pg_cron'" >> $PGDATA/postgresql.conf
# echo "cron.database_name = 'vms-primary'" >> $PGDATA/postgresql.conf
# psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
#     CREATE EXTENSION pg_cron;
# EOSQL

echo "Creating pgAgent extension"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE SCHEMA pgagent;
    CREATE EXTENSION pgagent SCHEMA pgagent;
EOSQL

echo "Adding pgagent permissions"
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    GRANT USAGE ON SCHEMA pgagent TO $POSTGRES_USER;
    GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA pgagent TO $POSTGRES_USER;
    GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA pgagent TO $POSTGRES_USER;
    GRANT ALL ON ALL SEQUENCES IN SCHEMA pgagent TO $POSTGRES_USER;
EOSQL

# echo "Setting up pgAgent"
pgagent hostaddr=127.0.0.1 dbname=$POSTGRES_DB user=$POSTGRES_USER -s pgagent_log.log

