package dcom.nearbuybackend.api.domain.post;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("auction")
public class AuctionPost extends Post {

    @Column
    private Integer startPrice;

    @Column
    private Integer increasePrice;

    @Column
    private Integer currentPrice;

    @Column
    private Long deadline;
}

