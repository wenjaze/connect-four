package connectfour.javafx.utils.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PlayerData {

    private String playerName1;
    private String playerName2;

    private String winner;

}
