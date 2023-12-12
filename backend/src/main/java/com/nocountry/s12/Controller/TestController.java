package com.nocountry.s12.Controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {
    
    @GetMapping()
    public String probando() {
        return "Hola Mundo";
    }
    
    @Secured("ARTISTA")
    @GetMapping("endpointArtista")
    public String endpointArtista() {
        return "Hola, soy un artista";
    }
    
    @Secured("PRODUCTOR")
    @GetMapping("endpointProductor")
    public String endpointProductor() {
        return "Hola, soy un Productor";
    }
    
    @Secured("OYENTE")
    @GetMapping("endpointOyente")
    public String endpointOyente() {
        return "Hola, soy un Oyente";
    }
    
}
