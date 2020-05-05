package com.github.radist_nt.iuliia.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.testng.annotations.Test;

import com.github.radist_nt.iuliia.impl.LetterIterator;

public class TestIterator {

	private void testIt(LetterIterator it) {
		assertTrue(it.hasNext());
		assertEquals('0', it.futherChar());
		assertTrue(it.hasNext());
		assertEquals('0', it.nextChar());
		assertTrue(it.hasNext());
		assertEquals('1', it.futherChar());
		assertEquals('1', it.futherChar());
		assertTrue(it.hasNext());
		assertEquals('1', it.nextChar());
		assertTrue(it.hasNext());
		assertEquals('2', it.futherChar());
		assertEquals('2', it.futherChar());
		assertTrue(it.hasNext());
		assertEquals('2', it.nextChar());
		assertFalse(it.hasNext());
		assertEquals(Character.MIN_VALUE, it.futherChar());
		assertFalse(it.hasNext());
		assertEquals(Character.MIN_VALUE, it.futherChar());
		try {
			it.nextChar();
			assertTrue(false);
		} catch (NoSuchElementException e) {
			// passed
		}
	}

	@Test(groups = "init")
	public void testIterator() {
		testIt(new LetterIterator("0123456789ABCD", 3));
		testIt(new LetterIterator("012", 3));
	}

	@Test(groups = "init")
	public void testExtreme() {
		LetterIterator it = new LetterIterator("0", 1);
		assertTrue(it.hasNext());
		assertEquals('0', it.futherChar());
		assertTrue(it.hasNext());
		assertEquals('0', it.nextChar());
		assertFalse(it.hasNext());
		assertEquals(Character.MIN_VALUE, it.futherChar());
	}

	@Test(groups = "init")
	public void testInvalid() {
		try {
			new LetterIterator("123", 4);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			// passed
		}
		try {
			new LetterIterator("123", 0);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			// passed
		}
	}

}
