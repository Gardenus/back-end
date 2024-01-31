package com.ssginc.secretgarden.global.util;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.io.InputStream;
import java.net.URL;


@Component
public class S3Uploader {
    private final AmazonS3 s3Client;

    public S3Uploader(@Value("${aws.accessKey}") String accessKey,
                      @Value("${aws.secretKey}") String secretKey) {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.AP_NORTHEAST_2)
                .build();
    }

    public void uploadImageToS3(String imageUrl, String bucketName, String keyName) {
        try {
            URL url = new URL(imageUrl);
            try (InputStream inputStream = url.openStream()) {
                ObjectMetadata metadata = new ObjectMetadata();
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, inputStream, metadata);
                s3Client.putObject(putObjectRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}