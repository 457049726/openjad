package com.openjad.orm.mybatis.scanner;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import com.openjad.orm.mybatis.constant.MybatisLogCode;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;


/**
 * 
 * Mapper扫描器
 * 自动从classpath中扫描mapper文件
 * 
 *  @author hechuan
 *
 */
public class JadClassPathMapperScanner extends ClassPathMapperScanner {

	private static final Logger logger = LoggerFactory.getLogger(JadClassPathMapperScanner.class);

	/**
	 * 扫描到的所有dao类
	 */
	private static CopyOnWriteArraySet<String> ALL_DAO_CLASS_NAME = new CopyOnWriteArraySet<String>();

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

				ALL_DAO_CLASS_NAME.add(daoClassName);

				logger.trace("扫描到dao:" + daoClassName);
			} catch (Exception e) {
				logger.warn(MybatisLogCode.CODE_00009,
						"获取" + definition.getBeanName() + "对应的接口类失败,忽略," + e.getMessage(), e);
			}
		}

		return definitions;
	}
	
	/**
	 * 获取所有 dao类名
	 * @return dao类名列表
	 */
	public static CopyOnWriteArraySet<String> getAllDaoClassName() {
		return ALL_DAO_CLASS_NAME;
	}

}
