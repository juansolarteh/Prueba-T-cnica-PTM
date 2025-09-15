package ptm.pruebaTecnica.infrastructure.httpController;

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
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})

@AutoConfigureMockMvc
class GetAllIntegrationTest {
    private final String URL = "/productos";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductoJpaRepository productoJpaRepository;

    @Test
    void shouldReturnAllProductosSuccessfully() throws Exception {
        ProductoDatabaseEntity producto = new ProductoDatabaseEntity(1L, "Nombre", "Descripción", new BigDecimal("100.0"), 10);
        when(productoJpaRepository.findAll()).thenReturn(List.of(producto));

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Nombre"))
                .andExpect(jsonPath("$[0].descripcion").value("Descripción"))
                .andExpect(jsonPath("$[0].precio").value(100.0))
                .andExpect(jsonPath("$[0].cantidadStock").value(10))
                .andExpect(jsonPath("$[0].valorInventario").value(1000.0));
    }
}