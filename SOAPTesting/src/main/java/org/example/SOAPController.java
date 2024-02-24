package org.example;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/test")
@RestController
public class SOAPController {

    private final SOAPService service;
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public Envelope getData(){
        return service.convertTOXML();
    }

}
