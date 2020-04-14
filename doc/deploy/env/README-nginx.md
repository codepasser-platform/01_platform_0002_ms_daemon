# Nginx 配置

### Nginx 资源

    http://nginx.org/

### 安装

> MAC

    # without ssl
    ./configure --prefix=/usr/local/nginx-proxy --conf-path=/usr/local/nginx-proxy/conf/nginx.conf --with-pcre=/usr/local/lib/pcre --with-http_stub_status_module --with-http_gzip_static_module --with-cc-opt="-Wno-deprecated-declarations" --without-http_fastcgi_module

    # with ssl
    ./configure --prefix=/usr/local/nginx-proxy --conf-path=/usr/local/nginx-proxy/conf/nginx.conf --with-pcre=/usr/local/lib/pcre --with-http_stub_status_module --with-http_gzip_static_module --with-http_ssl_module  --with-openssl=/usr/local/lib/openssl --with-cc-opt="-Wno-deprecated-declarations" --without-http_fastcgi_module

    # 本地 with ssl
    sudo ./configure --prefix=/Users/codepasser/Applications/nginx-1.16.1 --conf-path=/Users/codepasser/Applications/nginx-1.16.1/conf/nginx.conf --with-pcre=/usr/local/lib/pcre-8.42 --with-http_stub_status_module --with-http_gzip_static_module  --with-http_ssl_module --with-openssl=/usr/local/lib/openssl --with-cc-opt="-Wno-deprecated-declarations" --without-http_fastcgi_module
    sudo make
    sudo make install

    - 注：Mac 手动安装openssl
    git clone git://git.openssl.org/openssl.git
    sudo ./config --prefix=/usr/local/lib/openssl
    make
    make install
    openssl version
    
> CentOS 7

    # CentOS 常用开发库依赖安装
    yum -y install make gcc gcc-c++ ncurses-devel perl pcre-devel zlib zlib-devel
    yum -y install openssl openssl-devel 
    yum -y install libgd2-devel libpcre-devel libcurl-devel gd-devel libaio
    yum -y install net-tools telnet tree lsof wget unzip zip portmap vim lvm2 rsync ntp httpd nfs-utils psmisc
    yum install -y perl-Module-Install.noarch

    # without ssl
    ./configure --user=root --group=root --prefix=/usr/local/lib/nginx/nginx-proxy --with-pcre --with-http_stub_status_module --with-http_gzip_static_module --add-module=../nginx-cache-purge-2.3 --without-http_fastcgi_module
    
    # with ssl
    ./configure --user=root --group=root --prefix=/usr/local/lib/nginx/nginx-proxy --with-pcre --with-http_stub_status_module --with-http_gzip_static_module --with-http_ssl_module
    # --add-module=../cache-purge-2.3

### Nginx配置SSL


#### 使用OpenSSL创建证书


> 建立服务器私钥（过程需要输入密码，请记住这个密码）生成RSA密钥(目录:  nginx/conf/ssl)

    openssl genrsa -des3 -out daemon.key 1024
    
> 生成一个证书请求    
    
    openssl req -new -key daemon.key -out daemon.csr
    
    # 需要依次输入国家，地区，组织，email。最重要的是有一个common name，可以写你的名字或者域名。
    # 如果为了https申请，这个必须和域名吻合，否则会引发浏览器警报。生成的csr文件交给CA签名后形成服务端自己的证书
    #---------------------------------------------------------------------------------------------------------------
    Enter pass phrase for daemon.key:                          # 之前输入的密码
    Country Name (2 letter code) [XX]:                            # 国家
    State or Province Name (full name) []:                        # 区域或是省份
    Locality Name (eg, city) [Default City]:                      # 地区局部名字
    Organization Name (eg, company) [Default Company Ltd          # 机构名称：填写公司名
    Organizational Unit Name (eg, section) []:                    # 组织单位名称:部门名称
    Common Name (eg, your name or your daemon's hostname) []:  # 网站域名
    Email Address []:                                             # 邮箱地址
    A challenge password []:                                      # 输入一个密码，可直接回车
    An optional company name []:                                  # 一个可选的公司名称，可直接回车
    #---------------------------------------------------------------------------------------------------------------
    #输入完这些内容，就会在当前目录生成 daemon.csr文件

> 使用上面的密钥和CSR对证书进行签名

    cp daemon.key daemon.key.com
    
    # 输入第一次密码
    openssl rsa -in daemon.key.com -out daemon.key

> 以下命令生成v1版证书

    # daemon.crt
    openssl x509 -req  -days 365 -sha256 -in daemon.csr -signkey daemon.key -out daemon.crt

> 以下命令生成v3版证书

    openssl x509 -req  -days 365 -sha256 -extfile openssl.cnf -extensions v3_req   -in daemon.csr -signkey daemon.key -out daemon.crt
    
    #v3版证书另需配置文件openssl.cnf，该文件内容详见博客《OpenSSL生成v3证书方法及配置文件》至此，证书生成完毕!
    
    #查看key、csr及证书信息
    openssl rsa -noout -text -in daemon.key
    openssl req -noout -text -in daemon.csr
    openssl x509 -noout -text -in daemon.crt

#### nginx配置 https服务

    # nginx配置 https proxy
    server {
    
        listen 443 ssl;

        server_name www.codepasser.com;

        charset utf-8;
        
        #access_log logs/access.www.codepasser.com.log main;
    
        ssl on;
        ssl_certificate      ssl/daemon.crt;
        ssl_certificate_key  ssl/daemon.key;
        
        #ssl_session_cache    shared:SSL:1m;
        #ssl_session_timeout  5m;
      
        #ssl_ciphers  HIGH:!aNULL:!MD5;
        #ssl_prefer_server_ciphers  on;
        ...
    }
