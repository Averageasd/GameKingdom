docker-compose -f docker-compose.dev.yml -p app-dev down -v
docker-compose -f docker-compose.dev.yml -p app-dev build --no-cache
docker-compose -f docker-compose.dev.yml -p app-dev up -d