package connectfour.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * A POJO class, uses Builder design patter and is a persistence entity.
 * Used for storing data.
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class GameResult {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String player1;

    @Column(nullable = false)
    private String player2;

    @Column(nullable = false)
    private String winner;

    @Column(nullable = false)
    private int circlesPlaced;

    @Column(nullable = false)
    private Duration duration;

    @Column(nullable = false)
    private ZonedDateTime created;

    @PrePersist
    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}
