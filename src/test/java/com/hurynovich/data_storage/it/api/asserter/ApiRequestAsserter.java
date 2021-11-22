package com.hurynovich.data_storage.it.api.asserter;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public interface ApiRequestAsserter {

	<T, U> void assertRequest(HttpMethod method, String urlTemplate, T body, HttpStatus expectedStatus, U expectedBody);

	<T> void assertRequest(HttpMethod method, String urlTemplate, HttpStatus expectedStatus, T expectedBody);

	<T> void assertRequest(HttpMethod method, String urlTemplate, T body, HttpStatus expectedStatus);

	<T> void assertRequest(HttpMethod method, String urlTemplate, HttpStatus expectedStatus);

}
