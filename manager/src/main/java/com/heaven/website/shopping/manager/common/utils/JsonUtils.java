package com.heaven.website.shopping.manager.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xiaoxi.li
 * @date 2019/03/25 15:00
 * @description
 */
public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	public static String toJson(Object obj) {
		try {
			return OBJECT_MAPPER.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}


}
