
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.http.MediaType;

import com.cargosmart.snakebox.controller.NewsControllerTest;

import java.nio.charset.Charset;


@RunWith(Suite.class)
@Suite.SuiteClasses({
        NewsControllerTest.class
})
public class TestAll {

    public static MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
}