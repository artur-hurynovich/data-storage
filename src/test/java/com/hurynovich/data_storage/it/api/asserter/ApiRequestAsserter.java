package com.hurynovich.data_storage.it.api.asserter;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface ApiRequestAsserter {

	<T, U> void assertRequest(HttpMethod method, String urlTemplate, T body, HttpStatus expectedStatus,
							  U expectedBody, String... ignoreProperties);

	<T> void assertRequest(HttpMethod method, String urlTemplate, HttpStatus expectedStatus, T expectedBody,
						   String... ignoreProperties);

	<T> void assertRequest(HttpMethod method, String urlTemplate, T body, HttpStatus expectedStatus);

	<T> void assertRequest(HttpMethod method, String urlTemplate, HttpStatus expectedStatus);

}
