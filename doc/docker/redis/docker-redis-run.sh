# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name redis-master \
	-p 6379:6379 \
	-v $HOME/redis/volume/conf/redis.conf:/usr/local/etc/redis/redis.conf \
	-v $HOME/redis/volume/data/:/data \
	-d codepasser/redis redis-server /usr/local/etc/redis/redis.conf