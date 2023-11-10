package com.project.service.business;

import com.project.entity.business.Book;
import com.project.entity.business.Publisher;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.mapper.PublisherMapper;
import com.project.payload.message.ErrorMessages;
import com.project.payload.message.SuccessMessages;
import com.project.payload.request.business.PublisherRequest;
import com.project.payload.response.business.BookResponse;
import com.project.payload.response.business.PublisherResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.repository.business.PublisherRepository;
import com.project.service.helper.PageableHelper;
import com.project.service.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    private final PageableHelper pageableHelper;

    private final PublisherMapper publisherMapper;

    private final Util util;

    private final Book book;

    // getAllPublisher()******
    public Page<List<PublisherResponse>> getAllPublisher(int page, int size, String sort, String type) {

        Pageable pageable = pageableHelper.getPageableWithProperties(page, size, sort, type);

        List<PublisherResponse> publisherResponseList = publisherRepository.findAll()
                .stream()
                .map(publisherMapper::mapPublisherToPublisherResponse)
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), publisherResponseList.size());

        List<PublisherResponse> content = publisherResponseList.subList(start, end);

        return new PageImpl<>(List.of(content), pageable, publisherResponseList.size());
    }


    // getPublisherById()******
    public ResponseMessage<PublisherResponse> getPublisherById(Long publisherId) {

        Publisher publisher = util.isPublisherExistById(publisherId);

        PublisherResponse response = publisherMapper.mapPublisherToPublisherResponse(publisher);

        return ResponseMessage.<PublisherResponse>builder()
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }



    // createPublisher()****
    public ResponseMessage<PublisherResponse> createPublisher(PublisherRequest request) {

        Publisher publisher = publisherMapper.mapPublisherRequestToPublisher(request);

        Publisher savedPublisher = publisherRepository.save(publisher);

        PublisherResponse response = publisherMapper.mapPublisherToPublisherResponse(savedPublisher);

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_SAVED_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }

    // updatePublisherById()****
    public ResponseMessage<PublisherResponse> updatePublisherById(Long publisherId,
                                                                  PublisherRequest request) {

        // id kontrolü
        util.isPublisherExistById(publisherId);

        // Request DTO --> POJO dönüşümü
        Publisher publisher = publisherMapper.mapPublisherRequestToPublisher(request);

        // DB değişlikleri kaydet.
        Publisher savedPublisher = publisherRepository.save(publisher);

        PublisherResponse response = publisherMapper.mapPublisherToPublisherResponse(savedPublisher);

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_UPDATE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();
    }


    // deletePublisherById()*****
    public ResponseMessage<PublisherResponse> deletePublisherById(Long publisherId) {

        //id kontrolü
        Publisher publisher = util.isPublisherExistById(publisherId);

        // Yayıncının kitaplarının olup olmadığı
        hasRelatedBooks(publisherId);

        // Silme işlemi
        publisherRepository.deleteById(publisherId);

        // POJO --> DTO Dönüşümü
        PublisherResponse response = publisherMapper.mapPublisherToPublisherResponse(publisher);

        return ResponseMessage.<PublisherResponse>builder()
                .message(SuccessMessages.PUBLISHER_DELETE_MESSAGE)
                .httpStatus(HttpStatus.OK)
                .object(response)
                .build();

    }


    // Helper Method
    private boolean hasRelatedBooks(Long publisherId) {

        if(book.getPublisher().getId().equals(publisherId)){
            throw new ResourceNotFoundException(ErrorMessages.NOT_DELETED_PUBLISHER_BY_ID);
        }

        return true;
    }




}
