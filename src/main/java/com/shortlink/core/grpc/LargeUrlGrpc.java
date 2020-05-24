package com.shortlink.core.grpc;

import com.shortlink.core.model.exception.GrpcInvalidArgumentException;
import com.shortlink.core.service.TinyUrlService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.example.tinurl.backend.LargeUrlDto;
import org.example.tinurl.backend.LargeUrlServiceGrpc;
import org.example.tinurl.backend.MiniUrlDto;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
@RequiredArgsConstructor
public class LargeUrlGrpc extends LargeUrlServiceGrpc.LargeUrlServiceImplBase {

    private final TinyUrlService tinyUrlService;

    @Override
    public void getLargeUrl(MiniUrlDto request, StreamObserver<LargeUrlDto> responseObserver) {
        String originalUrl = request.getMinifiedUrl();
        String tinyUrl = tinyUrlService.findExistedUrl(originalUrl)
                .orElseThrow(() -> new GrpcInvalidArgumentException("Url not found"));

        responseObserver.onNext(mapToResponse(tinyUrl));
        responseObserver.onCompleted();
    }

    private LargeUrlDto mapToResponse(String minifiedUrl) {
        return LargeUrlDto.newBuilder().setOriginalUrl(minifiedUrl).build();
    }
}
