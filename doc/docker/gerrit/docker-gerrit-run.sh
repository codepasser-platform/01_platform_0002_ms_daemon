# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name gerrit-master \
	--link postgres-master:db \
	-p 7003:8080 \
	-p 29418:29418 \
	-e WEBURL=http://gerrit.codepasser.io \
	-e DATABASE_TYPE=postgresql \
	-e DB_ENV_POSTGRES_DB=gerrit \
	-e DB_ENV_POSTGRES_USER=gerrit \
	-e DB_ENV_POSTGRES_PASSWORD=gerrit_pw \
	-e AUTH_TYPE=LDAP \
	-e LDAP_SERVER=ldap://172.16.20.121:389 \
	-e LDAP_ACCOUNTBASE=dc=codepasser,dc=io \
	-v $HOME/gerrit/volume/data/:/var/gerrit/review_site \
	-d codepasser/gerrit
