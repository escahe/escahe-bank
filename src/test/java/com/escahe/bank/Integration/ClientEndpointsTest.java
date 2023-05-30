package com.escahe.bank.Integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;
import com.escahe.bank.model.Client;
import com.escahe.bank.repository.ClientRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ClientEndpointsTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ClientRepository clientRepository;


    @BeforeEach
    public void createSamples() {
        clientRepository.save(
            new Client(
                1L,
                "Homero Simpson",
                "masculino",
                40,
                "1111111111",
                "Avenida siempre viva 123",
                "3335555677",
                "flanders",
                true
            )
        );
        clientRepository.save(
            new Client(
                2L,
                "Marge Simpson",
                "femenino",
                40,
                "2222222222",
                "Avenida siempre viva 123",
                "3335555677",
                "flanders",
                true
            )
        );
    }

    @Test
    public void createNewClient() throws Exception {
        mockMvc.perform(
            post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"name\": \"Fernando Fernandez Florez\",\r\n    \"genre\": \"masculino\",\r\n    \"age\": 28,\r\n    \"dni\": \"5555555555\",\r\n    \"address\": \"Avenida siempre viva 555\",\r\n    \"phoneNumber\": \"5555555555\",\r\n    \"password\": \"123\",\r\n    \"status\": true\r\n}")
        ).andExpect(status().isCreated());
    }

    @Test
    public void createClientWithDniInUse() throws Exception {
        mockMvc.perform(
            post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"name\": \"Fernando Fernandez Florez\",\r\n    \"genre\": \"masculino\",\r\n    \"age\": 28,\r\n    \"dni\": \"1111111111\",\r\n    \"address\": \"Avenida siempre viva 555\",\r\n    \"phoneNumber\": \"5555555555\",\r\n    \"password\": \"123\",\r\n    \"status\": true\r\n}")
        ).andExpect(status().isConflict());
    }
    @Test
    public void createClientWithWrongNameFormat() throws Exception {
        mockMvc.perform(
            post("/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"name\": \"Fernand@ Fern@ndez Florez\",\r\n    \"genre\": \"masculino\",\r\n    \"age\": 28,\r\n    \"dni\": \"1111111111\",\r\n    \"address\": \"Avenida siempre viva 555\",\r\n    \"phoneNumber\": \"5555555555\",\r\n    \"password\": \"123\",\r\n    \"status\": true\r\n}")
        ).andExpect(status().isBadRequest());
    }

    @Test
    public void updateClientName() throws Exception {
        mockMvc.perform(
            patch("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"name\": \"Fernando Fernández Flores\"\r\n}")
        ).andExpect(status().isOk());
    }

    @Test
    public void updatingClientDniWithATakenNumber() throws Exception {
        mockMvc.perform(
            patch("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"dni\": \"2222222222\"\r\n}")
        ).andExpect(status().isConflict());
    }

    @Test
    public void updateClient() throws Exception {
        mockMvc.perform(
            put("/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"name\": \"Alicia Actualizada All\",\r\n    \"genre\": \"Femenino\",\r\n    \"age\": 24,\r\n    \"dni\": \"1111111112\",\r\n    \"address\": \"Avenida siempre viva 111\",\r\n    \"phoneNumber\": \"5555555555\",\r\n    \"password\": \"123\",\r\n    \"status\": true\r\n}")
        ).andExpect(status().isOk());
    }

    @Test
    public void updateInexistentClient() throws Exception {
        mockMvc.perform(
            put("/clientes/100")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\r\n    \"name\": \"Alicia Actualizada All\",\r\n    \"genre\": \"Femenino\",\r\n    \"age\": 24,\r\n    \"dni\": \"1111111112\",\r\n    \"address\": \"Avenida siempre viva 111\",\r\n    \"phoneNumber\": \"5555555555\",\r\n    \"password\": \"123\",\r\n    \"status\": true\r\n}")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void getAllClients() throws Exception {
        mockMvc.perform(
            get("/clientes")
        ).andExpect(status().isOk());
    }

    @Test
    public void getClientById() throws Exception {
        mockMvc.perform(
            get("/clientes/1")
        ).andExpect(status().isOk());
    }
    @Test
    public void getInexistentClientById() throws Exception {
        mockMvc.perform(
            get("/clientes/100")
        ).andExpect(status().isNotFound());
    }

    @Test
    public void deleteClient() throws Exception {
        mockMvc.perform(
            delete("/clientes/1")
        ).andExpect(status().isOk());
    }
    @Test
    public void deleteAnInexistentClient() throws Exception {
        mockMvc.perform(
            delete("/clientes/100")
        ).andExpect(status().isNotFound());
    }
}
