# docker exec [OPTIONS] CONTAINER COMMAND [ARG...]
#docker exec -it oracle-master bash -c "source /home/oracle/.bashrc; sqlplus / as sysdba"
docker exec -it oracle-master bash -c "source /home/oracle/.bashrc; sqlplus /nolog"