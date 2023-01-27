package dcom.nearbuybackend.api.domain.post;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("free")
public class FreePost extends Post {

    @Column
    private String emptyField;
}
