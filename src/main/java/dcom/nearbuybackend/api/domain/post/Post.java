package dcom.nearbuybackend.api.domain.post;

import dcom.nearbuybackend.api.domain.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorColumn(name="D_TYPE")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String type;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column
    private String detail;

    @Column
    private String image;

    @Column
    private Long time;

    @Column
    private Boolean ongoing;

    @Column
    private String tag;

    @Column
    private String location;

}