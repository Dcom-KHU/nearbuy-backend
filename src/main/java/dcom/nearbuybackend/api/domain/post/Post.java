package dcom.nearbuybackend.api.domain.post;

import dcom.nearbuybackend.api.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public List<String> getImageList() {
        if (image == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(image.split(",")));
    }

    public String getThumbnailImage() {
        if (image == null) {
            return null;
        }
        return image.split(",")[0];
    }
}