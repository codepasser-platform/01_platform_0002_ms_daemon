# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name zookeeper-b \
	-p 12181:2181 \
	-v $HOME/zookeeper-cluster/volume/conf/zoo.cfg:/conf/zoo.cfg \
	-v $HOME/zookeeper-cluster/volume/data-b/:/data \
	-d codepasser/zookeeper
