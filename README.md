

>   该项目自动装载 Druid , 并且可以通过配置文件设置是否开启 slq监控和 Druid Dashboard。 具体使用方法如下



### 使用步骤

1.  将项目clone 到你的本地
```
git clone https://github.com/jeesk/druid-spring-boot-starter.git
```
2.  使用IDEA 将项目打包的你的本地maven 仓库。 也可以直接在项目根目录下面使用 mavne 命令
```
mvn -DskipTests=true clean compile package install
```

3. 在你的项目pom文件中中引入 这个starter
```
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid-spring-boot-starter</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

4.  在你的项目中的配置以下配置文件
```
spring.datasource.username=root # 数据库账号名
spring.datasource.password=111111 # 数据库密码
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver  #  数据库驱动
spring.datasource.url=jdbc:mysql://127.0.0.1:3307/jdbc # 数据库地址
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource 
spring.datasource.initialSize=5
spring.datasource.minIdle=5
spring.datasource.maxActive=20
spring.datasource.maxWait=60000
spring.datasource.timeBetweenEvictionRunsMillis=60000
spring.datasource.minEvictableIdleTimeMillis=300000
spring.datasource.testWhileIdle=true
spring.datasource.testOnBorrow=false
spring.datasource.testOnReturn=false
spring.datasource.poolPreparedStatements=true
spring.datasource.filters=stat,wall,log4j
spring.datasource.maxPoolPreparedStatementPerConnectionSize=20
spring.datasource.useGlobalDataSourceStat=true
spring.datasource.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
spring.datasource.initialization-mode=always
#mybatis.mapper-locations=classpath:mapper/*Mapper.xml
#mybatis.config-location=classpath:mybatis-cnf.xml
# 
spring.datasource.druid.druid-servlet-enable=true # 开启Druid dashboard 
spring.datasource.druid.druid-sql-filter-enable=true # 开启sql 监控

spring.datasource.druid.druid-sql-filter.url-patterns=/*  # 配置druid 的sql 监控 url映射
spring.datasource.druid.druid-servlet.login-username=admin # 设置 druid dashboard 的登录账户名， 还有其他的设置可以同意看properties 类来配置
```

5.  该项目只是一个 Demo, 不足之处请提出意见。
 记得要在你的项目中引入 数据库驱动。