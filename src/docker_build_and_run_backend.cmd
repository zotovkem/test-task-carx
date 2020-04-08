rem cd ..

rem mvn clean package -DskipTests=true

call docker build --target carx-app -t carx:latest .
call docker build --target carx_redis -t carx_redis:latest .
call docker build --target carx_postgres -t carx_postgres:latest .

rem cd ..
rem Чистим volume docker образа postgres, иначе не отрабатывает скрипт init.sql.
call docker volume rm -f backend_pg_data_volume
rem Запускаем докер контейнеры.
call docker-compose up -d --build
