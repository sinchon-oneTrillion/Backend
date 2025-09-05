package mutsa.server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "Calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    //FK->Users(id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users userId;

    //날짜
    @Column(name="date", updatable = false)
    private LocalDate date;

    @Column(name="picture")
    private String picture;

    @Column(name="memo")
    private String memo;
}
