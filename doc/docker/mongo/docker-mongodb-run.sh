# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name mongo-master \
	-p 27017:27017 \
	-v $HOME/mongo/volume/data/:/data/db \
	-d codepasser/mongo
