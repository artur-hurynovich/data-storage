package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.DataStorageApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = DataStorageApplication.class)
public abstract class AbstractAPITest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	protected <T, U> ResponseEntity<U> send(final HttpMethod method, final String urlTemplate,
											final ParameterizedTypeReference<U> responseClass) {
		final RequestEntity<T> requestEntity =
				new RequestEntity<>(null, null, method, null, responseClass.getType());

		return testRestTemplate.exchange(urlTemplate, method, requestEntity, responseClass);
	}

	protected <T, U> ResponseEntity<U> send(final HttpMethod method, final String urlTemplate, final T body,
											final ParameterizedTypeReference<U> responseClass) {
		final RequestEntity<T> requestEntity =
				new RequestEntity<>(body, null, method, null, responseClass.getType());

		return testRestTemplate.exchange(urlTemplate, method, requestEntity, responseClass);
	}
}
