package com.cndinfo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SmsRequestDTO {

	public String type;
	public String from;
	public String content;
	public List<MessageRequestDTO> messages;
}
