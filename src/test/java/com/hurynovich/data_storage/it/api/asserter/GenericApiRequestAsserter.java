package com.hurynovich.data_storage.it.api.asserter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.skyscreamer.jsonassert.comparator.JSONComparator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.skyscreamer.jsonassert.comparator.JSONCompareUtil.getKeys;

public class GenericApiRequestAsserter implements ApiRequestAsserter {

	private final MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public GenericApiRequestAsserter(final MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Override
	public <T, U> void assertRequest(final HttpMethod method, final String urlTemplate, final T body,
									 final HttpStatus expectedStatus, final U expectedBody,
									 final String... ignoreProperties) {
		try {
			final ResultMatcher bodyMatcher = resolveBodyMatcher(expectedBody, ignoreProperties);

			mockMvc.
					perform(MockMvcRequestBuilders.request(method, urlTemplate).
							contentType(MediaType.APPLICATION_JSON_VALUE).
							content(objectMapper.writeValueAsString(body))).
					andExpect(MockMvcResultMatchers.status().is(expectedStatus.value())).
					andExpect(bodyMatcher);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private <T> ResultMatcher resolveBodyMatcher(final T expectedBody, final String... ignoreProperties)
			throws JsonProcessingException {
		final String expectedJson = objectMapper.writeValueAsString(expectedBody);
		final ResultMatcher matcher;
		if (ignoreProperties != null) {
			matcher = new JsonIgnoringMatcher(expectedJson, ignoreProperties);
		} else {
			matcher = defaultMatcher(expectedJson).get();
		}

		return matcher;
	}

	private Supplier<ResultMatcher> defaultMatcher(final String expectedJson) {
		return () -> MockMvcResultMatchers.content().json(expectedJson);
	}

	@Override
	public <T> void assertRequest(final HttpMethod method, final String urlTemplate,
								  final HttpStatus expectedStatus, final T expectedBody,
								  final String... ignoreProperties) {
		try {
			final ResultMatcher bodyMatcher = resolveBodyMatcher(expectedBody, ignoreProperties);

			mockMvc.
					perform(MockMvcRequestBuilders.request(method, urlTemplate)).
					andExpect(MockMvcResultMatchers.status().is(expectedStatus.value())).
					andExpect(bodyMatcher);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> void assertRequest(final HttpMethod method, final String urlTemplate, final T body,
								  final HttpStatus expectedStatus) {
		try {
			mockMvc.
					perform(MockMvcRequestBuilders.request(method, urlTemplate).
							contentType(MediaType.APPLICATION_JSON_VALUE).
							content(objectMapper.writeValueAsString(body))).
					andExpect(MockMvcResultMatchers.status().is(expectedStatus.value())).
					andExpect(MockMvcResultMatchers.content().json(StringUtils.EMPTY));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void assertRequest(final HttpMethod method, final String urlTemplate,
							  final HttpStatus expectedStatus) {
		try {
			mockMvc.
					perform(MockMvcRequestBuilders.request(method, urlTemplate).
							contentType(MediaType.APPLICATION_JSON_VALUE)).
					andExpect(MockMvcResultMatchers.status().is(expectedStatus.value())).
					andExpect(MockMvcResultMatchers.content().json(StringUtils.EMPTY));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static class JsonIgnoringMatcher implements ResultMatcher {

		private final String expectedJson;

		private final String[] ignoreProperties;

		private JsonIgnoringMatcher(final String expectedJson, final String[] ignoreProperties) {
			this.expectedJson = expectedJson;
			this.ignoreProperties = ignoreProperties;
		}

		@Override
		public void match(final MvcResult result) throws Exception {
			final String actualJson = result.getResponse().getContentAsString();
			Assertions.assertNotNull(actualJson);

			final JSONComparator comparator = new RecursiveComparator(JSONCompareMode.STRICT,
					Stream.of(ignoreProperties).
							map(ignoreProperty -> new Customization(ignoreProperty, (p1, p2) -> true)).
							toArray(Customization[]::new)
			);

			JSONAssert.assertEquals(expectedJson, actualJson, comparator);
		}

	}

	/*
	 * RecursiveComparator ignores prefixes , which means that if e.g. Customization
	 * is created with path 'id', comparator will be applied to every field with name 'id'
	 * recursively, on every level, like 'root.id', 'root.child.id', 'root.children[0].id',
	 * 'root.children[0].inner.id' and so on
	 */
	private static class RecursiveComparator extends CustomComparator {

		public RecursiveComparator(final JSONCompareMode mode, final Customization... customizations) {
			super(mode, customizations);
		}

		@Override
		protected void checkJsonObjectKeysExpectedInActual(final String prefix, final JSONObject expected,
														   final JSONObject actual, final JSONCompareResult result)
				throws JSONException {
			final Set<String> expectedKeys = getKeys(expected);
			for (String key : expectedKeys) {
				final Object expectedValue = expected.get(key);
				if (actual.has(key)) {
					Object actualValue = actual.get(key);

					compareValues(key, expectedValue, actualValue, result);
				} else {
					result.missing(prefix, key);
				}
			}
		}

	}

}
