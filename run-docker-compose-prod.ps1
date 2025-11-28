docker-compose -f docker-compose.prod.yml -p app-dev down -v
docker-compose -f docker-compose.prod.yml -p app-prod down -v
docker-compose -f docker-compose.prod.yml -p app-prod build --no-cache
docker-compose -f docker-compose.prod.yml -p app-prod up -d