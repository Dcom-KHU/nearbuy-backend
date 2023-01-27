package dcom.nearbuybackend.api.domain.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column
    private String emotion;

    @Column
    private Boolean reply;

    @Column
    private Boolean location;

    @Column
    private Boolean time;

    @Column
    private Boolean manner;

    @Column
    private String detail;
}
