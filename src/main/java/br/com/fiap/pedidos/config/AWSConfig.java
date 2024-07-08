package br.com.fiap.pedidos.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.credentials.client-id}")
    private String awsClientId;

    @Value("${aws.credentials.client-secret}")
    private String awsClientSecret;

    public AWSCredentials credentials() {
        return new BasicAWSCredentials(awsClientId, awsClientSecret);
    }

    @Bean
    public AmazonSQS amazonSQS() {
        return AmazonSQSClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(credentials()))
                .build();
    }

}
