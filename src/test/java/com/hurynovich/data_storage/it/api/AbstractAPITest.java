package com.hurynovich.data_storage.it.api;

import com.hurynovich.data_storage.it.api.asserter.GenericApiRequestAsserter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.PostConstruct;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractAPITest {

	@Autowired
	private MockMvc mockMvc;

	protected GenericApiRequestAsserter requestAsserter;

	@PostConstruct
	private void initRequestAsserter() {
		requestAsserter = new GenericApiRequestAsserter(mockMvc);
	}

}
