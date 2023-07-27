package com.luchkovskiy.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@EqualsAndHashCode(exclude = {
        "employee", "project"
})
@Entity
@Table(name = "l_employees_projects")
public class EmployeeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonBackReference
    @ToString.Exclude
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    @ToString.Exclude
    private Project project;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

}
