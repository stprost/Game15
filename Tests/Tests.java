import Game15.Mybot;
import org.junit.Test;

public class Tests {

    Mybot mybot;

    @Test
    public void WinRate() {
        int quantityOfGames = 10000;
        int quantityOfWins = 0;
        mybot = new Mybot();
        for (int i = 0; i < quantityOfGames; i++) {
            mybot.bot();
            if (mybot.isSolved()) quantityOfWins++;
            mybot.reload();
        }
        System.out.print(quantityOfWins / quantityOfGames * 100 + " % Правильных решений ");
    }

}
