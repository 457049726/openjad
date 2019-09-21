package com.openjad.test.base;

import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import com.openjad.test.junit.MySpringJunit4ClassRunner;

/**
 * 
 * <一句话文件描述>
 * 
 *  @Title SpringAndPowerMockCase
 *  @author hechuan
 *  @date 2019年9月20日
 *
 *  <功能详述>
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(MySpringJunit4ClassRunner.class)
public class SpringAndPowerMockCase extends PowerMockIgnoreConf{
}
