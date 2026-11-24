package tn.epi.orders;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void createValidOrderReturns201() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\":\"Clavier\",\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.product").value("Clavier"));
    }

    @Test
    void createdOrderCanBeRead() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\":\"Ecran\",\"quantity\":1}"))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/orders/1")).andExpect(status().isOk());
    }

    @Test
    void invalidQuantityIsRejected() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\":\"Clavier\",\"quantity\":0}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tooLargeQuantityIsRejected() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\":\"Clavier\",\"quantity\":500}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void maliciousProductNameIsRejected() throws Exception {
        mvc.perform(post("/api/orders").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\":\"<script>alert(1)</script>\",\"quantity\":1}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void unknownOrderReturns404() throws Exception {
        mvc.perform(get("/api/orders/999")).andExpect(status().isNotFound());
    }

    @Test
    void listReturns200() throws Exception {
        mvc.perform(get("/api/orders")).andExpect(status().isOk());
    }
}