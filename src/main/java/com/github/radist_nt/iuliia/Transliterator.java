package com.github.radist_nt.iuliia;

import javax.annotation.Nonnull;

/**
 * Transliterator with pre-configured schema. The implementation is thread-safe.
 *
 * @see Iuliia#withSchema(String)
 * 
 * @author radist-nt
 *
 */
public interface Transliterator {

	/**
	 * Name of transliteration schema
	 * 
	 * @return pre-configured schema name
	 */
	public @Nonnull String schemaName();

	/**
	 * Transliterate method. Using pre-configured transliteration schema.
	 * 
	 * @param text text for transliteration
	 * @return transliterated text
	 */
	public @Nonnull String transliterate(@Nonnull String text);

	/**
	 * Synonym for {@link #transliterate(String)}.
	 * 
	 * @see #transliterate(String)
	 * 
	 * @param text text for transliteration
	 * @return transliterated text
	 */
	public default @Nonnull String translate(@Nonnull String text) {
		return transliterate(text);
	}

}
