package com.shortlink.core.grpc;

import com.shortlink.core.model.exception.GrpcInvalidArgumentException;
import com.shortlink.core.service.TinyUrlService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.example.tinurl.backend.GetRequest;
import org.example.tinurl.backend.GetResponse;
import org.example.tinurl.backend.TinyUrlServiceGrpc;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
@RequiredArgsConstructor
public class TinyUrlGrpc extends TinyUrlServiceGrpc.TinyUrlServiceImplBase {

    private final TinyUrlService tinyUrlService;

    @Override
    public void get(GetRequest request, StreamObserver<GetResponse> responseObserver) {
        String originalUrl = request.getOriginalUrl();
        boolean isValidUrl = UrlValidator.getInstance().isValid(originalUrl);
        if (!isValidUrl) throw new GrpcInvalidArgumentException("Not valid url!");

        String tinyUrl = tinyUrlService.findTinyUrl(originalUrl);

        responseObserver.onNext(mapToResponse(tinyUrl));
        responseObserver.onCompleted();
    }

    private GetResponse mapToResponse(String minifiedUrl) {
        return GetResponse.newBuilder().setMinifiedUrl(minifiedUrl).build();
    }
}
