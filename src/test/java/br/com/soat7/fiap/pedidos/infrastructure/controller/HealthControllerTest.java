package br.com.soat7.fiap.pedidos.infrastructure.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HealthControllerTest {


    @Test
    void readiness_deveRetornarOk() {
        HealthController healthController = new HealthController(); // Instanciar o controller diretamente
        ResponseEntity<Map<String, String>> response = healthController.readiness();
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("ok", response.getBody().get("status"));

    }
}