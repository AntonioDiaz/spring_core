package com.adiaz.springmvc.controllers;

import com.adiaz.springmvc.domain.Customer;
import com.adiaz.springmvc.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void listTest() throws Exception {
        List<Customer> customerList = Arrays.asList(new Customer(), new Customer());
        when(customerService.listAll()).thenReturn(customerList);
        mockMvc.perform(get("/customer/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_list"))
                .andExpect(model().attribute("list", hasSize(2)));
    }

    @Test
    public void showTest() throws Exception {
        Integer id = 1;
        when(customerService.getById(id)).thenReturn(new Customer());
        mockMvc.perform(get("/customer/show/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_view"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void createTest() throws Exception {
        verifyZeroInteractions(customerService);
        mockMvc.perform(get("/customer/create/"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_new"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));
    }

    @Test
    public void doCreateTest() throws Exception {
        Integer id = 1;
        String firstName = "pepe";
        String secondName = "perez";
        Customer customer = new Customer();
        customer.setId(id);
        customer.setFirstName(firstName);
        customer.setSecondName(secondName);
        when(customerService.createOrUpdate(any())).thenReturn(customer);
        mockMvc.perform(
                post("/customer/doCreate")
                    .param("id", "1")
                        .param("firstName", firstName)
                        .param("secondName", secondName))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/" + id))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andExpect(model().attribute("customer", hasProperty("id", is(1))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(firstName))))
                .andExpect(model().attribute("customer", hasProperty("secondName", is(secondName))));
        //verify properties of bound object
        ArgumentCaptor<Customer> boundProduct = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).createOrUpdate(boundProduct.capture());
        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(firstName, boundProduct.getValue().getFirstName());
        assertEquals(secondName, boundProduct.getValue().getSecondName());
    }

    @Test
    public void updateTest() throws Exception {
        Integer id = 1;
        when(customerService.getById(1)).thenReturn(new Customer());
        mockMvc.perform(get("/customer/update/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("customer_update"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)));

        ArgumentCaptor<Integer> boundProduct = ArgumentCaptor.forClass(Integer.class);
        verify(customerService).getById(boundProduct.capture());
        assertEquals(id, boundProduct.getValue());
    }

    @Test
    public void testDelete() throws Exception{
        Integer id = 1;
        mockMvc.perform(get("/customer/doDelete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/list"));
        verify(customerService, times(1)).delete(id);
    }


}
