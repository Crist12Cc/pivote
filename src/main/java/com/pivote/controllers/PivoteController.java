package com.pivote.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pivote")
public class PivoteController {

    @PostMapping
    public ResponseEntity<?> pivote(){
        return ResponseEntity.ok().build();
    }

}
