# docker run [OPTIONS] IMAGE [COMMAND] [ARG...]
docker run --privileged \
	--name=iredmail-master \
	--dns=172.16.20.121 \
	-p 7000:80 \
	-p 7443:443 \
	-p 25:25 \
	-e "DOMAIN=codepasser.io" \
	-e "HOSTNAME=iredmail" \
	-e "MYSQL_ROOT_PASSWORD=Sa*963.-+" \
	-e "SOGO_WORKERS=1" \
	-e "TIMEZONE=Asia/Shanghai" \
	-e "POSTMASTER_PASSWORD={PLAIN}Sa*963.-+" \
	-e "IREDAPD_PLUGINS=['reject_null_sender', 'reject_sender_login_mismatch', 'greylisting', 'throttle', 'amavisd_wblist', 'sql_alias_access_policy']" \
	-v $HOME/iredmail/volume/data/mysql:/var/lib/mysql \
	-v $HOME/iredmail/volume/data/vmail:/var/vmail \
	-v $HOME/iredmail/volume/data/clamav:/var/lib/clamav \
	-d codepasser/iredmail
