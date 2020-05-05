package com.github.radist_nt.iuliia.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator.OfInt;

class LetterIterator implements OfInt {

	private final String str;
	private final int len;
	private int pos;
	private int next;

	LetterIterator(String str, int len) {
		this.str = str;
		this.len = len;
		if (len == 0 || str.length() < len)
			throw new IllegalArgumentException("Invalid iteration length (must be 1 <= len <= str.length() = "
					+ str.length() + ", actual len = " + len + ")");
		pos = 1;
		next = str.charAt(0);
	}

	@Override
	public boolean hasNext() {
		return pos <= len;
	}

	@Override
	public int nextInt() {
		if (!hasNext())
			throw new NoSuchElementException();
		int result = next;
		next = pos < len ? str.charAt(pos) : (int) Character.MIN_VALUE;
		++pos;
		return result;
	}

	public char nextChar() {
		return (char) nextInt();
	}

	public char futherChar() {
		return hasNext() ? (char) next : Character.MIN_VALUE;
	}

}
