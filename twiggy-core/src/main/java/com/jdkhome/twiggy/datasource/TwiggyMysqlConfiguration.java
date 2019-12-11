package com.jdkhome.twiggy.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.jdkhome.twiggy.generator.dao", sqlSessionTemplateRef = "twiggySqlSessionTemplate")
public class TwiggyMysqlConfiguration {

    @Value("${twiggy.mysql.url}")
    private String url;

    @Value("${twiggy.mysql.username}")
    private String username;

    @Value("${twiggy.mysql.password}")
    private String password;

    /**
     * 生成数据源.  @Primary 注解声明为默认数据源
     */
    @Bean(name = "twiggyDataSource")
    @Primary
    public DataSource twiggyDataSource() {
        HikariConfig conf = new HikariConfig();
        conf.setDriverClassName("com.mysql.cj.jdbc.Driver");
        conf.setJdbcUrl(url);
        conf.setUsername(username);
        conf.setPassword(password);

        return new HikariDataSource(conf);
    }

    /**
     * 创建 SqlSessionFactory
     */
    @Bean(name = "twiggySqlSessionFactory")
    @Primary
    public SqlSessionFactory twiggySqlSessionFactory(@Qualifier("twiggyDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return bean.getObject();
    }

    /**
     * 配置事务管理
     */
    @Bean(name = "twiggyTransactionManager")
    @Primary
    public DataSourceTransactionManager twiggyTransactionManager(@Qualifier("twiggyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "twiggySqlSessionTemplate")
    @Primary
    public SqlSessionTemplate twiggySqlSessionTemplate(@Qualifier("twiggySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}