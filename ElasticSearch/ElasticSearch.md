# ElasticSearch

Elasticsearch（简称ES）是一个基于Apache Lucene™的开源搜索引擎，无论在开源还是专有领域，Lucene 可以被认为是迄今为止最先进、性能最好的、功能最全的搜索引擎库。注意，Lucene 只是一个库。想要发挥其强大的作用，你需使用 Java 并要将其集成到你的应用中。

Lucene 非常复杂，你需要深入的了解检索相关知识来理解它是如何工作的，就跟学习 springmvc 之前先从 servlet 开始，繁琐复杂的工作，Solor、Elasticsearch 应由而生， 其使用 Java 编写并使用 Lucene 来建立索引并实现搜索功能，但是它的目的是通过简单连贯的 RESTful API 让全文搜索变得简单并隐藏 Lucene 的复杂性。

官网地址  
https://www.elastic.co/

es各个版本的下载地址  
https://www.elastic.co/downloads/past-releases#elasticsearch


## 安装

* 将下载的tar包上传到服务器，并解压

```shell
tar -xzvf xxx.tar.gz
```

* 新建运行es的用户

```shell
useradd -m -s /bin/bash elasticsearch
```

* 建立词典文件,es启动时指定了这个文件
```shell
touch ./elasticsearch-7.7.1/config/search_dic.csv
```

* 配置JAVA_HOME

```shell
vim ~/.bashrc

export JAVA_HOME=/home/elasticsearch/elasticsearch-7.7.1/jdk
export PATH=.:$JAVA_HOME/bin:$PATH

source ~/.bashrc

java -version
```

* 启动
```shell
/home/elasticsearch/elasticsearch-7.7.1/bin/elasticsearc
```