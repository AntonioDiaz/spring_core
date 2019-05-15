package com.adiaz.springmvc.controllers;

import com.adiaz.springmvc.domain.Product;
import com.adiaz.springmvc.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Profile("jpadao")
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testList() throws Exception {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.listAll()).thenReturn(products);
        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("product_list"))
                .andExpect(model().attribute("list", hasSize(2)));
    }

    @Test
    public void testShow() throws Exception {
        when(productService.getById(1)).thenReturn(new Product());
        mockMvc.perform(get("/product/show/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("product_view"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testCreate() throws Exception {
        verifyZeroInteractions(productService);
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("product_new"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        String description = "my product";
        BigDecimal price = new BigDecimal("33.00");
        String imageUrl = "http://image.com/3333";
        Product p = new Product();
        p.setId(id);
        p.setDescription(description);
        p.setPrice(price);
        p.setImageUrl(imageUrl);
        when(productService.saveOrUpdate(Matchers.<Product>any())).thenReturn(p);
        mockMvc.perform(
                post("/product/doCreate")
                    .param("id","1")
                    .param("description",description)
                    .param("price","33.00")
                    .param("imageUrl", "http://image.com/3333"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/show/1"))
                .andExpect(model().attribute("product", instanceOf(Product.class)))
                .andExpect(model().attribute("product", hasProperty("id", is(id))))
                .andExpect(model().attribute("product", hasProperty("description", is(description))))
                .andExpect(model().attribute("product", hasProperty("price", is(price))))
                .andExpect(model().attribute("product", hasProperty("imageUrl", is(imageUrl))));

        //verify properties of bound object
        ArgumentCaptor<Product> boundProduct = ArgumentCaptor.forClass(Product.class);
        verify(productService).saveOrUpdate(boundProduct.capture());
        assertEquals(id, boundProduct.getValue().getId());
        assertEquals(description, boundProduct.getValue().getDescription());
        assertEquals(price, boundProduct.getValue().getPrice());
        assertEquals(imageUrl, boundProduct.getValue().getImageUrl());
    }

    @Test
    public void testDelete() throws Exception{
        Integer id = 1;
        mockMvc.perform(get("/product/doDelete/" + id))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/product/list"));
        verify(productService, times(1)).delete(id);
    }

    @Test
    public void testUpdate() throws Exception {
        Integer id = 1;
        when(productService.getById(1)).thenReturn(new Product());
        mockMvc.perform(get("/product/update/" + id))
                .andExpect(status().isOk())
                .andExpect(view().name("product_update"))
                .andExpect(model().attribute("product", instanceOf(Product.class)));

        ArgumentCaptor<Integer> boundProduct = ArgumentCaptor.forClass(Integer.class);
        verify(productService).getById(boundProduct.capture());
        assertEquals(id, boundProduct.getValue());
    }

}
