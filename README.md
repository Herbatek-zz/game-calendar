## docker database run
```
docker run --rm -d -p 3306:3306 -e MYSQL_DATABASE=gamecalendar -e MYSQL_USER=gamer_1 -e MYSQL_PASSWORD=hardpassword -e MYSQL_ROOT_PASSWORD=superhardpassword mariadb
```
```
docker run   --rm -d -u root   -p 8080:8080   -v /home/kenshin/jenkins-data:/var/jenkins_home   -v /var/run/docker.sock:/var/run/docker.sock   -v "$HOME":/home   jenkinsci/blueocean
```

