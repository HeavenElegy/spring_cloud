package com.heaven.website.shopping.manager.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author xiaoxi.li
 * @date 2019/03/25 14:47
 * @description
 */
public class NetworkUtils {

	private static final OkHttpClient OK_HTTP_CLIENT;

	static {
		OK_HTTP_CLIENT = new OkHttpClient.Builder()
				.authenticator((route, response) -> {
					String credential = Credentials.basic("client_id", "666");
					return response.request().newBuilder().header("Authorization", credential).build();
				})
				.build();
	}

	public static <T> T get(String url, Class<T> returnType) {
		Request request = new Request.Builder()
				.url(url)
				.build();
		return exec(request, returnType);
	}

	public static <T> T form(String url, Map<String, Object> params, Class<T> returnType) {
		FormBody.Builder builder = new FormBody.Builder();
		for (Map.Entry<String, Object> e : params.entrySet()) {
			builder.add(e.getKey(), e.getValue() == null ? null : e.getValue().toString());
		}
		Request request = new Request.Builder()
				.url(url)
				.post(builder.build())
				.build();
		return exec(request, returnType);
	}

	private static <T> T exec(Request request, Class<T> returnType) {
		try {
			Call call = OK_HTTP_CLIENT.newCall(request);

			Response execute = call.execute();
			byte[] bytes = execute.body().bytes();
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(bytes, returnType);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

}
