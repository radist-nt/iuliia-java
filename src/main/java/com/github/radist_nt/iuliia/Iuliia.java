package com.github.radist_nt.iuliia;

import java.util.Collection;

import javax.annotation.Nonnull;

import com.github.radist_nt.iuliia.impl.TransliteratorBuilder;

/**
 * Transliterate Cyrillic → Latin in every possible way.
 * 
 * @author radist-nt
 *
 */
public class Iuliia {

	private Iuliia() {
	}

	/**
	 * Transliterate method. Loads schema and transliterate according to it. Schema
	 * is not cached and reloads on each call.
	 * 
	 * @param text   text for transliteration
	 * @param schema transliteration schema
	 * @return transliterated text
	 */
	public static @Nonnull String transliterate(@Nonnull String text, @Nonnull String schema) {
		return withSchema(schema).transliterate(text);
	}

	/**
	 * Factory method for {@link Transliterator}.
	 * 
	 * @param schema transliteration schema
	 * @return Transliterator with pre-configured and loaded schema
	 */
	public static @Nonnull Transliterator withSchema(@Nonnull String schema) {
		return TransliteratorBuilder.build(schema);
	}

	/**
	 * List of transliteration schemas supported by library
	 * 
	 * @param includeAliases whether to include schema aliases
	 * @return collection of supported transliterations schemas
	 */
	public static @Nonnull Collection<String> supportedSchemas(boolean includeAliases) {
		return includeAliases ? TransliteratorBuilder.schemasAll() : TransliteratorBuilder.schemas();
	}

	/**
	 * Synonym for {@link #transliterate(String, String)}.
	 * 
	 * @see #transliterate(String, String)
	 * 
	 * @param text   text for transliteration
	 * @param schema transliteration schema
	 * @return transliterated text
	 */
	public static @Nonnull String translate(@Nonnull String text, @Nonnull String schema) {
		return transliterate(text, schema);
	}

	// GENERATED CONSTANTS (DO NOT EDIT MANUALLY)

	/**
	 * ALA-LC transliteration schema.<br>
	 * https://dangry.ru/iuliia/ala-lc/<br>
	 * <b>Name</b>: {@code ala_lc}
	 */
	public static final String ALA_LC = "ala_lc";

	/**
	 * ALA-LC transliteration schema.<br>
	 * https://dangry.ru/iuliia/ala-lc/<br>
	 * <b>Name</b>: {@code ala_lc_alt}
	 */
	public static final String ALA_LC_ALT = "ala_lc_alt";

	/**
	 * BGN/PCGN transliteration schema.<br>
	 * https://dangry.ru/iuliia/bgn-pcgn/<br>
	 * <b>Name</b>: {@code bgn_pcgn}
	 */
	public static final String BGN_PCGN = "bgn_pcgn";

	/**
	 * BGN/PCGN transliteration schema.<br>
	 * https://dangry.ru/iuliia/bgn-pcgn/<br>
	 * <b>Name</b>: {@code bgn_pcgn_alt}
	 */
	public static final String BGN_PCGN_ALT = "bgn_pcgn_alt";

	/**
	 * British Standard 2979:1958 transliteration schema.<br>
	 * https://dangry.ru/iuliia/bs-2979/<br>
	 * This schema defines two alternative translations for `Ы`:<br>
	 * - `Ы` → `Ȳ` (used by the Oxford University Press)<br>
	 * - `Ы` → `UI` (used by the British Library).<br>
	 * `iuliia` uses `Ы` → `Ȳ`.<br>
	 * <b>Name</b>: {@code bs_2979}
	 */
	public static final String BS_2979 = "bs_2979";

	/**
	 * British Standard 2979:1958 transliteration schema.<br>
	 * https://dangry.ru/iuliia/bs-2979/<br>
	 * <b>Name</b>: {@code bs_2979_alt}
	 */
	public static final String BS_2979_ALT = "bs_2979_alt";

	/**
	 * GOST 16876-71 (aka GOST 1983) transliteration schema.<br>
	 * https://dangry.ru/iuliia/gost-16876/<br>
	 * <b>Name</b>: {@code gost_16876}
	 */
	public static final String GOST_16876 = "gost_16876";

	/**
	 * GOST 16876-71 (aka GOST 1983) transliteration schema.<br>
	 * https://dangry.ru/iuliia/gost-16876/<br>
	 * <b>Name</b>: {@code gost_16876_alt}
	 */
	public static final String GOST_16876_ALT = "gost_16876_alt";

	/**
	 * GOST R 52290-2004 transliteration schema.<br>
	 * https://dangry.ru/iuliia/gost-52290/<br>
	 * <b>Name</b>: {@code gost_52290}
	 */
	public static final String GOST_52290 = "gost_52290";

	/**
	 * GOST R 52535.1-2006 transliteration schema.<br>
	 * http://localhost:3000/iuliia/gost-52535/<br>
	 * <b>Name</b>: {@code gost_52535}
	 */
	public static final String GOST_52535 = "gost_52535";

