package com.shortlink.core.grpc;

import com.shortlink.core.model.exception.GrpcInvalidArgumentException;
import com.shortlink.core.service.TinyUrlService;
import io.grpc.internal.testing.StreamRecorder;
import org.example.tinurl.backend.GetRequest;
import org.example.tinurl.backend.GetResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TinyUrlGrpcTest {

    private final TinyUrlService tinyUrlServiceMock = Mockito.mock(TinyUrlService.class);
    private final TinyUrlGrpc tinyUrlGrpc = new TinyUrlGrpc(tinyUrlServiceMock);

    @Test
    void should_ReturnTinyUrl_IfFullUrlValid() {
        String fullUrl = "https://mail.ru/find_a_cookie";
        String tinyUrl = "urkTime";
        Mockito.when(tinyUrlServiceMock.findTinyUrl(fullUrl)).thenReturn(tinyUrl);
        GetRequest request = GetRequest.newBuilder().setOriginalUrl(fullUrl).build();
        StreamRecorder<GetResponse> responseObserver = StreamRecorder.create();

        tinyUrlGrpc.get(request, responseObserver);

        assertThat(responseObserver.getError()).isNull();
        List<GetResponse> results = responseObserver.getValues();
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getMinifiedUrl()).isEqualTo(tinyUrl);
    }

    @Test
    void should_ReturnError_IfFullUrlInValid() {
        String fullUrl = "Arra!";

        GetRequest request = GetRequest.newBuilder().setOriginalUrl(fullUrl).build();
        StreamRecorder<GetResponse> responseObserver = StreamRecorder.create();

        GrpcInvalidArgumentException exception = Assertions.assertThrows(
                GrpcInvalidArgumentException.class,
                () -> tinyUrlGrpc.get(request, responseObserver)
        );

        assertThat(exception).isNotNull();
    }
}
