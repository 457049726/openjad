package com.openjad.common.util.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.openjad.common.exception.BizException;
import com.openjad.common.page.PageBO;
import com.openjad.common.page.PageDTO;
import com.openjad.common.util.constant.UtilLogCode;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

public class PojoUtils {

	public static final Logger logger = LoggerFactory.getLogger(PojoUtils.class);

	public static <T> T copyTo(Class<T> clazz, Object src) {
		if (src == null) {
			return null;
		}
		try {
			T t = clazz.newInstance();
			BeanUtils.copyProperties(src, t);
			return t;

		} catch (Exception e) {

			throw new BizException(UtilLogCode.CODE_00003, "属性复制失败," + e.getMessage(), e);

		}
	}

	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> PageDTO<T> copyToPageDTO(Class<T> clazz, PageBO<?> pageBO) {
		if (pageBO == null) {
			return null;
		}

		PageDTO dto = new PageDTO(pageBO.getPageNo(), pageBO.getPageSize());
		dto.setCount(pageBO.getCount());

		if (pageBO.getList() != null && !pageBO.getList().isEmpty()) {
			dto.setList(copyToList(clazz, pageBO.getList()));
		}

		return dto;

	}

	public static <T> List<T> copyToList(Class<T> clazz, List<?> srcList) {
		if (srcList == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (Object src : srcList) {
			list.add(copyTo(clazz, src));
		}
		return list;
	}

	public static void mergeObject(Object[] srcs, Object dst) {
		if (srcs == null || srcs.length == 0 || dst == null) {
			return;
		}
		for (Object src : srcs) {
			copyProperties(src, dst);
		}
	}
	
	private static void copyProperties(Object src, Object dst) {
		if (src == null || dst == null) {
			return;
		}
		try {
			BeanUtils.copyProperties(src, dst);
		} catch (Exception e) {
			throw new BizException(UtilLogCode.CODE_00003, "属性复制失败," + e.getMessage(), e);
		}
	}

}
