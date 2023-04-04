package dcom.nearbuybackend.api.domain.chat;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

    private String sender;

    private String receiver;

    private String message;

    private Long time;

    private Boolean last;

    private Boolean senderIn;

    private Boolean receiverIn;
}

