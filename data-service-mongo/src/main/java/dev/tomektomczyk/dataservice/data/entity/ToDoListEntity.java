package dev.tomektomczyk.dataservice.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "todolist")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ToDoListEntity {
    @Id
    private String id;
    private String name;
    private List<String> taskIds;
}
