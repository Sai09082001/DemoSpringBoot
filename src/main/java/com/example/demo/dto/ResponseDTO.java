package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {
   private int status; //200,400,500
   private String msg;

   @JsonInclude(Include.NON_NULL)
   private T data;

   public ResponseDTO(int status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}
   
}