# CLI
docker exec -it redis-master redis-cli
# AUTHORIZATION
AUTH cde3VFR$

# 集群配置

> redis.conf

- 【注】： 配置创建集群前，不能设置密码，构建集群之后设置密码后重启

```
# 修改绑定地址
bind 0.0.0.0

# 将守护进程yes改成no
daemonize no

# 将密码项注释去掉，添加新密码
requirepass cde3VFR$

# 因为配置了密码，所以，配置中另外一处主从连接也需要配置密码
masterauth cde3VFR$

# 配置集群相关信息，去掉配置项前面的注释
# 如果想在特定的Redis实例中启用Redis群集支持就设置为yes。 否则，实例通常作为独立实例启动。
cluster-enabled yes

# 请注意，尽管有此选项的名称，但这不是用户可编辑的配置文件，而是Redis群集节点每次发生更改时自动保留群集配置（基本上为状态）的文件，
# 以便能够 在启动时重新读取它。 该文件列出了群集中其他节点，它们的状态，持久变量等等。 由于某些消息的接收，通常会将此文件重写并刷新到磁盘上。
cluster-config-file nodes-01.conf

# Redis群集节点可以不可用的最长时间，而不会将其视为失败。 如果主节点超过指定的时间不可达，它将由其从属设备进行故障切换。 
# 此参数控制Redis群集中的其他重要事项。 值得注意的是，每个无法在指定时间内到达大多数主节点的节点将停止接受查询。
cluster-node-timeout 15000

# 如果设置为0，无论主设备和从设备之间的链路保持断开连接的时间长短，从设备都将尝试故障切换主设备。
# 如果该值为正值，则计算最大断开时间作为节点超时值乘以此选项提供的系数，
# 如果该节点是从节点，则在主链路断开连接的时间超过指定的超时值时，它不会尝试启动故障切换。 
# 例如，如果节点超时设置为5秒，并且有效因子设置为10，则与主设备断开连接超过50秒的从设备将不会尝试对其主设备进行故障切换。 
# 请注意，如果没有从服务器节点能够对其进行故障转移，则任何非零值都可能导致Redis群集在主服务器出现故障后不可用。 在这种情况下，只有原始主节点重新加入集群时，集群才会返回可用。
# cluster-slave-validity-factor <factor>

# 主设备将保持连接的最小从设备数量，以便另一个从设备迁移到不受任何从设备覆盖的主设备。有关更多信息，请参阅本教程中有关副本迁移的相应部分。
# cluster-migration-barrier <count>

# 如果将其设置为yes，则默认情况下，如果key的空间的某个百分比未被任何节点覆盖，则集群停止接受写入。 如果该选项设置为no，则即使只处理关于keys子集的请求，群集仍将提供查询。
# cluster-require-full-coverage <yes / no>
```

> 配置示例

```
bind 0.0.0.0  // 绑定服务器IP地址

port 6379  // 绑定端口号，必须修改，以此来区分Redis实例

requirepass cde3VFR$ // 设置密码

masterauth cde3VFR$ // 设置集群密码

cluster-enabled yes  // 启用集群

cluster-config-file nodes-01.conf  // 配置每个节点的配置文件，同样以端口号为名称

cluster-node-timeout 15000  // 配置集群节点的超时时间，可改可不改

appendonly yes  // 启动AOF增量持久化策略

appendfsync always  // 发生改变就记录日志
```

> 集群节点不同配置项
```
# node-01
port 6379
cluster-config-file nodes-01.conf

# node-02
port 6380
cluster-config-file nodes-02.conf

# node-03
port 6381
cluster-config-file nodes-03.conf

# node-11
port 6389
cluster-config-file nodes-11.conf

# node-12
port 6390
cluster-config-file nodes-12.conf

# node-13
port 6391
cluster-config-file nodes-13.conf
```

> 下载 inem0o/redis-trib

```
docker pull inem0o/redis-trib
```

> 建立集群

```
## replicas 1 每台主服务配置一个从服务
## 6台服务 前3主服务 后3依次为对应从服务
docker run --rm -it inem0o/redis-trib create \
    --replicas 1 \
    172.16.20.121:6379 \
    172.16.20.121:6380 \
    172.16.20.121:6381 \
    172.16.20.121:6389 \
    172.16.20.121:6390 \
    172.16.20.121:6391
```

> 运行结果

