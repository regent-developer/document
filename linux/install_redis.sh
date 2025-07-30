#!/bin/bash	
redis_config=/usr/local/redis/bin/redis.conf
function gcc_install(){	    yum install gcc gcc-c++ man wget telnet perl-devel python-devel net-tools -y}
function install_redis() {    cd /usr/local/src    wget http://download.redis.io/releases/redis-6.2.6.tar.gz    tar -zxf redis-6.2.6.tar.gz    cd  redis-6.2.6 && make && make install PREFIX=/usr/local/redis    mkdir -p /usr/local/redis/{bin,run,log,data}    yes| cp -r /usr/local/src/redis-6.2.6/redis.conf /usr/local/redis/bin/    sed -ri '/^bind/s#127.0.0.1 -::1#0.0.0.0#g' $redis_config    sed -ri '/^dir/s/\.\//\/usr\/local\/redis\/data/' $redis_config	    sed -ri '/^protected-mode/s/yes/no/' $redis_config    sed -ri '/^logfile/s/\"\"/\"\/usr\/local\/redis\/log\/redis.log\"/' $redis_config    sed -ri '/^# requirepass/s/# requirepass foobared/requirepass 123456/' $redis_config    export PATH=$PATH:/usr/local/redis/bin
cat > /usr/lib/systemd/system/redis.service << EOF	[Unit]Description=RedisAfter=network.target
[Service]ExecStart=/usr/local/redis/bin/redis-server /usr/local/redis/bin/redis.conf --daemonize noExecStop=/usr/local/redis/bin/redis-cli -h 127.0.0.1 -p 6379 shutdown
[Install]WantedBy=multi-user.targetEOF
systemctl daemon-reload	systemctl start redis.service}
gcc_install	install_redis