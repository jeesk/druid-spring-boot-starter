package com.alibaba.druid.springboot.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration // 申明这是一个配置类
@EnableConfigurationProperties({DruidServletDashBoardProperties.class, DruidSqlFilterProperties.class})
public class DruidAutoConfiguration {


    @Autowired
    DruidServletDashBoardProperties druidServletProperties;
    @Autowired
    DruidSqlFilterProperties druidSqlFilterProperties;


    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @ConditionalOnClass(DruidDataSource.class)
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    // 引入 sql 监控
    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(name = "spring.datasource.druid.druid-sql-filter-enable", havingValue = "true")
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> initParams = new HashMap<>();
        initParams.put("loginUsername", druidServletProperties.getLoginUsername());
        initParams.put("loginPassword", druidServletProperties.getLoginPassword());
        initParams.put("allow", druidServletProperties.getAllow());
        initParams.put("deny", druidServletProperties.getDeny());
        bean.setInitParameters(initParams);
        return bean;
    }

    // 引入 Druid 的DashBoard
    @Bean
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    @ConditionalOnProperty(name = "spring.datasource.druid.druid-servlet-enable", havingValue = "true")
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", druidSqlFilterProperties.getExclusions());
        bean.setInitParameters(initParams);
        bean.setUrlPatterns(Arrays.asList(druidSqlFilterProperties.getUrlPatterns()));
        return bean;
    }
}
