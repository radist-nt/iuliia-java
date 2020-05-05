package com.github.radist_nt.iuliia.impl;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.github.radist_nt.iuliia.Iuliia;
import com.github.radist_nt.iuliia.impl.MappingLoader;
import com.github.radist_nt.iuliia.impl.TransliteratorImpl;

public class TestIuliiaApi {

	private void testMapping(TransliteratorImpl impl) {
		impl.getSchema().getSamples().forEach(s -> assertEquals(impl.getSchema().getName() + " sample test failed",
				s.get(1), impl.transliterate(s.get(0))));
	}

	@Test(dependsOnGroups = "init")
	public void testTransliterator() {
		MappingLoader.loadSchemaList().stream().map(m -> MappingLoader.loadMapping(m, true))
				.map(TransliteratorImpl::new).forEach(this::testMapping);
	}

	private void testMapping(Schema schema) {
		schema.getSamples().forEach(s -> assertEquals(schema.getName() + " sample test failed (static)", s.get(1),
				Iuliia.translate(s.get(0), schema.getName())));
	}

	@Test(dependsOnGroups = "init")
	public void testStatic() {
		MappingLoader.loadSchemaList().stream().map(m -> MappingLoader.loadMapping(m, true)).forEach(this::testMapping);
	}

	@Test(dependsOnGroups = "init")
	public void testConstants() throws IllegalArgumentException, IllegalAccessException {
		final int modifiers = Modifier.FINAL | Modifier.PUBLIC | Modifier.STATIC;
		Set<String> schemas = new HashSet<>(MappingLoader.loadSchemaList());
		for (Field field : Iuliia.class.getFields()) {
			if ((field.getModifiers() & modifiers) != modifiers || !String.class.equals(field.getType()))
				continue;
			String value = (String) field.get(null);
			assertNotNull(value);
			assertTrue(schemas.remove(value));
		}
		assertTrue(schemas.isEmpty(), schemas.toString());
	}

}
