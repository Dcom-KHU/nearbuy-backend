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
@DiscriminatorColumn(name="type")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @Column(length=1024)
    private String detail;

    @Column(length=2048)
    private String image;

    @Column
    private Long time;

    @Column
    private Boolean ongoing;

    @Column(length=2048)
    private String tag;

    @Column
    private String location;

}