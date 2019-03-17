##docker run
docker run --rm -p 3306:3306 -e MYSQL_ROOT_PASSWORD=ROOT -e MYSQL_DATABASE=testDB -e MYSQL_USER=userTest -e MYSQL_PASSWORD=strongPassword mariadb