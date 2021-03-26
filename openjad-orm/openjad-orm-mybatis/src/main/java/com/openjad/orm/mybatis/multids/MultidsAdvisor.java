package com.openjad.orm.mybatis.multids;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.core.Ordered;

public class MultidsAdvisor extends AbstractPointcutAdvisor {

	MultidsPointcut pointcur = new MultidsPointcut();

	MultidsInterceptor multidsInterceptor = new MultidsInterceptor();

	@Override
	public Pointcut getPointcut() {
		return pointcur;
	}

	@Override
	public Advice getAdvice() {
		return multidsInterceptor;
	}

	public int getOrder() {
		Advice advice = getAdvice();
		if (advice instanceof Ordered) {
			return ((Ordered) advice).getOrder();
		}
		return Ordered.LOWEST_PRECEDENCE - 1000;
	}

}
