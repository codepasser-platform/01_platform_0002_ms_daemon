# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name redis-node-03 \
	--net host \
	-v $HOME/redis/volume/conf/redis-node-03.conf:/usr/local/etc/redis/redis.conf \
	-v $HOME/redis/volume/data/node-03:/data \
	-d codepasser/redis redis-server /usr/local/etc/redis/redis.conf
