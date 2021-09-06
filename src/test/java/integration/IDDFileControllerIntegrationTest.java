package integration;

import com.cacf.cdt.bffclone.CdtBffCloneApplication;
import com.cacf.cdt.bffclone.dto.common.page.PageResponseDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileSummaryDTO;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = CdtBffCloneApplication.class)
@AutoConfigureWebTestClient
@DisplayName("API files")
class IDDFileControllerIntegrationTest {

    @Autowired
    WebTestClient webClient;

    @Test
    void findAll() {
        val page = webClient.get()
                .uri("/files")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<PageResponseDTO<IDDFileSummaryDTO>>() {
                })
                .returnResult().getResponseBody();
        assertThat(page).isNotNull();
        assertThat(page.getContent()).isEmpty();
    }

}
