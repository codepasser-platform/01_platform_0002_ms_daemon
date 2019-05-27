# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name elasticsearch-head \
	-p 9100:9100 \
	-d codepasser/elasticsearch-head