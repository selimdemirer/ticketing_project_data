package com.cydeo.entity;

import com.cydeo.enums.Status;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@Where(clause = "is_deleted=false") //for deleting operation
public class Task extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "assigned_employee_id")
    private User assignedEmployee;

    private String taskSubject;

    private String taskDetail;

    @Enumerated(EnumType.STRING)
    private Status taskStatus;

    @Column(columnDefinition = "DATE")
    private LocalDate assignedDate;

}
