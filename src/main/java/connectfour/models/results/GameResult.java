package connectfour.models.results;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Slf4j
@Entity
public class GameResult {

    @Id
    @GeneratedValue
    private Long id;


    @Column(nullable = false)
    private String player;

    private String winner;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private ZonedDateTime finished;

    @PrePersist
    protected void onPersist() {
        finished = ZonedDateTime.now();
    }
}
