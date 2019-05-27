
# Centos7 GlusterFS 4.1 部署文档

https://wiki.centos.org/SpecialInterestGroup/Storage/gluster-Quickstart

> Step 0 Using Storage SIG Yum Repos

    yum install -y centos-release-gluster

> Step 1 – Have at least two nodes

glusterfs1	glusterfs1
glusterfs2	glusterfs2

> Step 2 - Format and mount the bricks (TODO)

    # mkfs.xfs -i size=512 /dev/sdb1
    # mkdir -p /bricks/brick1
    # vi /etc/fstab
    Add the following:

    /dev/sdb1 /bricks/brick1 xfs defaults 1 2
    Save the file and exit
    # mount -a && mount


> Step 3 - Installing GlusterFS

    yum install -y glusterfs-server
    # 服务注册
    systemctl enable glusterd
    # 服务启动
    systemctl start glusterd
    # 服务状态
    systemctl status glusterd
    # 停止服务
    systemctl stop glusterd

> Step 4 - Iptables configuration


> Step 5 - Configure the trusted pool

    # from glusterfs1
    gluster peer probe glusterfs1
    gluster peer probe glusterfs2

> Step 6 - Set up a GlusterFS volume

    # On both glusterfs1 and glusterfs2
    # su deploy user
    mkdir -p /data/glusterfs/volume0
    mkdir -p /data/storage

    # From any single server:
    # 基本卷：
    # (1)  distribute volume：分布式卷 ： 在分布式卷中，文件随机扩展到卷中的砖块中。 使用分布式卷，需要扩展存储和冗余不是很重要，或由其他硬件/软件层提供。
    # 创建语法：gluster volume create [transport tcp | rdma | tcp,rdma]
    gluster volume create StorageVolume glusterfs1:/data/glusterfs/volume0 glusterfs2:/data/glusterfs/volume0

    # (2)  stripe volume：条带卷： 条带卷在卷中的砖块之间进行条带化。 为获得最佳效果，只能在访问非常大的文件的高并发环境中使用条带卷。
    # 创建语法：gluster volume create [stripe ] [transport tcp | rdma | tcp,rdma]
    gluster volume create StorageVolume stripe 2 glusterfs1:/data/glusterfs/volume0 glusterfs2:/data/glusterfs/volume0

    # (3)  replica volume：复制卷 复制卷创建卷中多个砖块的文件副本。 可以在高可用性和高可靠性至关重要的环境中使用复制卷。
    # 创建语法：gluster volume create [replica ] [transport tcp | rdma | tcp,rdma]
    gluster volume create StorageVolume replica 2 glusterfs1:/data/glusterfs/volume0 glusterfs2:/data/glusterfs/volume0

    # 复合卷
    # (4)  distribute stripe volume：分布式条带卷
    # 分布式条带卷在集群中的两个或多个节点之间进行条带化。 为了获得最佳效果，应该使用分布式条带卷，其中要求是扩展存储，并且访问大型文件的高并发环境至关重要。
    # 创建语法： gluster volume create [stripe ] [transport tcp | rdma | tcp,rdma]
    # 例子：gluster volume create test-volume stripe 4 transport tcp server1:/exp1 server2:/exp2 server3:/exp3 server4:/exp4 server5:/exp5 server6:/exp6 server7:/exp7 server8:/exp8

    # (5)  distribute replica volume：分布式复制卷
    # 在卷中的复制卷上分发文件。 可以在需要扩展存储和高可靠性至关重要的环境中使用分布式复制卷。 分布式复制卷还可在大多数环境中提供改进的读取性能。
    # 创建语法：gluster volume create [replica ] [transport tcp | rdma | tcp,rdma]
    # 例子：gluster volume create test-volume replica 2 transport tcp server1:/exp1 server2:/exp2 server3:/exp3 server4:/exp4 

    # (6) stripe replica volume：条带复制卷
    # 将文件分割并备份随机存放在不同的服务器里,File被分割4段，1、3存放在server1(exp1)上，2、4存放在server2(exp4),server1上的(exp3)存放server2(exp4)的备份文件，server2上的(exp2)存放server1(exp1)的备份文件。
    # 例子:gluster volume create test-volume stripe 2 replica 2 transport tcp server1:/exp1 server2:/exp2 server3:/exp3 server4:/exp4

    # (7) distribute stripe replicavolume：分布式条带复制卷
    # 三种基本卷的复合卷。
    # 创建语法： gluster volume create [disperse [<count>]] [redundancy <count>] [transport tcp | rdma | tcp,rdma]
    # 例子：gluster volume create test1 disperse 3 server1:/brick{1..6} force


> Step 7 Volume start

    # Start volume
    gluster volume start StorageVolume

    # Confirm that the volume shows "Started":
    gluster volume info

> Step 8 - Client mount glusterFS volume

    # On both glusterfs1 and glusterfs2
    mkdir -p /data/storage
    # StorageVolume
    mount.glusterfs glusterfs1:/StorageVolume /data/storage

> Step 9 - Testing the GlusterFS volume

    # For this step, we will use one of the servers to mount the volume. Typically, you would do this from an external machine, known as a "client". Since using the method
    # here would require additional packages to be installed on the client machine, we will use the servers as a simple place to test first.

    mount -t glusterfs glusterfs1:/file-volume /mnt
    for i in `seq -w 1 100`; do cp -rp /var/log/messages /mnt/copy-test-$i; done
    
    # First, check the mount point:
    ls -lA /mnt | wc -l

    # You should see 100 files returned. Next, check the GlusterFS mount points on each server:
    ls -lA /bricks/brick1/file-volume

    You should see 100 per server using the method we listed here. Without replication, in a distribute only volume (not detailed here), you should see about 50 each.

> 常用命令

    # 从存储池中删除节点
    gluster peer detach glusterfs1
    gluster peer detach glusterfs2

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
    gluster volume stop StorageVolume
    # 客户端解除挂载
    umount glusterfs1:/StorageVolume 
    #删除
    gluster volume delete StorageVolume
