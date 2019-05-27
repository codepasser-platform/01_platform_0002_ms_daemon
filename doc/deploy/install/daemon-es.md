# 问题1 [1] bootstrap checks failed

# vi /etc/security/limits.conf

```
* soft memlock unlimited
* hard memlock unlimited
* soft nofile 65536
* hard nofile 131072
```

# 问题2 [2] bootstrap checks failed
# vi /etc/sysctl.conf
```
vm.max_map_count=655360
net.ipv4.ip_forward=1

sysctl -p
```

# 设置不限制应用最大内存
# bootstrap.memory_lock: true
# 设置为true来锁住内存。因为当jvm开始swapping时es的效率会降低，所以要保证它不swap，可以把ES_MIN_MEM和ES_MAX_MEM两个环境变量设置成同一个值，
# 并且保证机器有足够的内存分配给es。同时也要允许elasticsearch的进程可以锁住内存
```
ulimit -l unlimited
```

# Data挂在目录 777权限

```
chmod 777 $HOME/elasticsearch/volume/data/*
```

# 查询集群的健康状态

```
curl '172.16.20.121:9201/_cat/health?v'
curl -lk '172.16.20.121:9201/_cluster/health?pretty'
```

# 查询节点状态

```
curl '172.16.20.121:9201/_cat/nodes?v'
```

# 查看所有索引

```
curl '172.16.20.121:9201/_cat/indices?v'
```

# 创建索引

```
curl -XPUT '172.16.20.121:9201/sample_manuals?pretty'
```

# 删除索引

```
curl -XDELETE '172.16.20.121:9201/sample_manuals?pretty'
```

# 创建文档

```
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/manual/1?pretty' -d '
{
  "content": "John Doe"
}'

```

# 删除文档
```
curl -XDELETE '172.16.20.121:9201/sample_manuals/manual/1?pretty'
```

# IK 中文分词

> 1 test analyzer

```
# standard [default]
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_analyze?pretty' -d '
{
  "analyzer":"standard",
  "text":"我是中国人"
}
'

# ik_max_word
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_analyze?pretty' -d '
{
  "analyzer":"ik_max_word",
  "text":"我是中国人"
}
'

#ik_smart
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_analyze?pretty' -d '
{
  "analyzer":"ik_smart",
  "text":"我是中国人"
}
'

```

> 2 create a mapping

```
# 创建索引后，创建文档前定义mapping
curl -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/_mapping?pretty' -d '
{
  "properties": {
      "content": {
          "type": "text",
          "analyzer": "ik_max_word",
          "search_analyzer": "ik_max_word"
      }
  }
}'
# 执行成功后，查询确认定义mapping
curl '172.16.20.121:9201/sample_manuals/manual/_mapping?pretty'
```

> 3 create document 
```
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/manual/1?pretty' -d '
{
  "content": "John Doe"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/2?pretty' -d '
{
  "content":"美国留给伊拉克的是个烂摊子吗"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/3?pretty' -d '
{
  "content":"公安部：各地校车将享最高路权"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/4?pretty' -d '
{
  "content":"中韩渔警冲突调查：韩警平均每天扣1艘中国渔船"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/5?pretty' -d '
{
  "content":"中国驻洛杉矶领事馆遭亚裔男子枪击 嫌犯已自首"
}
'
```

> 4 test search ik analyzer

```
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_search?pretty' -d '
{
    "query" : { "match" : { "content" : "中国" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "content" : {}
        }
    }
}
'
```

# PINYIN 拼音分词

> 1 test analyzer

```
# standard [default]
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_analyze?pretty' -d '
{
  "analyzer":"pinyin",
  "text":"我是中国人"
}
'

curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_analyze?pretty' -d '
{
  "analyzer":"pinyin",
  "text":"woshizhongguoren"
}
'
```


> 2 create a mapping

```
# 创建索引后，创建文档前定义mapping
curl -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/_mapping?pretty' -d '
{
  "properties": {
      "name": {
          "type": "text",
          "analyzer": "pinyin",
          "search_analyzer": "pinyin"
      }
  }
}
'
# 执行成功后，查询确认定义mapping
curl '172.16.20.121:9201/sample_manuals/manual/_mapping?pretty'
```


> 3 create document 
```
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/manual/1?pretty' -d '
{
  "name": "John Doe"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/2?pretty' -d '
{
  "name":"刘德华"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/3?pretty' -d '
{
  "name":"王祖贤"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/4?pretty' -d '
{
  "name":"波多野结衣"
}
'

curl  -H "Content-Type: application/json" -XPUT '172.16.20.121:9201/sample_manuals/manual/5?pretty' -d '
{
  "name":"苍井空"
}
'
```

> 4 test search ik analyzer

```
curl -H "Content-Type: application/json" -XPOST '172.16.20.121:9201/sample_manuals/_search?pretty' -d '
{
    "query" : { "match" : { "name" : "de" }},
    "highlight" : {
        "pre_tags" : ["<tag1>", "<tag2>"],
        "post_tags" : ["</tag1>", "</tag2>"],
        "fields" : {
            "name" : {}
        }
    }
}
'
```