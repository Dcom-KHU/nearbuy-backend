package dcom.nearbuybackend.api.domain.post;

import lombok.*;

import javax.persistence.*;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("exchange")
public class ExchangePost extends Post {

    @Column
    private String target;
}

