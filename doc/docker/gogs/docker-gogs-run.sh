# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
# -v $HOME/gogs/volume/conf/app.ini:/data/gogs/conf/app.ini
docker run \
	--name gogs-master \
	--dns 172.16.20.121 \
	-p 10022:22 \
	-p 7001:3000 \
	-v $HOME/gogs/volume/data/:/data \
	-d codepasser/gogs
