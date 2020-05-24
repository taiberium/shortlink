package stress.requests;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.assertj.core.api.Assertions;
import org.example.tinurl.backend.GetRequest;
import org.example.tinurl.backend.GetResponse;
import org.example.tinurl.backend.TinyUrlServiceGrpc;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


public class TinyUrlRequest {

    @Test
    public void should_makeGrpcTinyUrlRequest_andGetResultSuccessfully() throws InterruptedException {
        String ipAddr = "localhost";
        int port = 6565;
        final ManagedChannel channel = ManagedChannelBuilder.forAddress(ipAddr, port).usePlaintext().build();
        final TinyUrlServiceGrpc.TinyUrlServiceBlockingStub stub = TinyUrlServiceGrpc.newBlockingStub(channel);

        String validAddr = "http://mail.ru";
        final GetRequest getRequest = GetRequest.newBuilder().setOriginalUrl(validAddr).build();
        GetResponse getResponse = stub.get(getRequest);
        channel.shutdown();
        channel.awaitTermination(2, TimeUnit.SECONDS);

        Assertions.assertThat(getResponse).isNotNull();
    }
}