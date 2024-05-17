package poker.hands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Program {
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("./data/pokerHands.txt");
        Files.lines(path)
                .map(PokerHand::buildHand)
                .forEach(System.out::println);
    }
}
