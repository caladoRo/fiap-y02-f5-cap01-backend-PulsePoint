package com.vanar.restApiPulsePoint.Controller;

import com.vanar.restApiPulsePoint.DTO.TriageFormsDTO;
import com.vanar.restApiPulsePoint.Service.TriageFormsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/triage")
public class TriageFormsController {

    @Autowired
    private TriageFormsService service;

    @GetMapping
    public ResponseEntity<List<TriageFormsDTO>> findAll(){
        List<TriageFormsDTO> dto = service.getAll();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriageFormsDTO> findById(@PathVariable Long id){
        TriageFormsDTO dto = service.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TriageFormsDTO> insert(@RequestBody @Valid TriageFormsDTO dto){
        dto = service.save(dto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);

    }

}
