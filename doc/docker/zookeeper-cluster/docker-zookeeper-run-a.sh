# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name zookeeper-a \
	-p 2181:2181 \
	-v $HOME/zookeeper-cluster/volume/conf/zoo.cfg:/conf/zoo.cfg \
	-v $HOME/zookeeper-cluster/volume/data-a/:/data \
	-d codepasser/zookeeper
