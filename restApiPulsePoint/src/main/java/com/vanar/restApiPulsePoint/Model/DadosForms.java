package com.vanar.restApiPulsePoint.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_triagem_forms")
@EqualsAndHashCode(of = "id")
public class DadosForms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false,length = 10)
    private int age;
    @NotBlank
    @Column(nullable = false,length = 10)
    private Float weight;
    @NotBlank
    @Column(nullable = false,length = 10)
    private Float height;
    @NotBlank
    @Column(nullable = false,length = 5)
    private String bloodType;

}
