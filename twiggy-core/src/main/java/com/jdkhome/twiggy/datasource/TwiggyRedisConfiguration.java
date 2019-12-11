package com.jdkhome.twiggy.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;


/**
 * create by linkji.
 * create at 15:15 2019-12-10
 */
@Configuration
public class TwiggyRedisConfiguration {

    @Value("${twiggy.redis.host}")
    private String host;

    @Value("${twiggy.redis.port}")
    private int port;

    @Value("${twiggy.redis.password}")
    private String password;

    @Value("${twiggy.redis.database}")
    private int database;

    /**
     * 配置redis连接工厂
     */
    @Bean
    public RedisConnectionFactory defaultRedisConnectionFactory() {

        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
        conf.setHostName(host);
        conf.setPort(port);
        conf.setPassword(password);
        conf.setDatabase(database);

        return new LettuceConnectionFactory(conf);
    }

    /**
     * 配置redisTemplate 注入方式使用@Resource(name="") 方式注入
     */
    @Bean(name = "twiggyRedisTemplate")
    public StringRedisTemplate twiggyRedisTemplate() {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(defaultRedisConnectionFactory());
        return template;
    }

}
