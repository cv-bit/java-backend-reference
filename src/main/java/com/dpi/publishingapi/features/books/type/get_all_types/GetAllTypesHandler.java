package com.dpi.publishingapi.features.books.type.get_all_types;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.type.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetAllTypesHandler implements Command.Handler<GetAllTypesRequest, GetAllTypesResponse> {

    private final TypeRepository typeRepository;

    @Autowired
    public GetAllTypesHandler(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Override
    public GetAllTypesResponse handle(GetAllTypesRequest getAllTypesRequest) {
        return new GetAllTypesResponse(typeRepository.findAll());
    }
}
