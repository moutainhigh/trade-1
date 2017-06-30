package com.pzj.core.trade.order.resolver;

import java.util.UUID;

/**
 * 验证码生成器.
 * @author YRJ
 *
 */
final public class CodeGenerater {

	public static String generate(String prefix) {
		int hashcode = UUID.randomUUID().hashCode();
		if (hashcode < 0) {
			hashcode = Math.abs(hashcode);
		}

		String code = (prefix + hashcode);
		return code.substring(code.length() - 6);
	}

}
