package com.dpi.publishingapi.features.books.creator;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.books.creator.get_all_creators.GetAllCreatorsRequest;
import com.dpi.publishingapi.features.books.creator.get_all_creators.GetAllCreatorsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/creators")
public class CreatorController {

    private final Pipeline pipeline;

    @Autowired
    public CreatorController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping
    public GetAllCreatorsResponse getAllCreators() {
        return pipeline.send(new GetAllCreatorsRequest());
    }
}
