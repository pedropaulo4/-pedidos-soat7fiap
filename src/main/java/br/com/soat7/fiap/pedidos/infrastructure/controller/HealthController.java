package br.com.soat7.fiap.pedidos.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("rest/api/v1/health")
public class HealthController {

    @GetMapping("status")
    public ResponseEntity<Map<String, String>> readiness(){
        return ResponseEntity.ok(Map.of("status", "ok"));
    }
}
