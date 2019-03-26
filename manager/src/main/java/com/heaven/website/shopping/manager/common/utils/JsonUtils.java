package com.heaven.website.shopping.manager.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json工具类
 * @author li.xiaoxi
 * @date 2019/03/25 15:00
 */
public class JsonUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * 对象转Json
	 * @param obj	任意对象
	 * @return	参数为null时，返回null。
	 */
	public static String toJson(Object obj) {
		try {
			if(obj == null) {
				return null;
			}else {
				return OBJECT_MAPPER.writeValueAsString(obj);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}


}
