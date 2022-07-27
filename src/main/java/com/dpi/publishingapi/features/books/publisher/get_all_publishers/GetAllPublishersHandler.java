package com.dpi.publishingapi.features.books.publisher.get_all_publishers;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.publisher.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllPublishersHandler implements Command.Handler<GetAllPublishersRequest, GetAllPublishersResponse> {

    private final PublisherRepository publisherRepository;

    @Autowired
    public GetAllPublishersHandler(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    @Override
    public GetAllPublishersResponse handle(GetAllPublishersRequest getAllPublishersRequest) {
        return new GetAllPublishersResponse(publisherRepository.findDistinctByName());
    }
}
