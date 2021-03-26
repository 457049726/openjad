package com.openjad.common.util;

import java.util.UUID;

public class UUIDUtils {
	
	public static String randomUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
