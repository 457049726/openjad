package com.openjad.orm.mybatis.scanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Repository;

import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;
import com.openjad.orm.annotation.JadDao;
import com.openjad.orm.mybatis.constant.MybatisLogCode;

/**
 * 
 * Mapper扫描器
 * 自动从classpath中扫描mapper文件
 * 
 * @author hechuan
 *
 */
public class JadClassPathMapperScanner extends ClassPathMapperScanner {

	private static final Logger logger = LoggerFactory.getLogger(JadClassPathMapperScanner.class);

	/**
	 * 扫描到的所有dao类
	 */
	private CopyOnWriteArraySet<String> allDaoClassName = new CopyOnWriteArraySet<String>();

	private List<Class<? extends Annotation>> annotationClasses;
	
	public JadClassPathMapperScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	/**
	 * 扫描
	 */
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> definitions = super.doScan(basePackages);

		for (BeanDefinitionHolder definition : definitions) {
			try {
				String daoClassName = definition.getBeanDefinition().getConstructorArgumentValues()
						.getArgumentValue(0, String.class).getValue().toString();

				allDaoClassName.add(daoClassName);

				logger.trace("扫描到dao:" + daoClassName);
			} catch (Exception e) {
				logger.warn(MybatisLogCode.CODE_00009,
						"获取" + definition.getBeanName() + "对应的接口类失败,忽略," + e.getMessage(), e);
			}
		}

		return definitions;
	}

	// 兼容性增强 20200528
	public void registerFilters() {

		try {
			Class<? extends Annotation> annotationClass = getAnnotationClass();
			if (annotationClass == null) {
				setAnnotationClass(JadDao.class);
			}
		} catch (Exception e) {
			logger.error("无法增强mybatis自动扫描注解," + e.getMessage(), e);
		}

		super.registerFilters();

		try {
			List<TypeFilter> tfList = getIncludeFilters();
			if (annotationClasses != null) {
				for (Class<? extends Annotation> ann : annotationClasses) {
					if (hasGneteDaoAnn(ann, tfList)) {
						continue;
					}
					addIncludeFilter(new AnnotationTypeFilter(ann));
				}
			}
			if (!hasGneteDaoAnn(JadDao.class, tfList)) {
				addIncludeFilter(new AnnotationTypeFilter(JadDao.class));
			}
			if (!hasGneteDaoAnn(Mapper.class, tfList)) {
				addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
			}
			if (!hasGneteDaoAnn(Repository.class, tfList)) {
				addIncludeFilter(new AnnotationTypeFilter(Repository.class));
			}

		} catch (Exception e) {
			logger.error("无法增强mybatis自动扫描注解," + e.getMessage(), e);
		}
	}

	private boolean hasGneteDaoAnn(Class<? extends Annotation> clazz, List<TypeFilter> tfList) throws Exception {
		for (TypeFilter tf : tfList) {
			if (tf instanceof AnnotationTypeFilter) {
				AnnotationTypeFilter annTf = (AnnotationTypeFilter) tf;
				if (annTf.getAnnotationType() == clazz) {
					return true;
				}
			}
		}
		return false;
	}

	private Class<? extends Annotation> getAnnotationClass() throws Exception {
		Field field = ClassPathMapperScanner.class.getDeclaredField("annotationClass");
		field.setAccessible(true);
		return (Class<? extends Annotation>) field.get(this);
	}

	private List<TypeFilter> getIncludeFilters() throws Exception {
		Field field = ClassPathScanningCandidateComponentProvider.class.getDeclaredField("includeFilters");
		field.setAccessible(true);
		return (List<TypeFilter>) field.get(this);
	}

	
	public CopyOnWriteArraySet<String> getAllDaoClassName() {
		return allDaoClassName;
	}

	public List<Class<? extends Annotation>> getAnnotationClasses() {
		return annotationClasses;
	}

	public void setAnnotationClasses(List<Class<? extends Annotation>> annotationClasses) {
		this.annotationClasses = annotationClasses;
	}

}
