package dev.tomektomczyk.dataservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "todolist")
@Data
@AllArgsConstructor
public class ToDoList {
    @Id
    @Field(name = "id")
    private String id;
    @Field(name = "name")
    private String name;
}
