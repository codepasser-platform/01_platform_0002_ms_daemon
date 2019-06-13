# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name oracle-master \
	-p 8080:8080 \
	-p 1521:1521 \
	-v $HOME/oracle/volume/data:/u01/app/oracle \
	-e TZ=Asia/Shanghai \
	-e DBCA_TOTAL_MEMORY=1024 \
	-d codepasser/oracle