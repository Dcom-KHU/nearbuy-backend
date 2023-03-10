package dcom.nearbuybackend.api.domain.post;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column
    private String type;

    @Column
    private String detail;
}

