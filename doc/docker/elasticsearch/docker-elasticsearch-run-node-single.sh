# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name elasticsearch-single \
	-p 9200:9200 \
	-p 9300:9300 \
	-e "ES_JAVA_OPTS=-Xms1024m -Xmx1024m" \
	-v $HOME/elasticsearch/volume/conf/elasticsearch-single.yml:/usr/share/elasticsearch/config/elasticsearch.yml \
	-v $HOME/elasticsearch/volume/plugins/elasticsearch-analysis-ik-6.4.3:/usr/share/elasticsearch/plugins/elasticsearch-analysis-ik-6.4.3 \
	-v $HOME/elasticsearch/volume/plugins/elasticsearch-analysis-pinyin-6.4.3:/usr/share/elasticsearch/plugins/elasticsearch-analysis-pinyin-6.4.3 \
	-v $HOME/elasticsearch/volume/data/node-single:/usr/share/elasticsearch/data \
	-d codepasser/elasticsearch