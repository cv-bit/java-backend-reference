package com.dpi.publishingapi.features.books.language.get_all_languages;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.language.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllLanguagesHandler implements Command.Handler<GetAllLanguagesRequest, GetAllLanguagesResponse> {

    private final LanguageRepository languageRepository;

    @Autowired
    public GetAllLanguagesHandler(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public GetAllLanguagesResponse handle(GetAllLanguagesRequest getAllLanguagesRequest) {
        return new GetAllLanguagesResponse(languageRepository.findAll());
    }
}
