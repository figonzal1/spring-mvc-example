package guru.springframework;

import guru.springframework.controllers.CustomerController;
import guru.springframework.domain.Customer;
import guru.springframework.domain.Product;
import guru.springframework.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {

    //Crear mock
    @Mock
    private CustomerService customerService;

    //Injectar mock en controlador
    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        //Iniciar mocks
        MockitoAnnotations.initMocks(this);


        //Construir el controlador para test
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }


    @Test
    public void testList() throws Exception {

        List<Customer> customerList = new ArrayList<>();
        customerList.add(new Customer());
        customerList.add(new Customer());
        customerList.add(new Customer());
        customerList.add(new Customer());

        when(customerService.listAll()).thenReturn((List) customerList);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/list"))
                .andExpect(model().attribute("customers", hasSize(4)));

    }

    @Test
    public void testShow() throws Exception {

        Integer id = 1;

        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/show/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/show")) //Nombre del html
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testNew() throws Exception {

        verifyNoInteractions(customerService);

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerForm"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testEdit() throws Exception {

        Integer id = 1;

        when(customerService.getById(id)).thenReturn(new Customer());

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerForm")) //HAcia archivo
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;

        Customer expected = new Customer();
        expected.setId(id);
        expected.setFirstName("Felipe");
        expected.setLastName("Gonzalez");
        expected.setCity("La Serena");
        expected.setZipCode("xxxx");
        expected.setState("IV");

        when(customerService.saveOrUpdate(any(Customer.class))).thenReturn(expected);

        mockMvc.perform(MockMvcRequestBuilders.post("/customer")
                .param("id", String.valueOf(expected.getId()))
                .param("firstName", expected.getFirstName())
                .param("lastName", expected.getLastName())
                .param("city", expected.getCity())
                .param("zipCode", expected.getZipCode())
                .param("state", expected.getState())
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/show/" + id)) //HACIA URL
                .andExpect(model().attribute("customer", hasProperty("firstName", is(expected.getFirstName()))))
                .andExpect(model().attribute("customer", hasProperty("lastName", is(expected.getLastName()))))
                .andExpect(model().attribute("customer", hasProperty("city", is(expected.getCity()))))
                .andExpect(model().attribute("customer", hasProperty("zipCode", is(expected.getZipCode()))))
                .andExpect(model().attribute("customer", hasProperty("state", is(expected.getState()))));

        //verify properties of bound object (CAPTURAR POST)
        ArgumentCaptor<Customer> boundProduct = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(boundProduct.capture());

        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(expected.getFirstName(), boundProduct.getValue().getFirstName());
        assertEquals(expected.getLastName(), boundProduct.getValue().getLastName());
        assertEquals(expected.getCity(), boundProduct.getValue().getCity());
        assertEquals(expected.getZipCode(), boundProduct.getValue().getZipCode());
        assertEquals(expected.getState(), boundProduct.getValue().getState());
    }

    @Test
    public void testDelete() throws Exception {

        Integer id = 1;

        mockMvc.perform(MockMvcRequestBuilders.get("/customer/delete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));

        //Verificar que delete solo se llama 1 vez
        verify(customerService, times(1)).delete(id);
    }

}
