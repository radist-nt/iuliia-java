package com.github.radist_nt.iuliia.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.json.JsonMapper;

class MappingLoader {

	private static final String SCHEMAS_DIR = "schemas/";
	private static final ObjectMapper mapper = JsonMapper.builder().enable(MapperFeature.DEFAULT_VIEW_INCLUSION)
			.build();
	private static final ObjectReader reader = mapper.readerWithView(NoExampleView.class);

	public static interface ExampleView {
	}

	public static interface NoExampleView {
	}

	public static @Nonnull Schema loadMapping(@Nonnull String schemaName) {
		Objects.requireNonNull(schemaName);
		Schema result = loadMapping(schemaName, false);
		if (result != null)
			return result;
		else if (SchemaAliasesHolder.SCHEMA_ALIASES.containsKey(schemaName))
			return loadMapping(SchemaAliasesHolder.SCHEMA_ALIASES.get(schemaName), false);
		throw new IllegalArgumentException("Invalid schema name: " + schemaName);
	}

	static Schema loadMapping(String fileName, boolean withExamples) {
		Schema schema = null;
		try {
			InputStream stream = MappingLoader.class.getResourceAsStream(SCHEMAS_DIR + fileName + ".json");
			if (stream == null)
				return null;
			schema = (withExamples ? mapper.readerWithView(ExampleView.class) : reader).readValue(stream, Schema.class);
			schema.setMapping(fixMap(schema.getMapping(), MappingLoader::capitalize, null));
			schema.setPrevMapping(fixMap(schema.getPrevMapping(), UnaryOperator.identity(), MappingLoader::capitalize));
			schema.setNextMapping(
					fixMap(schema.getNextMapping(), MappingLoader::capitalize, MappingLoader::capitalize));
			schema.setEndingMapping(fixMap(schema.getEndingMapping(), null, String::toUpperCase));
			return schema;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static Map<String, String> fixMap(Map<String, String> source, UnaryOperator<String> capitalizeValueOp,
			UnaryOperator<String> uppercaseValueOp) {
		if (source == null || source.isEmpty())
			return Collections.emptyMap();
		Map<String, String> capMap = null, upMap = null;
		if (capitalizeValueOp != null)
			capMap = source.entrySet().stream()
					.collect(Collectors.toMap(castLambda(Entry::getKey).andThen(MappingLoader::capitalize),
							castLambda(Entry::getValue).andThen(capitalizeValueOp)));
		if (uppercaseValueOp != null)
			upMap = source.entrySet().stream()
					.collect(Collectors.toMap(castLambda(Entry::getKey).andThen(String::toUpperCase),
							castLambda(Entry::getValue).andThen(uppercaseValueOp)));
		if (capMap != null)
			source.putAll(capMap);
		if (upMap != null)
			source.putAll(upMap);
		return Collections.unmodifiableMap(source);
	}

	private static Function<? super Entry<String, String>, ? extends String> castLambda(
			Function<? super Entry<String, String>, ? extends String> lambda) {
		return lambda;
	}

	private static String capitalize(String str) {
		switch (str.length()) {
		case 0:
			return str;
		case 1:
			return str.toUpperCase();
		default:
			return Character.toUpperCase(str.charAt(0)) + str.substring(1);
		}
	}

	public static List<String> loadSchemaList() {
		return SchemaListHolder.SCHEMA_LIST;
	}

	public static List<String> loadAllSchemaList() {
		List<String> result = new ArrayList<String>();
		result.addAll(SchemaListHolder.SCHEMA_LIST);
		result.addAll(SchemaAliasesHolder.SCHEMA_ALIASES.keySet());
		return result;
	}

	private static final class SchemaListHolder {
		private static final List<String> SCHEMA_LIST = getSchemaList();

		private static List<String> getSchemaList() {
			try {
				URI uri = MappingLoader.class.getResource(SCHEMAS_DIR).toURI();
				if (uri.getScheme().equals("jar")) {
					try (FileSystem fileSystem = FileSystems.getFileSystem(uri)) {
						return getSchemaList(Files.walk(fileSystem.getPath(SCHEMAS_DIR), 1));
					}
				} else {
					return getSchemaList(Files.walk(Paths.get(uri), 1));
				}
			} catch (URISyntaxException | IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		private static List<String> getSchemaList(Stream<Path> paths) {
			return Collections.unmodifiableList(paths.map(Path::getFileName).map(Path::toString)
					.filter(Pattern.compile("^.+\\.json$").asPredicate()).map(s -> s.substring(0, s.length() - 5))
					.collect(Collectors.toList()));
		}
	}

	private static final class SchemaAliasesHolder {
		private static final Map<String, String> SCHEMA_ALIASES = getSchemaAliases();

		private static Map<String, String> getSchemaAliases() {
			return SchemaListHolder.SCHEMA_LIST.stream().map(SchemaAliasesHolder::loadSchema)
					.flatMap(s -> s.getAliases() == null ? Stream.empty()
							: s.getAliases().stream().map(a -> new AbstractMap.SimpleImmutableEntry<>(a, s.getName())))
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		}

		private static Schema loadSchema(String name) {
			return loadMapping(name, false);
		}
	}

}
