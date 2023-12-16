package com.chervonnaya.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task", schema = "todo")
public class Task extends BaseEntity{

    @Column(name = "description")
    private String description;
    @Enumerated(value = EnumType.ORDINAL)
    private Status status;

}
