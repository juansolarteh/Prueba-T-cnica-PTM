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
import ptm.pruebaTecnica.infrastructure.persistence.ProductoDatabaseEntity;
import ptm.pruebaTecnica.infrastructure.persistence.ProductoJpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
@AutoConfigureMockMvc
class DeleteIntegrationTest {
    private final String URL = "/productos";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductoJpaRepository productoJpaRepository;
    @Test
    void shouldReturnBadRequestWithBadPathVariable() throws Exception {
        mockMvc.perform(delete(URL + "/0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFoundWhenProductoNotExists() throws Exception {
        when(productoJpaRepository.findById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(delete(URL + "/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNoCOntentWhenUpdateSuccessfully() throws Exception {
        ProductoDatabaseEntity producto = new ProductoDatabaseEntity(1L, "NombreOr", "DescripciónOr", new BigDecimal("50.0"), 10);
        when(productoJpaRepository.findById(anyLong())).thenReturn(Optional.of(producto));

        mockMvc.perform(delete(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}