# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
# -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=Sa*963.-+ 
docker run \
	--name rabbitmq-master \
	-p 5672:5672 \
	-v $HOME/rabbitmq/volume/conf/rabbitmq.config:/etc/rabbitmq/rabbitmq.config \
	-v $HOME/rabbitmq/volume/data/:/var/lib/rabbitmq/mnesia/ \
	-d codepasser/rabbitmq
