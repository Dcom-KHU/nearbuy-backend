package dcom.nearbuybackend.api.domain.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="chat")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    private String _id;

    private Integer room;

    private List<String> user;

    private String sender;

    private String message;

    private Long time;
}

