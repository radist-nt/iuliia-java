package com.github.radist_nt.iuliia.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import com.github.radist_nt.iuliia.Iuliia;
import com.github.radist_nt.iuliia.impl.MappingLoader;

public class TestLoader {

	public void testSchema(String schemaName) {
		Schema schema = MappingLoader.loadMapping(schemaName, true);
		assertNotNull(schemaName + " not loaded", schema);
		assertEquals(schemaName + " name does not matches", schemaName, schema.getName());
		assertNotNull(schemaName + " description is null", schema.getDescription());
		assertNotNull(schemaName + " url is null", schema.getUrl());
		assertNotNull(schemaName + " samples is null", schema.getSamples());
		assertFalse(schemaName + " samples is empty", schema.getSamples().isEmpty());
		schema.getSamples().forEach(l -> assertTrue(schemaName + " sample not valid", l != null && l.size() == 2));
	}

	@Test(groups = "init")
	public void loadSingle() {
		testSchema("gost_16876_alt");
	}

	@Test(groups = "init")
	public void loadNoExamples() {
		Schema schema = MappingLoader.loadMapping("mosmetro", false);
		assertNotNull(schema);
		assertNull(schema.getSamples());
	}

	@Test(groups = "init")
	public void loadInvalid() {
		Schema schema = MappingLoader.loadMapping("a-baba-galamaga", false);
		assertNull(schema);
	}

	@Test(groups = "init")
	public void loadAll() {
		List<String> list = MappingLoader.loadSchemaList();
		assertNotNull(list);
		// assertEquals(list.size(), 27); // could be changed
		list.forEach(this::testSchema);
	}

	private void testMap(Map<String, String> map, boolean nullable, Predicate<Integer> keyLengthCheck,
			String schemaName, String mapName) {
		assertNotNull(map);
		if (!nullable)
			assertTrue(schemaName + " empty/null map (cannot be empty) in " + mapName, !map.isEmpty());
		else if (map.isEmpty())
			return;
		map.keySet().stream().map(String::length).filter(keyLengthCheck.negate()).findAny()
				.ifPresent(x -> fail(schemaName + " not valid map key length in " + mapName));
	}

	private void testSchemaMapping(Schema schema) {
		testMap(schema.getMapping(), false, Integer.valueOf(1)::equals, schema.getName(), "mapping");
		testMap(schema.getPrevMapping(), true, l -> l == 1 || l == 2, schema.getName(), "prev_mapping");
		testMap(schema.getNextMapping(), true, Integer.valueOf(2)::equals, schema.getName(), "next_mapping");
		testMap(schema.getEndingMapping(), true, Integer.valueOf(2)::equals, schema.getName(), "ending_mapping");
		if (schema.getEndingMapping() != null)
			schema.getEndingMapping().values()
					.forEach(v -> assertFalse(schema.getName() + " empty value in ending_mapping", v.isEmpty()));
	}

	@Test(groups = "init")
	public void testSchemaMapping() {
		MappingLoader.loadSchemaList().stream().map(MappingLoader::loadMapping).forEach(this::testSchemaMapping);
		assertEquals(MappingLoader.loadSchemaList().size(), new HashSet<>(MappingLoader.loadSchemaList()).size());
	}

	@Test(groups = "init")
	public void testSchemaMappingAliases() {
		assertEquals(MappingLoader.loadAllSchemaList().size(), new HashSet<>(MappingLoader.loadAllSchemaList()).size());
	}

	/**
	 * Generate/update the constants in {@link Iuliia}. Enable only if
	 * 
	 * @throws IOException
	 */
	@Test(dependsOnGroups = "init", enabled = false)
	public void generateIuliiaConstants() throws IOException {
		String str = "\n"
				+ MappingLoader
						.loadSchemaList().stream().sorted().map(
								MappingLoader::loadMapping)
						.map(m -> "\t/**\n\t * " + m.getDescription() + (m.getDescription().endsWith(".") ? "" : ".") //
								+ "<br>\n\t * " + m.getUrl() //
								+ (m.getComments() != null
										? m.getComments().stream()
												.map(c -> "<br>\n\t * " + c.replace(">", "&gt;").replace("<", "&lt;"))
												.collect(Collectors.joining())
										: "") //
								+ "<br>\n\t * <b>Name</b>: {@code " + m.getName() + "}" //
								+ (m.getAliases() != null
										? "<br>\n\t * <b>Aliases</b>: " + m.getAliases().stream()
												.map(a -> "{@code " + a + "}").collect(Collectors.joining(", "))
										: "") //
								+ "\n\t */\n" //
								+ "\tpublic static final String " + m.getName().toUpperCase() + " = \"" + m.getName()
								+ "\";")
						.collect(Collectors.joining("\n\n"))
				+ "\n";
		File file = new File("src/main/java/com/radist_nt/iuliia/Iuliia.java");
		assertTrue(file.isFile());
		assertTrue(file.canRead());
		assertTrue(file.canWrite());
		String javaSource = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
		javaSource = javaSource.substring(0, javaSource.lastIndexOf('}')).replaceFirst(
				"(\\n[\\s\\t]*// GENERATED CONSTANTS (DO NOT EDIT MANUALLY)[\\s\\S]+)?$",
				"\n\t// GENERATED CONSTANTS (DO NOT EDIT MANUALLY)\n") + str + "\n}\n";
		FileUtils.writeStringToFile(file, javaSource, StandardCharsets.UTF_8);
	}

}
