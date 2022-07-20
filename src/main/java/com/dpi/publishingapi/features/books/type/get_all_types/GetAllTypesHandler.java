package com.dpi.publishingapi.features.books.type.get_all_types;

import an.awesome.pipelinr.Command;
import com.dpi.publishingapi.data.books.type.Type;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class GetAllTypesHandler implements Command.Handler<GetAllTypesRequest, GetAllTypesResponse> {
    @Override
    public GetAllTypesResponse handle(GetAllTypesRequest getAllTypesRequest) {
        return new GetAllTypesResponse(Arrays.stream(Type.values()).toList());
    }
}
