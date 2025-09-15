package ptm.pruebaTecnica.infrastructure.httpController;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ptm.pruebaTecnica.infrastructure.httpController.DTOs.CommandProductoDTO;
import ptm.pruebaTecnica.infrastructure.persistence.ProductoJpaRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@AutoConfigureMockMvc
class CreateIntegrationTest {
    private final String URL = "/productos";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductoJpaRepository productoJpaRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void shouldReturnBadRequestWithBadParams() throws Exception {
        CommandProductoDTO request = new CommandProductoDTO("", "", -1.0, -5);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(4));
    }

    @Test
    void shouldReturnProductoWhenCreateSuccessfully() throws Exception {
        CommandProductoDTO request = new CommandProductoDTO("Nombre", "Descripción", 100.0, 10);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Nombre"))
                .andExpect(jsonPath("$.descripcion").value("Descripción"))
                .andExpect(jsonPath("$.precio").value(100.0))
                .andExpect(jsonPath("$.cantidadStock").value(10))
                .andExpect(jsonPath("$.valorInventario").value(1000.0));
    }
}