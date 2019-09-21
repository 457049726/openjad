package com.openjad.test.base;

import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import com.openjad.test.junit.MySpringJunit4ClassRunner;

/**
 * 
 *  @author hechuan
 *
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(MySpringJunit4ClassRunner.class)
public class SpringAndPowerMockCase extends PowerMockIgnoreConf{
}
