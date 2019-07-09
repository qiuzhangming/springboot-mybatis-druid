package com.zzdz.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname RabbitMQConfig
 * @Description TODO
 * @Date 2019/7/9 13:24
 * @Created by joe
 */
@Configuration
public class RabbitMQConfig {

    /**
     * org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration
     * 自动配置类中生成默认的 RabbitTemplate 默认使用 private MessageConverter messageConverter = new SimpleMessageConverter();
     *
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
