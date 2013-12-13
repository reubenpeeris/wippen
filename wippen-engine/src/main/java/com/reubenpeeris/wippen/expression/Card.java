package com.reubenpeeris.wippen.expression;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import com.reubenpeeris.wippen.engine.WippenIllegalFormatException;

@EqualsAndHashCode(callSuper = false, doNotUseGetters = true)
@Getter
public final class Card extends Pile {
	private static final Pattern CARD_PATTERN = Pattern.compile("^([1-9]|1[0-3])([CDHS])$");
	private final Suit suit;
	private final Rank rank;

	public Card(@NonNull Suit suit, @NonNull Rank rank) {

		this.suit = suit;
		this.rank = rank;
	}

	public static Card parseCard(@NonNull String string) {
		Matcher matcher = CARD_PATTERN.matcher(string);

		if (!matcher.matches()) {
			throw new WippenIllegalFormatException("Invalid card format in '" + string + "'");
		}

		Rank rank = new Rank(Integer.parseInt(matcher.group(1)));
		String suit = matcher.group(2);

		return new Card(Suit.fromLetter(suit), rank);
	}

	@Override
	public Collection<Card> getCards() {
		return Collections.singleton(this);
	}

	@Override
	public String toString() {
		return rank.toString() + suit.toString();
	}

	@Override
	public Collection<Pile> getPiles() {
		return Collections.<Pile> singleton(this);
	}

	@Override
	public int getValue() {
		return rank.getValue();
	}
}
