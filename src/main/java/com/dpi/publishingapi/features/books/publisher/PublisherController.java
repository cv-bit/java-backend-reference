package com.dpi.publishingapi.features.books.publisher;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.books.publisher.get_all_publishers.GetAllPublishersRequest;
import com.dpi.publishingapi.features.books.publisher.get_all_publishers.GetAllPublishersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final Pipeline pipeline;

    @Autowired
    public PublisherController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping
    public GetAllPublishersResponse getAllPublishers() {
        return pipeline.send(new GetAllPublishersRequest());
    }
}
