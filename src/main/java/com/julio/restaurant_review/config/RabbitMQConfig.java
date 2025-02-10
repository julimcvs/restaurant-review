package com.julio.restaurant_review.config;

import com.julio.restaurant_review.model.dto.RabbitBindingConfig;
import com.julio.restaurant_review.model.dto.RabbitQueueConfig;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "rabbit")
public class RabbitMQConfig {
    private List<String> exchanges = new ArrayList<>();
    private List<RabbitQueueConfig> queues = new ArrayList<>();
    private List<RabbitBindingConfig> bindings = new ArrayList<>();

    @Bean
    public HashMap<String, Exchange> createExchanges(AmqpAdmin amqpAdmin) {
        HashMap<String, Exchange> exchangeMap = new HashMap<>();
        for (String exchangeName : exchanges) {
            Exchange exchange = ExchangeBuilder.directExchange(exchangeName).durable(true).build();
            amqpAdmin.declareExchange(exchange);
            exchangeMap.put(exchangeName, exchange);
        }
        return exchangeMap;
    }

    @Bean
    public HashMap<String, Queue> createQueues(AmqpAdmin amqpAdmin) {
        HashMap<String, Queue> queueMap = new HashMap<>();
        for (RabbitQueueConfig queue : queues) {
            Map<String, Object> arguments = new HashMap<>();
            if (queue.ttl() != null && queue.ttl() > 0)
                arguments.put("x-message-ttl", queue.ttl());
            if (queue.dlx() != null && !queue.dlx().isEmpty())
                arguments.put("x-dead-letter-exchange", queue.dlx());
            if (queue.dlqRoutingKey() != null && !queue.dlqRoutingKey().isEmpty())
                arguments.put("x-dead-letter-routing-key", queue.dlqRoutingKey());

            Queue q = QueueBuilder.durable(queue.name()).withArguments(arguments).build();
            amqpAdmin.declareQueue(q);
            queueMap.put(queue.name(), q);
        }
        return queueMap;
    }

    @Bean
    public List<Binding> createBindings(AmqpAdmin amqpAdmin, HashMap<String, Exchange> exchangeMap, HashMap<String, Queue> queueMap) {
        List<Binding> bindingList = new ArrayList<>();
        for (RabbitBindingConfig bindingConfig : bindings) {
            Binding binding = BindingBuilder.bind(queueMap.get(bindingConfig.queue()))
                    .to(exchangeMap.get(bindingConfig.exchange()))
                    .with(bindingConfig.routingKey()).noargs();
            amqpAdmin.declareBinding(binding);
            bindingList.add(binding);
        }
        return bindingList;
    }

    public List<String> getExchanges() {
        return exchanges;
    }

    public void setExchanges(List<String> exchanges) {
        this.exchanges = exchanges;
    }

    public List<RabbitQueueConfig> getQueues() {
        return queues;
    }

    public void setQueues(List<RabbitQueueConfig> queues) {
        this.queues = queues;
    }

    public List<RabbitBindingConfig> getBindings() {
        return bindings;
    }

    public void setBindings(List<RabbitBindingConfig> bindings) {
        this.bindings = bindings;
    }
}
