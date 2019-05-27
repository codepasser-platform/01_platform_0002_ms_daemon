# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name ldap-admin \
	-p 6443:443 \
	-e PHPLDAPADMIN_LDAP_HOSTS=172.16.20.121 \
	-d codepasser/phpldapadmin
