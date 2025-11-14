docker-compose down -v
docker-compose build --no-cache
docker-compose up -d
docker image prune -af
docker container prune -f