package com.dpi.publishingapi.features.books.type;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.books.type.get_all_types.GetAllTypesRequest;
import com.dpi.publishingapi.features.books.type.get_all_types.GetAllTypesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TypeController {
    private final Pipeline pipeline;

    @Autowired
    public TypeController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping
    public GetAllTypesResponse getAllTypes() {
        return pipeline.send(new GetAllTypesRequest());
    }
}
