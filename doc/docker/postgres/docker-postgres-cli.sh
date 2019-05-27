# docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
docker exec -it postgres-master psql -U postgres -d postgres -h 127.0.0.1 -p 5432