```
>>> Creating cluster
>>> Performing hash slots allocation on 6 nodes...
Using 3 masters:
172.16.20.121:6379
172.16.20.121:6380
172.16.20.121:6381
Adding replica 172.16.20.121:6389 to 172.16.20.121:6379
Adding replica 172.16.20.121:6390 to 172.16.20.121:6380
Adding replica 172.16.20.121:6391 to 172.16.20.121:6381
M: 1f6d45f35f6cd1f58400f3b45a43f1f6627bdb40 172.16.20.121:6379
   slots:0-5460 (5461 slots) master
M: f0eed6344015582691ea65503e624872eb36e48e 172.16.20.121:6380
   slots:5461-10922 (5462 slots) master
M: 4d3216a840f279d5128f2adb96cca5bd735afa6c 172.16.20.121:6381
   slots:10923-16383 (5461 slots) master
S: 18470780633fc85742c08ae792e8bdc083ef5fa1 172.16.20.121:6389
   replicates 1f6d45f35f6cd1f58400f3b45a43f1f6627bdb40
S: 952e17e0000d08a869ba59ba051a1a0eb168d576 172.16.20.121:6390
   replicates f0eed6344015582691ea65503e624872eb36e48e
S: 9879a557e8a658ba143534762e0e34229cd31bc4 172.16.20.121:6391
   replicates 4d3216a840f279d5128f2adb96cca5bd735afa6c
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join.....
>>> Performing Cluster Check (using node 172.16.20.121:6379)
M: 1f6d45f35f6cd1f58400f3b45a43f1f6627bdb40 172.16.20.121:6379
   slots:0-5460 (5461 slots) master
   1 additional replica(s)
S: 18470780633fc85742c08ae792e8bdc083ef5fa1 172.16.20.121:6389@16389
   slots: (0 slots) slave
   replicates 1f6d45f35f6cd1f58400f3b45a43f1f6627bdb40
S: 9879a557e8a658ba143534762e0e34229cd31bc4 172.16.20.121:6391@16391
   slots: (0 slots) slave
   replicates 4d3216a840f279d5128f2adb96cca5bd735afa6c
S: 952e17e0000d08a869ba59ba051a1a0eb168d576 172.16.20.121:6390@16390
   slots: (0 slots) slave
   replicates f0eed6344015582691ea65503e624872eb36e48e
M: 4d3216a840f279d5128f2adb96cca5bd735afa6c 172.16.20.121:6381@16381
   slots:10923-16383 (5461 slots) master
   1 additional replica(s)
M: f0eed6344015582691ea65503e624872eb36e48e 172.16.20.121:6380@16380
   slots:5461-10922 (5462 slots) master
   1 additional replica(s)
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```

> 查询集群状态
```
redis-trib.rb check 172.16.20.121:6379
```

> 分配槽信息

```
>cluster info
cluster_state:fail
cluster_slots_assigned:0    # 被分配槽的个数为0
cluster_slots_ok:0
cluster_slots_pfail:0
cluster_slots_fail:0
cluster_known_nodes:6
cluster_size:0
cluster_current_epoch:5
cluster_my_epoch:2
cluster_stats_messages_ping_sent:260418
cluster_stats_messages_pong_sent:260087
cluster_stats_messages_meet_sent:10
cluster_stats_messages_sent:520515
cluster_stats_messages_ping_received:260086
cluster_stats_messages_pong_received:260328
cluster_stats_messages_meet_received:1
cluster_stats_messages_received:520415
```

```
>cluster nodes
aeef20b6cbb105784e8de468813e55e90080baa3 172.16.20.121:6379@16379 myself,master - 0 1572598979000 1 connected 0-5460
2d2bfd398b17f93ae4ec72cce7584ed14d0b1fe6 172.16.20.121:6390@16390 slave 043aeeabcc8e2caed36f73db1b35fdfc113ce844 0 1572598979055 5 connected
043aeeabcc8e2caed36f73db1b35fdfc113ce844 172.16.20.121:6380@16380 master - 0 1572598978053 2 connected 5461-10922
5f0ca6625130ef2d10e52d562137f8509d228fe2 172.16.20.121:6389@16389 slave aeef20b6cbb105784e8de468813e55e90080baa3 0 1572598977050 4 connected
c1e9403bc9305a208d0a9988330a9bc9dc7f1359 172.16.20.121:6391@16391 slave b2d7824b02b722995b8f97e9f1f48432e9724b7d 0 1572598980058 6 connected
b2d7824b02b722995b8f97e9f1f48432e9724b7d 172.16.20.121:6381@16381 master - 0 1572598976048 3 connected 10923-16383
```

> 集群验证后，配置密码 后 重新启动

```
# 集群构建完成前不要配置密码，集群构建完毕再通过config set + config rewrite命令逐个机器设置密码。

config set masterauth cde3VFR$
config set requirepass cde3VFR$

> 验证
auth cde3VFR$

> 写入配置
config rewrite

```
