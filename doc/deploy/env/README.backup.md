
# CentOS7 Docker Mysql 备份

# 创建GlusterFS备份存储卷，利用GlusterFS 分布式存储备份文件

> Step 1 Volume start

    # 创建卷目录 glusterfs1
    mkdir -p /data/glusterfs/volume1
    # From any single server:
    # 基本卷：
    # (1)  distribute volume：分布式卷 ： 在分布式卷中，文件随机扩展到卷中的砖块中。 使用分布式卷，需要扩展存储和冗余不是很重要，或由其他硬件/软件层提供。
    # 创建语法：gluster volume create [transport tcp | rdma | tcp,rdma]
    gluster volume create BackupVolume glusterfs1:/data/glusterfs/volume1

> Step 2 Volume start

    # Start volume
    gluster volume start BackupVolume

    # Confirm that the volume shows "Started":
    gluster volume info

> Step 3 - Client mount glusterFS volume

    # On both glusterfs1 and glusterfs2
    mkdir -p /data/backup
    # BackupVolume
    mount.glusterfs glusterfs1:/BackupVolume /data/backup

> Step 4 - Testing the GlusterFS volume

    # For this step, we will use one of the servers to mount the volume. Typically, you would do this from an external machine, known as a "client". Since using the method
    # here would require additional packages to be installed on the client machine, we will use the servers as a simple place to test first.

    mount -t glusterfs glusterfs1:/file-volume /mnt
    for i in `seq -w 1 100`; do cp -rp /var/log/messages /mnt/copy-test-$i; done
    
    # First, check the mount point:
    ls -lA /mnt | wc -l

    # You should see 100 files returned. Next, check the GlusterFS mount points on each server:
    ls -lA /bricks/brick1/file-volume

    You should see 100 per server using the method we listed here. Without replication, in a distribute only volume (not detailed here), you should see about 50 each.

> Step 5 Create docker backup directory soft connection

    ln -s /data/backup/mysql /data/docker/mysql/backup
    
> Step 6 backup shell command

    _TIMESTAMP=$(date "+%Y%m%d-%H%M%S")
    echo "backup"${_TIMESTAMP}".sql"
    /usr/bin/docker exec  mysql-master mysqldump --add-drop-table -uroot -pSa*963.-+ capital_pre_ipo > "/data/docker/mysql/backup/"$_TIMESTAMP".sql"

> Step 7 Create crontab task

    # docker user
    crontab -e
    # > echo
    0 2 * * * /data/docker/mysql/docker-mysql-backup.sh

> 常用命令

    #状态
    gluster volume info
    # gluster peer help
    gluster peer detach { <HOSTNAME> | <IP-address> } [force] - detach peer specified by <HOSTNAME>
    gluster peer help - Help command for peer
    gluster peer probe { <HOSTNAME> | <IP-address> } - probe peer specified by <HOSTNAME>
    gluster peer status - list status of peers
    gluster pool list - list all the nodes in the pool (including localhost)

    # 卸载
    #停止
    gluster volume stop BackupVolume
    # 客户端解除挂载
    umount glusterfs1:/BackupVolume
    #删除
    gluster volume delete BackupVolume
