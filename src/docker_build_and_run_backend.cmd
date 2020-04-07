chcp 65001

mvn clean package -DskipTests=true

call docker build --target carxapp -t carx:latest .
call docker build --target postgres -t carx_postgres:latest .

cd ..
rem Чистим volume docker образа postgres, иначе не отрабатывает скрипт init.sql.
call docker volume rm -f backend_pg_data_volume
rem Запускаем докер контейнеры.
call docker-compose up -d --build
