package poker.hands;

import java.util.Arrays;
import java.util.function.Supplier;

public enum Suit {
    Clubs("C"),
    Spades("S"),
    Diamonds("D"),
    Hearts("H");

    private final String symbol;

    Suit(String symbol) {
        this.symbol = symbol;
    }

    public static Suit convert(String symbol) {
        return Arrays.stream(values())
                .filter(r -> r.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(buildError(symbol));
    }

    @Override
    public String toString() {
        return symbol;
    }

    private static Supplier<RuntimeException> buildError(String symbol) {
        return () -> new IllegalStateException("Unknown suit " + symbol);
    }
}
