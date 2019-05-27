# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name elasticsearch-node-3 \
	-p 9203:9203 \
	-p 9303:9303 \
	-e "ES_JAVA_OPTS=-Xms1024m -Xmx1024m" \
	-v $HOME/elasticsearch/volume/conf/elasticsearch-node-3.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
	-v $HOME/elasticsearch/volume/plugins/elasticsearch-analysis-ik-6.4.3:/usr/share/elasticsearch/plugins/elasticsearch-analysis-ik-6.4.3 \
	-v $HOME/elasticsearch/volume/plugins/elasticsearch-analysis-pinyin-6.4.3:/usr/share/elasticsearch/plugins/elasticsearch-analysis-pinyin-6.4.3 \
	-v $HOME/elasticsearch/volume/data/node-3:/usr/share/elasticsearch/data \
	-d codepasser/elasticsearch
