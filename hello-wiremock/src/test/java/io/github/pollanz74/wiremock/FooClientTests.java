package io.github.pollanz74.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@WireMockTest(httpPort = 8080)
class FooClientTests {

    private final FooClient fooClient = new FooClient("http://localhost:8080");

    private static final String STATIC_JSON_RESPONSE = "{\n" +
            "\"author\": \"Yours Truly\",\n" +
            "\"date\": \"date of publication\",\n" +
            "\"slides\": null,\n" +
            "\"title\": \"Sample Slide Show\"\n" +
            "}";

    @Test
    void callFooServiceShouldGetOk() {
        stubFor(get("/json")
                .withHeader("accept", equalTo("application/json"))
                .willReturn(WireMock.okJson(STATIC_JSON_RESPONSE)));

        Map<String, Object> response = fooClient.callFooService();

        Assertions.assertThat(response)
                .isNotEmpty()
                .containsEntry("author", "Yours Truly")
                .containsEntry("date", "date of publication")
                .containsEntry("slides", null)
                .containsEntry("title", "Sample Slide Show");

    }

}
