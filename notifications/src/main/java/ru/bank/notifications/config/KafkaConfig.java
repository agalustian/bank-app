package ru.bank.notifications.config;

import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

  private static final Logger logger = LoggerFactory.getLogger(KafkaConfig.class);

  @Bean
  DefaultErrorHandler onError(KafkaTemplate kafkaTemplate) {
    ConsumerRecordRecoverer recordRecoverer = new DeadLetterPublishingRecoverer(
        kafkaTemplate,
        (record, e) -> {
          logger.error("Не удалось обработать сообщение из топика {}: {}", record.topic(), e.getMessage(), e);

          return new TopicPartition(record.topic() + ".errors", record.partition());
        }
    );
    BackOff backOff = new FixedBackOff(1000, 5);

    return new DefaultErrorHandler(recordRecoverer, backOff);
  }

}
