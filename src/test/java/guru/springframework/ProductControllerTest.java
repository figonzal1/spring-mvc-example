package guru.springframework;

import guru.springframework.controllers.ProductController;
import guru.springframework.domain.Product;
import guru.springframework.services.ProductService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ProductControllerTest {

    @Mock //Mockito Mock object
    private ProductService productService;

    @InjectMocks //setups up controller, and injects mock objects into it.
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        //Iniciar controlador y mocks
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception {

        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());
        productList.add(new Product());

        //specific Mockito interaction, tell stub to return list of products
        when(productService.listAll()).thenReturn((List) productList); //need to strip generics to keep Mockito happy.

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/list"))
                .andExpect(model().attribute("products", hasSize(3)));

    }

    @Test
    public void testShow() throws Exception {
        Integer id = 1;

        //Cuando llama a getByid retorna un producto vacio
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/show/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("product/show"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testNew() throws Exception {

        Integer id = 1;

        verifyNoInteractions(productService);

        mockMvc.perform(get("/product/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productForm"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;

        //Tell Mockito stub to return new product for ID 1
        when(productService.getById(id)).thenReturn(new Product());

        mockMvc.perform(get("/product/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("product/productform"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {

        Integer id = 1;

        Product expected = new Product();
        expected.setId(id);
        expected.setDescription("Description");
        expected.setImageUrl("https://test.com");
        expected.setPrice(new BigDecimal("25.66"));

        when(productService.saveOrUpdate(any(Product.class))).thenReturn(expected);

        mockMvc.perform(post("/product")
                .param("id", String.valueOf(expected.getId()))
                .param("description", expected.getDescription())
                .param("imageUrl", expected.getImageUrl())
                .param("price", String.valueOf(expected.getPrice()))
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/show/" + id))
                .andExpect(model().attribute("product", instanceOf(Product.class)))
                .andExpect(model().attribute("product", hasProperty("id", is(id))))
                .andExpect(model().attribute("product", hasProperty("description", is(expected.getDescription()))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is(expected.getImageUrl()))))
                .andExpect(model().attribute("product", hasProperty("price", is(expected.getPrice()))));

        //verify properties of bound object (CAPTURAR POST)
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(expected.getDescription(), boundProduct.getValue().getDescription());
        assertEquals(expected.getPrice(), boundProduct.getValue().getPrice());
        assertEquals(expected.getImageUrl(), boundProduct.getValue().getImageUrl());
    }

    @Test
    public void testDelete() throws Exception {
        Integer id = 1;

        mockMvc.perform(get("/product/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));

        //Verificar que delete solo se llama 1 vez
        verify(productService, times(1)).delete(id);
    }
}
