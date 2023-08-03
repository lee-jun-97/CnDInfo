package com.cndinfo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SmsResponseDTO {

	String requestId;
	LocalDateTime requestTime;
	String statusCode;
	String statusName;
}
