package dcom.nearbuybackend.api.domain.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="chat")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    private String id;

    private Integer room;

    private Integer post;

    private String sender;

    private List<String> userList;

    private String message;

    private Long time;

    private Boolean last;
}

