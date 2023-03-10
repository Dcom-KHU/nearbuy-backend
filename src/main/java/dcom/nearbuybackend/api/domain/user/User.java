package dcom.nearbuybackend.api.domain.user;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private Double mannerPoint;

    @Column(length = 2048)
    private String image;

    @Column
    private String location;

    @Column
    private Boolean social;

    @Column(length = 1024)
    private String refreshToken;
}