	/**
	 * GOST R 7.0.34-2014 transliteration schema.<br>
	 * http://localhost:3000/iuliia/gost-7034/<br>
	 * This schema defines alternatives for many letters, but does not specify when
	 * to use which:<br>
	 * е → e (ye)<br>
	 * ё → yo (jo)<br>
	 * й → j (i,y)<br>
	 * х → x (kh)<br>
	 * ц → c (tz,cz)<br>
	 * ъ → '' (empty)<br>
	 * ь → ' (empty)<br>
	 * ю → yu (ju)<br>
	 * я → ya (ja)<br>
	 * `iuliia` uses the first of suggested translations for each such letter.<br>
	 * <b>Name</b>: {@code gost_7034}
	 */
	public static final String GOST_7034 = "gost_7034";

	/**
	 * GOST 7.79-2000 (aka ISO 9:1995) transliteration schema.<br>
	 * https://dangry.ru/iuliia/gost-779/<br>
	 * <b>Name</b>: {@code gost_779}<br>
	 * <b>Aliases</b>: {@code iso_9_1995}
	 */
	public static final String GOST_779 = "gost_779";

	/**
	 * GOST 7.79-2000 (aka ISO 9:1995) transliteration schema.<br>
	 * https://dangry.ru/iuliia/gost-779/<br>
	 * <b>Name</b>: {@code gost_779_alt}
	 */
	public static final String GOST_779_ALT = "gost_779_alt";

	/**
	 * ICAO DOC 9303 transliteration schema.<br>
	 * https://dangry.ru/iuliia/icao-doc-9303/<br>
	 * <b>Name</b>: {@code icao_doc_9303}
	 */
	public static final String ICAO_DOC_9303 = "icao_doc_9303";

	/**
	 * ISO/R 9:1954 transliteration schema.<br>
	 * https://dangry.ru/iuliia/iso-9-1954/<br>
	 * <b>Name</b>: {@code iso_9_1954}
	 */
	public static final String ISO_9_1954 = "iso_9_1954";

	/**
	 * ISO/R 9:1968 transliteration schema.<br>
	 * https://dangry.ru/iuliia/iso-9-1968/<br>
	 * <b>Name</b>: {@code iso_9_1968}
	 */
	public static final String ISO_9_1968 = "iso_9_1968";

	/**
	 * ISO/R 9:1968 transliteration schema.<br>
	 * https://dangry.ru/iuliia/iso-9-1968/<br>
	 * <b>Name</b>: {@code iso_9_1968_alt}
	 */
	public static final String ISO_9_1968_ALT = "iso_9_1968_alt";

	/**
	 * Moscow Metro map transliteration schema.<br>
	 * https://dangry.ru/iuliia/mosmetro/<br>
	 * <b>Name</b>: {@code mosmetro}
	 */
	public static final String MOSMETRO = "mosmetro";

	/**
	 * MVD 310-1997 transliteration schema.<br>
	 * https://dangry.ru/iuliia/mvd-310/<br>
	 * <b>Name</b>: {@code mvd_310}
	 */
	public static final String MVD_310 = "mvd_310";

	/**
	 * MVD 310-1997 transliteration schema.<br>
	 * https://dangry.ru/iuliia/mvd-310/<br>
	 * This schema defines the following rule for the French mapping:<br>
	 * &gt; `С` between two vowels → `SS`<br>
	 * There is no such rule in other schemas, and MVD-310 itself is deprecated,<br>
	 * so I decided to ignore this specific rule for the sake of code
	 * simplicity.<br>
	 * <b>Name</b>: {@code mvd_310_fr}
	 */
	public static final String MVD_310_FR = "mvd_310_fr";

	/**
	 * MVD 782-2000 transliteration schema.<br>
	 * https://dangry.ru/iuliia/mvd-782/<br>
	 * <b>Name</b>: {@code mvd_782}
	 */
	public static final String MVD_782 = "mvd_782";

	/**
	 * Scientific transliteration schema.<br>
	 * https://dangry.ru/iuliia/scientific/<br>
	 * <b>Name</b>: {@code scientific}
	 */
	public static final String SCIENTIFIC = "scientific";

	/**
	 * Telegram transliteration schema.<br>
	 * https://dangry.ru/iuliia/telegram/<br>
	 * <b>Name</b>: {@code telegram}
	 */
	public static final String TELEGRAM = "telegram";

	/**
	 * UNGEGN 1987 V/18 transliteration schema.<br>
	 * https://dangry.ru/iuliia/ungegn-1987/<br>
	 * <b>Name</b>: {@code ungegn_1987}
	 */
	public static final String UNGEGN_1987 = "ungegn_1987";

	/**
	 * Wikipedia transliteration schema.<br>
	 * https://dangry.ru/iuliia/wikipedia/<br>
	 * <b>Name</b>: {@code wikipedia}
	 */
	public static final String WIKIPEDIA = "wikipedia";

	/**
	 * Yandex.Maps transliteration schema.<br>
	 * https://dangry.ru/iuliia/yandex-maps/<br>
	 * <b>Name</b>: {@code yandex_maps}
	 */
	public static final String YANDEX_MAPS = "yandex_maps";

	/**
	 * Yandex.Money transliteration schema.<br>
	 * https://dangry.ru/iuliia/yandex-money/<br>
	 * <b>Name</b>: {@code yandex_money}
	 */
	public static final String YANDEX_MONEY = "yandex_money";

}
