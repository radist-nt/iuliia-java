package com.github.radist_nt.iuliia.impl;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import com.github.radist_nt.iuliia.Transliterator;

class TransliteratorImpl implements Transliterator {

	private static final Pattern WORD_PATTERN = Pattern.compile(
			"(?<=^|[^\\p{IsAlphabetic}])\\p{IsAlphabetic}+(?=[^\\p{IsAlphabetic}]|$)", Pattern.CASE_INSENSITIVE);

	private final Schema schema;

	TransliteratorImpl(Schema schema) {
		this.schema = schema;
	}

	Schema getSchema() {
		return schema;
	}

	@Override
	public @Nonnull String schemaName() {
		return schema.getName();
	}

	@Override
	public @Nonnull String transliterate(@Nonnull String str) {
		Objects.requireNonNull(str);
		Matcher matcher = WORD_PATTERN.matcher(str);
		StringBuffer result = new StringBuffer(str.length() + str.length() / 3);
		while (matcher.find()) {
			matcher.appendReplacement(result, transliterateWord(matcher.group()));
		}
		matcher.appendTail(result);
		return result.toString();
	}

	private String transliterateWord(String str) {
		StringBuilder result = new StringBuilder(str.length() + str.length() / 2);

		int mainLen = str.length();
		String ending = "";
		if (mainLen > 2) {
			ending = schema.getEndingMapping().getOrDefault(str.substring(mainLen - 2), ending);
			if (!ending.isEmpty())
				mainLen -= 2;
		}
		LetterIterator it = new LetterIterator(str, mainLen);
		for (char prev = Character.MIN_VALUE, curr, next; it.hasNext(); prev = curr) {
			curr = it.nextChar();
			next = it.futherChar();
			result.append(transliterateLetter(prev, curr, next));
		}
		result.append(ending);

		return result.toString();
	}

	private String transliterateLetter(char prev, char curr, char next) {
		String letter = schema.getPrevMapping()
				.get(prev == Character.MIN_VALUE ? Character.toString(curr) : "" + prev + curr);
		if (letter == null) {
			letter = schema.getNextMapping().get("" + curr + next);
			if (letter == null) {
				letter = Character.toString(curr);
				letter = schema.getMapping().getOrDefault(letter, letter);
			}
		}
		return letter;
	}

}
