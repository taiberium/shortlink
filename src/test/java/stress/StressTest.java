package stress;


import com.shortlink.core.CoreApplication;
import org.jsmart.zerocode.core.domain.LoadWith;
import org.jsmart.zerocode.core.domain.TestMapping;
import org.jsmart.zerocode.core.domain.TestMappings;
import org.jsmart.zerocode.jupiter.extension.ParallelLoadExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.SpringApplication;
import stress.requests.TinyUrlRequest;


@LoadWith("load_config.properties")
@ExtendWith({ParallelLoadExtension.class})
public class StressTest {

    static {
        SpringApplication.run(CoreApplication.class); //disable if you start app on another pc
    }

    @Test
    @TestMappings({
            @TestMapping(testClass = TinyUrlRequest.class, testMethod = "should_makeGrpcTinyUrlRequest_andGetResultSuccessfully")
    })
    public void stressTest() {
    }
}
