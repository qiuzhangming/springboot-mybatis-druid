package com.zzdz;

import com.zzdz.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.amqp.core.Binding.DestinationType.QUEUE;

/**
 * @Classname RabbitMQTest
 * @Description TODO
 * @Date 2019/7/9 13:12
 * @Created by joe
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMQTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Test
    public void testAmqpAdmin() {

        // 声明一个exchange
        amqpAdmin.declareExchange(new FanoutExchange("amqpadmin.fanout.exchange"));

        // 声明一个队列
        amqpAdmin.declareQueue(new Queue("amqpadmin.queue", true));

        // 声明绑定规则
        // destination:目的地
        // destinationType:目的地类型
        // exchange: 交换器
        // routingKey: 路邮件
        // arguments
        amqpAdmin.declareBinding(new Binding("amqpadmin.queue", QUEUE, "amqpadmin.fanout.exchange", "amqpadmin.fanout", null));
    }

    @Test
    public void test01() {
        rabbitTemplate.convertAndSend("amq.direct","test", new Employee("joe", 33));
    }

    @Test
    public void test02() {
        Object test = rabbitTemplate.receiveAndConvert("test");
        System.out.println(test.getClass());
        System.out.println(test);
    }
}
