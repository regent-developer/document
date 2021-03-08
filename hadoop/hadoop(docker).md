# hadoop(docker)

## 环境搭建

### 下载 centos 镜像

```shell
docker pull java
```

### 镜像重命名

```shell
docker tag [image_id] hadoop:latest
docker rmi java
```

### 建立容器

```shell
docker run -it hadoop /bin/bash
```

### 安装 yum

```shell
apt-get update
apt-get install yum
```

### SSH 的安装以及配置（todo）

```shell
yum install openssh-server
yum install openssh-clients
```

### 下载 hadoop3.2.2

```shell
curl -o hadoop-3.2.2.tar.gz  http://archive.apache.org/dist/hadoop/common/hadoop-3.2.2/hadoop-3.2.2.tar.gz
```

### 解压 hadoop

```shell
tar -zxvf hadoop-3.2.2.tar.gz
```

### 添加 Hadoop 相关的环境变量

编辑/etc/profile,添加 HADOOP_HOME 变量，并将 HADOOP_HOME 中的 bin 添加到 PATH 中

```shell
export HADOOP_HOME=/home/hadoop-3.2.2
export PATH=${JAVA_HOME}/bin:${HADOOP_HOME}/bin:$PATH
```

### 配置伪分布式

- 编辑/home/hadoop-3.2.2/etc/hadoop/core-site.xml 文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<configuration>
        <property>
                <name>hadoop.tmp.dir</name>
                <value>file:/home/hadoop/tmp</value>
        </property>
        <property>
                <name>fs.defaultFS</name>
                <value>hdfs://localhost:9000</value>
        </property>
</configuration>
```

- 编辑/home/hadoop-3.2.2/etc/hadoop/hdfs-site.xml 文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>

<configuration>
        <property>
                <name>dfs.replication</name>
                <value>1</value>
        </property>
        <property>
                <name>dfs.namenode.name.dir</name>
                <value>file:/home/hadoop/dfs/name</value>
        </property>
        <property>
                <name>dfs.datanode.data.dir</name>
                <value>file:/home/hadoop/dfs/data</value>
        </property>
</configuration>
```

### 格式化 namenode

```shell
./hdfs namenode -format
```

### 开启 HDFS 和 Yarn

```shell
./start-dfs.sh
./start-yarn.sh
```
