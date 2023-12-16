package com.chervonnaya.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO extends BaseDTO{
    private String description;
    private String status;
}
