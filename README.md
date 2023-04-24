## AWS EC2 배포 방법(실행 전)
```
sudo apt-get update
```

```
sudo apt install docker.io

sudo apt install docker-compose

sudo usermod -aG docker ${USER}

sudo reboot
```

```
sudo apt install openjdk-11-jdk

nano ~/.bashrc

export JAVA_HOME= $(dirname $(dirname $(readlink -f $(which java))))
export PATH=$PATH:$JAVA_HOME/bin

source ~/.bashrc
```

```
sudo apt install certbot

sudo certbot certonly --standalone

sudo chmod 755 /etc/letsencrypt/live

cd /etc/letsencrypt/live/{DOMAIN_URL}

sudo openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name nearbuy -CAfile chain.pem -caname root

sudo mv keystore.p12 ~/

cd ~
```

## 실행
```
git clone https://github.com/Dcom-KHU/nearbuy-backend.git

sudo mv keystore.p12 ./nearbuy-backend

cd ./nearbuy-backend

[Write down "MYSQL_ROOT_PASSWORD", "MONGO_INITDB_ROOT_PASSWORD" in docker-compose.yml]

docker-compose up -d

[Write down "spring.mail.password", "server.ssl.key-store-password" in /src/main/resources/application.yml]

[Write down "spring.datasource.password", "spring.data.mongodb.password" in /src/main/resources/application-db.yml] 

[Write down {NAVER CLIENT_ID + SECRET} / {KAKAO CLIENT_ID + SECRET} / {JWT SECRET KEY} in /src/main/resources/application-oauth.yml] 

sudo chmod 755 ./gradlew

sudo chmod 755 ./keystore.p12

./gradlew build

java -jar build/libs/nearbuy-backend-0.0.1-SNAPSHOT.jar
```
