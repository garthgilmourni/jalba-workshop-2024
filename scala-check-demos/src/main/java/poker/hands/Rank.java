package poker.hands;

import java.util.Arrays;
import java.util.function.Supplier;

public enum Rank {
    Ace("A"),
    King("K"),
    Queen("Q"),
    Jack("J"),
    Ten("10"),
    Nine("9"),
    Eight("8"),
    Seven("7"),
    Six("6"),
    Five("5"),
    Four("4"),
    Three("3"),
    Two("2");

    private final String symbol;

    Rank(String symbol) {
        this.symbol = symbol;
    }

    public static Rank convert(String symbol) {
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
        return () -> new IllegalStateException("Unknown rank " + symbol);
    }
}
