package com.cndinfo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DateUtil {
	
	/* 현재 시간 'yyyy-MM-dd' 형태로 반환함. */
	public String createDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

}
