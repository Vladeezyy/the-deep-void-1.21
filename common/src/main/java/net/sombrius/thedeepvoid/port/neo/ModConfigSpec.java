package net.sombrius.thedeepvoid.port.neo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * NeoForge's config builder calls on a small TOML file of the same layout as NeoForge writes it (sections, comments,
 * {@code key = value}); booleans, numbers and strings. Both loaders read the same file, so a NeoForge config keeps its
 * values on Fabric. Missing or broken values get their default and the file is written again.
 */
public final class ModConfigSpec {
	private final List<ConfigValue<?>> values;

	private ModConfigSpec(List<ConfigValue<?>> values) {
		this.values = values;
	}

	public List<ConfigValue<?>> values() {
		return this.values;
	}

	public void load(Path file) {
		Map<String, String> raw = new LinkedHashMap<>();
		boolean complete = false;
		if (Files.exists(file)) {
			try {
				String section = "";
				for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
					String t = line.strip();
					if (t.isEmpty() || t.startsWith("#"))
						continue;
					if (t.startsWith("[") && t.endsWith("]")) {
						section = unquote(t.substring(1, t.length() - 1).strip()) + ".";
						continue;
					}
					int eq = t.indexOf('=');
					if (eq > 0)
						raw.put(section + unquote(t.substring(0, eq).strip()), t.substring(eq + 1).strip());
				}
				complete = true;
			} catch (IOException e) {
				complete = false;
			}
		}
		for (ConfigValue<?> value : this.values) {
			String text = raw.get(value.path());
			if (text == null || !value.parse(text)) {
				value.reset();
				complete = false;
			}
		}
		if (!complete)
			save(file);
	}

	public void save(Path file) {
		StringBuilder out = new StringBuilder();
		String section = null;
		for (ConfigValue<?> value : this.values) {
			int dot = value.path().lastIndexOf('.');
			String s = dot < 0 ? "" : value.path().substring(0, dot);
			if (!s.equals(section)) {
				section = s;
				if (!s.isEmpty())
					out.append(out.isEmpty() ? "" : "\n").append('[').append(quoteKey(s)).append("]\n");
			}
			String indent = s.isEmpty() ? "" : "\t";
			for (String c : value.comment)
				out.append(indent).append('#').append(c).append('\n');
			out.append(indent).append(quoteKey(value.path().substring(dot + 1))).append(" = ").append(value.format()).append('\n');
		}
		try {
			Files.createDirectories(file.getParent());
			Files.writeString(file, out.toString(), StandardCharsets.UTF_8);
		} catch (IOException ignored) {
			// a read-only config folder keeps the defaults
		}
	}

	private static String quoteKey(String key) {
		StringBuilder out = new StringBuilder();
		for (String part : key.split("\\.")) {
			if (!out.isEmpty())
				out.append('.');
			out.append(part.matches("[A-Za-z0-9_-]+") ? part : '"' + part + '"');
		}
		return out.toString();
	}

	private static String unquote(String s) {
		StringBuilder out = new StringBuilder();
		boolean quoted = false;
		for (char c : s.toCharArray()) {
			if (c == '"')
				quoted = !quoted;
			else if (quoted || !Character.isWhitespace(c))
				out.append(c);
		}
		return out.toString();
	}

	public static class Builder {
		private final List<ConfigValue<?>> values = new ArrayList<>();
		private final List<String> path = new ArrayList<>();
		private String[] comment = new String[0];

		public Builder comment(String... comment) {
			this.comment = comment;
			return this;
		}

		public Builder push(String name) {
			this.path.add(name);
			return this;
		}

		public Builder pop() {
			this.path.remove(this.path.size() - 1);
			return this;
		}

		public <T> ConfigValue<T> define(String name, T defaultValue) {
			List<String> full = new ArrayList<>(this.path);
			full.add(name);
			ConfigValue<T> value = new ConfigValue<>(String.join(".", full), defaultValue, this.comment);
			this.comment = new String[0];
			this.values.add(value);
			return value;
		}

		public ModConfigSpec build() {
			return new ModConfigSpec(List.copyOf(this.values));
		}
	}

	public static class ConfigValue<T> {
		private final String path;
		private final T defaultValue;
		private final String[] comment;
		private T value;

		ConfigValue(String path, T defaultValue, String[] comment) {
			this.path = path;
			this.defaultValue = defaultValue;
			this.comment = comment;
			this.value = defaultValue;
		}

		public String path() {
			return this.path;
		}

		public T get() {
			return this.value;
		}

		public T getDefault() {
			return this.defaultValue;
		}

		public void set(T value) {
			this.value = value;
		}

		void reset() {
			this.value = this.defaultValue;
		}

		@SuppressWarnings("unchecked")
		boolean parse(String text) {
			try {
				Object v;
				if (this.defaultValue instanceof Boolean) {
					if (!text.equals("true") && !text.equals("false"))
						return false;
					v = Boolean.parseBoolean(text);
				} else if (this.defaultValue instanceof Double) {
					v = Double.parseDouble(text);
				} else if (this.defaultValue instanceof Integer) {
					v = Integer.parseInt(text);
				} else if (this.defaultValue instanceof Long) {
					v = Long.parseLong(text);
				} else if (this.defaultValue instanceof String) {
					if (text.length() < 2 || !text.startsWith("\"") || !text.endsWith("\""))
						return false;
					v = text.substring(1, text.length() - 1).replace("\\\"", "\"").replace("\\\\", "\\");
				} else {
					return false;
				}
				this.value = (T) v;
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}

		String format() {
			if (this.value instanceof String s)
				return '"' + s.replace("\\", "\\\\").replace("\"", "\\\"") + '"';
			return String.valueOf(this.value);
		}
	}
}
