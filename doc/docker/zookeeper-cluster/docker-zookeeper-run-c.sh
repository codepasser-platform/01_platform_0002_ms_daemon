# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name zookeeper-c \
	-p 22181:2181 \
	-v $HOME/zookeeper-cluster/volume/conf/zoo.cfg:/conf/zoo.cfg \
	-v $HOME/zookeeper-cluster/volume/data-c/:/data \
	-d codepasser/zookeeper
