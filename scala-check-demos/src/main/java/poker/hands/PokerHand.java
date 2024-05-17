package poker.hands;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PokerHand {
    public static PokerHand buildHand(String hand) {
        String[] words = hand.split("[ ]+");
        Card[] cards = Arrays.stream(words)
                .map(Card::buildCard)
                .toArray(Card[]::new);
        return new PokerHand(cards);
    }

    private class Pair {
        Pair(Rank first, Rank second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return first == pair.first &&
                    second == pair.second;
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        private final Rank first;
        private final Rank second;
    }

    private Card[] cards;
    private String name;

    private PokerHand(Card[] cards) {
        this.cards = cards;
    }

    public String name() {
        if (isRoyalFlush()) {
            return "Royal Flush";
        }
        if (isStraightFlush()) {
            return "Straight Flush";
        }
        if (isFlush()) {
            return "Flush";
        }
        if (isStraight()) {
            return "Straight";
        }
        if (isFourOfAKind()) {
            return "Four Of A Kind";
        }
        if (isFullHouse()) {
            return "Full House";
        }
        if (isThreeOfAKind()) {
            return "Three Of A Kind";
        }
        if (isTwoPair()) {
            return "Two Pair";
        }
        if (isPair()) {
            return "Pair";
        }
        return "Highest Card";
    }

    public boolean isPair() {
        return applyPredicateToRanks(this::twoOf);
    }

    public boolean isTwoPair() {
        Stream<Pair> combinations = generatePairsOfRanks();
        return combinations.filter(pair -> twoOf(pair.first) && twoOf(pair.second))
                .count() == 2;
    }

    public boolean isFullHouse() {
        return generatePairsOfRanks()
                .filter(this::twoThenThree)
                .count() == 1;
    }

    private boolean twoThenThree(Pair pair) {
        return twoOf(pair.first) && threeOf(pair.second);
    }

    private Stream<Pair> generatePairsOfRanks() {
        Set<Pair> pairsOfRanks = new HashSet<>();
        for (Rank r1 : Rank.values()) {
            for (Rank r2 : Rank.values()) {
                if (r1 != r2) {
                    pairsOfRanks.add(new Pair(r1, r2));
                }
            }
        }
        return pairsOfRanks.stream();
    }

    public boolean isThreeOfAKind() {
        return applyPredicateToRanks(this::threeOf);
    }

    public boolean isFourOfAKind() {
        return applyPredicateToRanks(this::fourOf);
    }

    private boolean applyPredicateToRanks(Predicate<Rank> test) {
        return Stream.of(Rank.values())
                .filter(test)
                .count() >= 1;
    }

    public boolean isFlush() {
        return allOfSameSuit();
    }

    public boolean isRoyalFlush() {
        return allOfSameSuit() && allCardsHigh();
    }

    public boolean isStraightFlush() {
        return allOfSameSuit() && isStraight();
    }

    public boolean isStraight() {
        Rank[] ranks = Arrays.stream(cards)
                .map(Card::getRank)
                .sorted((a, b) -> indexOf(a) > indexOf(b) ? 1 : -1)
                .toArray(Rank[]::new);

        String ranksAsString = Arrays.stream(ranks)
                .map(Rank::toString)
                .collect(Collectors.joining(""));
        String allRanksAsString = Arrays.stream(Rank.values())
                .map(Rank::toString)
                .collect(Collectors.joining(""));

        return allRanksAsString.contains(ranksAsString);
    }

    private int indexOf(Rank item) {
        Rank[] allRanks = Rank.values();
        for (int x = 0; x < allRanks.length; x++) {
            if (allRanks[x] == item) {
                return x;
            }
        }
        throw new IllegalStateException("Cant identify a rank");
    }

    private boolean allCardsHigh() {
        List<Rank> highCards = Arrays.asList(Rank.Ace, Rank.Queen, Rank.King, Rank.Jack, Rank.Ten);
        return Arrays.stream(cards)
                .allMatch(c -> highCards.stream().anyMatch(r -> c.getRank() == r));
    }

    private boolean allOfSameSuit() {
        Suit sampleSuit = cards[0].getSuit();
        return Arrays.stream(cards)
                .allMatch(c -> c.getSuit() == sampleSuit);
    }

    private boolean nOf(int count, Rank rank) {
        long actualCount = Arrays.stream(cards)
                .filter(c -> c.getRank() == rank)
                .count();
        return actualCount >= count;
    }

    private boolean twoOf(Rank rank) {
        return nOf(2, rank);
    }

    private boolean threeOf(Rank rank) {
        return nOf(3, rank);
    }

    private boolean fourOf(Rank rank) {
        return nOf(4, rank);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : cards) {
            builder.append(card);
            builder.append(" ");
        }
        builder.append("= ");
        builder.append(name());
        return builder.toString();
    }

}
