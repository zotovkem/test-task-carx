FROM postgres:10.1-alpine

# Копируем конфиг и скрипты
COPY pg-init.sql /docker-entrypoint-initdb.d/init.sql
RUN chmod 777 /docker-entrypoint-initdb.d/init.sql
#COPY postgresql.conf /tmp/postgresql.conf
#RUN chmod 777 /tmp/postgresql.conf
#COPY updateConfig.sh /docker-entrypoint-initdb.d/_updateConfig.sh
#RUN chmod 777 /docker-entrypoint-initdb.d/_updateConfig.sh
#COPY db_sync_linux.sh /db_sync_linux.sh
#RUN chmod 777 /db_sync_linux.sh
