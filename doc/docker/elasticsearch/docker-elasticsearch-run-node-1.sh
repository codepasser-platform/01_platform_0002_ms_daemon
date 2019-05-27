# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name elasticsearch-node-1 \
	-p 9201:9201 \
	-p 9301:9301 \
	-e "ES_JAVA_OPTS=-Xms1024m -Xmx1024m" \
	-v $HOME/elasticsearch/volume/conf/elasticsearch-node-1.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
	-v $HOME/elasticsearch/volume/plugins/elasticsearch-analysis-ik-6.4.3:/usr/share/elasticsearch/plugins/elasticsearch-analysis-ik-6.4.3 \
	-v $HOME/elasticsearch/volume/plugins/elasticsearch-analysis-pinyin-6.4.3:/usr/share/elasticsearch/plugins/elasticsearch-analysis-pinyin-6.4.3 \
	-v $HOME/elasticsearch/volume/data/node-1:/usr/share/elasticsearch/data \
	-d codepasser/elasticsearch
