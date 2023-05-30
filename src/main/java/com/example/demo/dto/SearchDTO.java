package com.example.demo.dto;

import lombok.Data;

@Data
public class SearchDTO {

//	@NotBlank(message = "Not Blank")
//	@NotBlank(message = "{not.blank}")
//	@Size(min = 2, max = 20,message = "{not.blank}")
	private String keyword;
	private Integer currentPage;
	private Integer size;
	private String sortedField;
}
