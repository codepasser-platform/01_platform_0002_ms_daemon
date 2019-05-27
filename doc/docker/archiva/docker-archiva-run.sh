# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name archiva-master \
	-p 7002:8080 \
	-e DB_TYPE="mysql" \
	-e DB_HOST="172.16.20.121" \
	-e DB_PORT="3306" \
	-e DB_USER="archiva" \
	-e DB_PASS="archiva_pw" \
	-e USERS_DB_NAME="archiva" \
	-v $HOME/archiva/volume/data/:/archiva-data \
	-d codepasser/archiva
