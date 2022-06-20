package com.example.challengegrupowl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "usuarios")
@Table(schema = "wlgroup")
public class users {

    @Id
    @Min(value = 11, message = "Insira um cpf valido")
    @Max(value = 11, message = "Insira um cpf valido")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cpf;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @UniqueElements
    @Column(nullable = false, unique = true)
    private String item;
}
