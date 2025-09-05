package mutsa.server.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    @JoinColumn(name = "userId", nullable = false)
    private Users userId;

    //날짜
    @Column(name="date",nullable = false,updatable = false)
    private LocalDate date;

    //이미지
    @Column(name="picture")
    private String picture;

    @Column(name="memo")
    private String memo;

    //달성률
//    @Column(name="achievementRate",nullable=false)
//    private Integer achievementRate;
}
