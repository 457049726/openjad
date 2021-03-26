package com.openjad.common.util.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.openjad.common.constant.FrameworkCode;
import com.openjad.common.exception.BizException;
import com.openjad.common.page.PageBO;
import com.openjad.common.page.PageDTO;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

public class PojoUtils {

	public static final Logger logger = LoggerFactory.getLogger(PojoUtils.class);

	public static <T,R> T copyTo(Class<T> clazz, R src, CopyCallBack<T,R> copyCallBack) {
		if (src == null) {
			return null;
		}
		try {
//			T t = clazz.newInstance();
//			BeanUtils.copyProperties(src, t);
			T t = BeanMapper.map(src, clazz);
			if (copyCallBack != null) {
				copyCallBack.callBack(t, src);
			}
			return t;

		} catch (Exception e) {
			throw new BizException(FrameworkCode.CODE_00015, "属性复制失败," + e.getMessage(), e);
		}
	}

	public static <T,R> T copyTo(Class<T> clazz, R src) {
		return copyTo(clazz, src, null);
	}
	
	public static <T,R> PageDTO<T> copyToPageDTO(Class<T> clazz, PageBO<R> pageBO) {
		return copyToPageDTO(clazz,pageBO,null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T,R> PageDTO<T> copyToPageDTO(Class<T> clazz, PageBO<R> pageBO, CopyCallBack<T,R> copyCallBack) {
		if (pageBO == null) {
			return null;
		}

		PageDTO dto = new PageDTO(pageBO.getPageNo(), pageBO.getPageSize());
		dto.setCount(pageBO.getCount());

		if (pageBO.getList() != null && !pageBO.getList().isEmpty()) {
			dto.setList(copyToList(clazz, pageBO.getList(),copyCallBack));
		}

		return dto;

	}

	public static <T,R> List<T> copyToList(Class<T> clazz, List<R> srcList) {
		return copyToList(clazz, srcList, null);
	}

	public static <T,R> List<T> copyToList(Class<T> clazz, List<R> srcList,  CopyCallBack<T,R> copyCallBack) {
		if (srcList == null) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (R src : srcList) {
			list.add(copyTo(clazz, src, copyCallBack));
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
			throw new BizException(FrameworkCode.CODE_00015, "属性复制失败," + e.getMessage(), e);
		}
	}

}
