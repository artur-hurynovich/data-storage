package com.hurynovich.data_storage.it.api.asserter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GenericApiRequestAsserter implements ApiRequestAsserter {

	private final MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public GenericApiRequestAsserter(final MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Override
	public <T, U> void assertRequest(final HttpMethod method, final String urlTemplate, final T body,
									 final HttpStatus expectedStatus, final U expectedBody) {
		try {
			mockMvc.
					perform(MockMvcRequestBuilders.request(method, urlTemplate).
							contentType(MediaType.APPLICATION_JSON_VALUE).
							content(objectMapper.writeValueAsString(body))).
					andExpect(MockMvcResultMatchers.status().is(expectedStatus.value())).
					andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedBody)));
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
					andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> void assertRequest(final HttpMethod method, final String urlTemplate,
								  final HttpStatus expectedStatus, final T expectedBody) {
		try {
			mockMvc.
					perform(MockMvcRequestBuilders.request(method, urlTemplate)).
					andExpect(MockMvcResultMatchers.status().is(expectedStatus.value())).
					andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedBody)));
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
					andExpect(MockMvcResultMatchers.content().string(StringUtils.EMPTY));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
