package com.project.controller.business;

import com.project.payload.request.business.PublisherRequest;
import com.project.payload.response.business.PublisherResponse;
import com.project.payload.response.business.ResponseMessage;
import com.project.payload.response.user.UserResponse;
import com.project.service.business.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PublisherController {

    private final PublisherService publisherService;

    // getAllPublisher()******
    // http://localhost:8080/publishers?page=1&size=10&sort=name&type=asc
    @GetMapping("/publishers")
    public Page<List<PublisherResponse>> getAllPublisher(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "20") int size,
                                                         @RequestParam(value = "sort", defaultValue = "publisherName") String sort,
                                                         @RequestParam(value = "type", defaultValue = "asc") String type){

        return publisherService.getAllPublisher(page,size,sort,type);

    }

    // getPublisherById()******
    // http://localhost:8080/publishers/4
    @GetMapping("/publishers/{publisherId}")
    public ResponseMessage<PublisherResponse> getPublisherById(@PathVariable Long publisherId){

        return publisherService.getPublisherById(publisherId);

    }

    // createPublisher()****
    // http://localhost:8080/publishers
    @PostMapping("/publishers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<PublisherResponse> createPublisher(@RequestBody @Valid PublisherRequest request){

        return publisherService.createPublisher(request);
    }

    // updatePublisherById()****
    // http://localhost:8080/publishers/4
    @PutMapping("/publishers/{publisherId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<PublisherResponse> updatePublisherById(@PathVariable Long publisherId,
                                                                  @RequestBody @Valid PublisherRequest request){

        return publisherService.updatePublisherById(publisherId,request);

    }


    // deletePublisherById()*****
    // http://localhost:8080/publishers/4
    @DeleteMapping("/publishers/{publisherId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseMessage<PublisherResponse> deletePublisherById(@PathVariable Long publisherId){

        return publisherService.deletePublisherById(publisherId);
    }



}
