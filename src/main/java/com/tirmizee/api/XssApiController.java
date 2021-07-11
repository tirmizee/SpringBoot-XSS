package com.tirmizee.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tirmizee.dto.TemplateDTO;

@RestController
public class XssApiController {
	
	@PostMapping(path = "/xss")
	public TemplateDTO testXss(@RequestBody TemplateDTO reqTemplateDTO) {
		return reqTemplateDTO;
	}

}
