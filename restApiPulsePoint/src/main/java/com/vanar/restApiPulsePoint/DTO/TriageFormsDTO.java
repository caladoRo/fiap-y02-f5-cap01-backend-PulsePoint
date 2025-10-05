package com.vanar.restApiPulsePoint.DTO;

import com.vanar.restApiPulsePoint.Model.TriageForms;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TriageFormsDTO {

    private Long id;

    @NotBlank(message = "O campo não deve estar nulo")
    @Size(min = 10, message = "O campo deve ter no mínimo 10 caracteres")
    private String sintomas;

    public TriageFormsDTO(TriageForms entity){
        id = entity.getId();
        sintomas = entity.getSintomas();
    }
}
