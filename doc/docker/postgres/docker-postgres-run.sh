# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name postgres-master \
	-p 5432:5432 \
	-e POSTGRES_USER=postgres \
	-e POSTGRES_PASSWORD=Sa*963.-+ \
	-e POSTGRES_DB=postgres \
	-v $HOME/postgres/volume/data/:/var/lib/postgresql/data \
	-d codepasser/postgres
