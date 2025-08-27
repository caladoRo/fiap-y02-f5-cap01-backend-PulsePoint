package com.vanar.restApiPulsePoint.Service;

import com.vanar.restApiPulsePoint.DTO.TriageFormsDTO;
import com.vanar.restApiPulsePoint.Model.TriageForms;
import com.vanar.restApiPulsePoint.Repository.TriagemFormsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TriageFormsService {

    @Autowired
    private TriagemFormsRepository repository;

    @Transactional(readOnly = true)
    public List<TriageFormsDTO> getAll() {
        return repository.findAll().stream().map(TriageFormsDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public TriageFormsDTO getById(Long id) {
        TriageForms entity = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Id não encontrado" + id)
        );
        return new TriageFormsDTO(entity);
    }

    @Transactional
    public TriageFormsDTO save(TriageFormsDTO dto){
        TriageForms entity = new TriageForms();
        copyDtoToEntity(dto,entity);
        entity = repository.save(entity);
        return new TriageFormsDTO(entity);
    }

    public void copyDtoToEntity(TriageFormsDTO dto, TriageForms entity){
        entity.setId(dto.getId());
        entity.setSintomas(dto.getSintomas());
    }

}
