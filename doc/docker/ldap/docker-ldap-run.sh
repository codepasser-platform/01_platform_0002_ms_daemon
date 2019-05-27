# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run \
	--name ldap-master \
	-p 389:389 \
	-p 636:636 \
	-e LDAP_ORGANISATION="codepasser" \
	-e LDAP_DOMAIN="codepasser.io" \
	-e LDAP_ADMIN_PASSWORD="Sa*963.-+" \
	-e LDAP_TLS=false \
	-v $HOME/ldap/volume/data:/var/lib/ldap \
	-v $HOME/ldap/volume/conf:/etc/ldap/slapd.d \
	-d codepasser/openldap
