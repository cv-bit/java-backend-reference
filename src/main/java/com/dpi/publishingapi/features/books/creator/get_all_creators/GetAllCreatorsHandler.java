package com.dpi.publishingapi.features.books.creator.get_all_creators;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.creator.CreatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllCreatorsHandler implements Command.Handler<GetAllCreatorsRequest, GetAllCreatorsResponse> {

    private final CreatorRepository creatorRepository;

    @Autowired
    public GetAllCreatorsHandler(CreatorRepository creatorRepository) {
        this.creatorRepository = creatorRepository;
    }

    @Override
    public GetAllCreatorsResponse handle(GetAllCreatorsRequest getAllCreatorsRequest) {
        return new GetAllCreatorsResponse(creatorRepository.findAll());
    }
}
