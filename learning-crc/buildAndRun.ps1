mvn clean package
docker build -t alliancecoder/learning-crc .
docker rm -f learning-crc
docker run -d -p 8080:8080 -p 9090:9090 --name learning-crc alliancecoder/learning-crc