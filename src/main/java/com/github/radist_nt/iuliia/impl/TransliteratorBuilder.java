package com.github.radist_nt.iuliia.impl;

import java.util.List;

import javax.annotation.Nonnull;

import com.github.radist_nt.iuliia.Transliterator;

public class TransliteratorBuilder {

	private TransliteratorBuilder() {
	}

	public static @Nonnull Transliterator build(@Nonnull String name) {
		Schema schema = MappingLoader.loadMapping(name);
		return new TransliteratorImpl(schema);
	}

	public static List<String> schemas() {
		return MappingLoader.loadSchemaList();
	}

	public static List<String> schemasAll() {
		return MappingLoader.loadAllSchemaList();
	}

}
