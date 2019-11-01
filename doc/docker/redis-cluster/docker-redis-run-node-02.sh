# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name redis-node-02 \
	--net host \
	-v $HOME/redis/volume/conf/redis-node-02.conf:/usr/local/etc/redis/redis.conf \
	-v $HOME/redis/volume/data/node-02:/data \
	-d codepasser/redis redis-server /usr/local/etc/redis/redis.conf
