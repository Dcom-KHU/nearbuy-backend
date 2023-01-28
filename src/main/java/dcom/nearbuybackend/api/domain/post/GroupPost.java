package dcom.nearbuybackend.api.domain.post;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("group")
public class GroupPost extends Post {

    @Column
    private Integer groupPrice;

    @Column
    private String distribute;

    @Column
    private Long day;
}

