 

开发方式如下：
前后端业务需求确定》前端布局/前端业务逻辑（浏览器调试）
后端业务逻辑DAL/BLL/（jUnit测试）》
后端服务发布（热部署）》前端Rest调用测试（整体统一测试）；

项目依赖树结构
mvn dependency:tree

安装本地jar:
mvn install:install-file -DgroupId=com.bea.xml -DartifactId=jsr173-ri -Dversion=1.0 -Dpackaging=jar -Dfile=[path to file]
 


安装本地oracle驱动:
mvn install:install-file -Dfile=ojdbc-10.2.0.1.0.jar -DgroupId=com.oracle -DartifactId=ojdbc -Dversion=10.2.0.1.0 -Dpackaging=jar
mvn install:install-file -Dfile=ojdbc-11.2.0.1.0.jar -DgroupId=com.oracle -DartifactId=ojdbc -Dversion=11.2.0.1.0 -Dpackaging=jar



开发环境log4j配置
-----------------------------------------------------------
# Output pattern : date [thread] priority category - message
log4j.rootLogger=DEBUG,ERROR,console
 
log4j.appender.DEBUG.Encoding=UTF-8

#Console
log4j.appender.console.Threshold=ERROR  
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.Encoding=UTF-8 
log4j.appender.console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n
  
#hibernate default level
log4j.logger.org.hibernate.type.descriptor.sql.BasicBinder=Trace
log4j.logger.org.hibernate.type.descriptor.sql.BasicExtractor=TRACE  
log4j.logger.org.hibernate.engine.QueryParameters=DEBUG 
log4j.logger.org.hibernate.engine.query.HQLQueryPlan=DEBUG 

#apache
log4j.logger.org.apache=ERROR

log4j.logger.net.sf.ehcache=ERROR 
