# Redis数据结构

## String
* 单个操作:set 、 get 、 exists 、del、setex、setnx(不存在就创建)、incr、incrby
* 批量操作:mset 、mget

## list
* 操作：rpush ， lpop，rpop，llen，lindex(得到某个位置的元素，可为负数，表示倒序拿，非常慢，慎用)，ltrim（保留某个范围的元素）

## hash
* 操作：hset 、hgetall 、hlen、hget 、hmset、hincrby、incr

## set
* 操作：sadd ， smembers ， sismember ， scard(获取长度) ， spop

## zset
* 操作：zadd、zrange（按照score得到某个范围的数据）、zrevrange（按照score得到某个范围的数据逆序输出）、zcard（含几个元素） 、zrank（某个元素排第几位） 、 zrangebyscore（按照排序输出范围）、zrem（删除某个元素）、 zscore（得到某个元素的 score）
