FROM postgres:15

LABEL maintainer="Petrus"

# Install dependencies
RUN apt-get update && \
    apt-get install -y git make gcc postgresql-server-dev-15

# Install pg_cron
RUN apt-get update && \
    apt-get install -y postgresql-15-cron

# Install pg_jobmon
RUN set -ex \
    && git clone https://github.com/omniti-labs/pg_jobmon.git /usr/src/pg_jobmon \
    && cd /usr/src/pg_jobmon \
    && make \
    && make install \
    && cd / \
    && rm -rf /usr/src/pg_jobmon

# Install pg_partman
RUN set -ex \
    && git clone https://github.com/pgpartman/pg_partman.git /usr/src/pg_partman \
    && cd /usr/src/pg_partman \
    && make \
    && make install \
    && cd / \
    && rm -rf /usr/src/pg_partman

RUN set -ex \
    && git clone https://github.com/citusdata/pg_cron.git /usr/src/pg_cron \
    && cd /usr/src/pg_cron \
    && make \
    && make install \
    && cd / \
    && rm -rf /usr/src/pg_cron


# Install pgAgent from apt
RUN apt-get update && \
    apt-get install -y pgagent

# Clean up unnecessary packages
RUN apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Copy initialization scripts
# COPY initdb.sh /docker-entrypoint-initdb.d/initdb.sh
COPY create_table.sql /docker-entrypoint-initdb.d/create_table.sql
# COPY ./partman_conf.sql /docker-entrypoint-initdb.d/partman_conf.sql
