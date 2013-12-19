package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import com.reubenpeeris.wippen.engine.WippenIllegalFormatException;

@Getter
@EqualsAndHashCode(callSuper = false, of = { "suit", "rank" })
public final class Card extends Pile {
	private static final Pattern CARD_PATTERN = Pattern.compile("^([1-9]|1[0-3])([CDHS])$");
	private final Suit suit;
	private final Rank rank;
	private final Collection<Card> cards = Collections.singleton(this);
	private final Collection<Pile> piles = Collections.<Pile> singleton(this);

	public Card(@NonNull Rank rank, @NonNull Suit suit) {
		this.suit = suit;
		this.rank = rank;
	}

	static Card parseCard(@NonNull String string) {
		Matcher matcher = CARD_PATTERN.matcher(string);

		if (!matcher.matches()) {
			throw new WippenIllegalFormatException("Invalid card format '" + string + "'");
		}

		Rank rank = Rank.fromInt(Integer.parseInt(matcher.group(1)));
		String suit = matcher.group(2);

		return new Card(rank, Suit.fromLetter(suit));
	}

	@Override
	public int getValue() {
		return rank.getValue();
	}

	@Override
	public String toString() {
		return rank.toString() + suit.toString();
	}
}
