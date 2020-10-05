@echo off
docker run --name ToDoContainer -p 1212:3306 -e MYSQL_ROOT_PASSWORD=1234 -d mysql/mysql-server:8.0.20
docker cp create_todo_list_data_base.sql ToDoContainer:/create_todo_list_data_base.sql
docker exec -it ToDoContainer bash -c "mysql -h localhost -u root -p1234 < create_todo_list_data_base.sql"