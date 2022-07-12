package com.dpi.publishingapi.features.books.language;

import an.awesome.pipelinr.Pipeline;
import com.dpi.publishingapi.features.books.language.get_all_languages.GetAllLanguagesRequest;
import com.dpi.publishingapi.features.books.language.get_all_languages.GetAllLanguagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/languages")
public class LanguageController {

    private final Pipeline pipeline;

    @Autowired
    public LanguageController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping
    public GetAllLanguagesResponse getAllBooks() {
        return pipeline.send(new GetAllLanguagesRequest());
    }
}
