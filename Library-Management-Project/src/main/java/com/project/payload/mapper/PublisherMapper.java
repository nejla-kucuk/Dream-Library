package com.project.payload.mapper;

import com.project.entity.business.Publisher;
import com.project.payload.request.business.PublisherRequest;
import com.project.payload.response.business.PublisherResponse;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    // POJO --> Response DTO
    public PublisherResponse mapPublisherToPublisherResponse(Publisher publisher){

        return PublisherResponse.builder()
                .id(publisher.getId())
                .publisherName(publisher.getPublisherName())
                .builtIn(publisher.getBuiltIn())
                .build();

    }

    // Request DTO --> POJO
    public Publisher mapPublisherRequestToPublisher(PublisherRequest request){

        return Publisher.builder()
                .publisherName(request.getPublisherName())
                .builtIn(request.getBuiltIn())
                .build();

    }

    // Request DTO --> POJO
    public Publisher mapPublisherRequestToUpdatedPublisher(Long publisherId, PublisherRequest request) {

        return mapPublisherRequestToPublisher(request)
                .toBuilder()
                .id(publisherId)
                .build();
    }
}
