# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name redis-node-13 \
	--net host \
	-v $HOME/redis/volume/conf/redis-node-13.conf:/usr/local/etc/redis/redis.conf \
	-v $HOME/redis/volume/data/node-13:/data \
	-d codepasser/redis redis-server /usr/local/etc/redis/redis.conf
