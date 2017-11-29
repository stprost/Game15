import Game15.Graph;
import Game15.Mybot;
import org.junit.Test;

public class Tests {


    @Test
    public void WinRate() {
        int quantityOfGames = 1000;
        int quantityOfWins = 0;
        Mybot mybot = new Mybot();
        for (int i = 0; i < quantityOfGames; i++) {
            mybot.bot();
            if (mybot.isSolved()) quantityOfWins++;
            mybot.reload();
        }
        System.out.print(quantityOfWins / quantityOfGames * 100 + " % Правильных решений ");
    }

}
