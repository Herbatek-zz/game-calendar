## docker database run
```
docker run --rm -d -p 3306:3306 -e MYSQL_DATABASE=gamecalendar -e MYSQL_USER=gamer_1 -e MYSQL_PASSWORD=hardpassword -e MYSQL_ROOT_PASSWORD=superhardpassword mariadb --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```
## jenkins and sonar
```
docker-compose -f compose-jenkins-sonar.yml up -d
```

