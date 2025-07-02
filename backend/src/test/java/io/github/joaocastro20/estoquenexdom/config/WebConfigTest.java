package io.github.joaocastro20.estoquenexdom.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void devePermitirCorsParaOrigemPermitida() throws Exception {
        mockMvc.perform(options("/api/algum-recurso")
                        .header("Origin", "https://meusite.com")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isOk())
                .andExpect(header().string("Access-Control-Allow-Origin", "https://meusite.com"))
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE"))
                .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }

    @Test
    void deveNegarCorsParaOrigemNaoPermitida() throws Exception {
        mockMvc.perform(options("/api/algum-recurso")
                        .header("Origin", "https://origem-nao-permitida.com")
                        .header("Access-Control-Request-Method", "POST"))
                .andExpect(status().isForbidden())
                .andExpect(header().doesNotExist("Access-Control-Allow-Origin"));
    }
}
