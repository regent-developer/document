# https自签证书

···bash
# 设置openssl配置文件路径
set OPENSSL_CONF=..\conf\openssl.cnf

# 生成RSA私钥，长度为1024位，输出到server.key文件
openssl genrsa -out server.key 1024

# 生成证书签名请求，输出到server.csr文件，使用server.key文件中的私钥
openssl req -new -out server.csr -key server.key

# 生成证书签名请求，输出到server.csr文件，使用server.key文件中的私钥
openssl req -new -out server.csr -key server.key

# 生成RSA私钥，长度为1024位，输出到ca.key文件
openssl genrsa -out ca.key 1024

# 生成自签名证书，输出到ca.crt文件，使用ca.key文件中的私钥，有效期为365天
openssl req -new -x509 -days 365 -key ca.key -out ca.crt

# 前提
在当前文件夹下创建文件夹demoCA	
在当前文件夹下创建文件index.txt	
在当前文件夹下创建文件serial.txt，内容为01，将文件后缀删除	
文件夹demoCA下创建文件夹newcerts
	
# 使用ca.crt和ca.key文件中的证书和私钥，对server.csr文件中的证书签名请求进行签名，输出到server.crt文件
openssl ca -in server.csr -out server.crt -cert ca.crt -keyfile ca.key
···