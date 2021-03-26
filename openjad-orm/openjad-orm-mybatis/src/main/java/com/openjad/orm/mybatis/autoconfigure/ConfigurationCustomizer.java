package com.openjad.orm.mybatis.autoconfigure;

import org.apache.ibatis.session.Configuration;

@FunctionalInterface
public interface ConfigurationCustomizer {

  /**
   * Customize the given a {@link Configuration} object.
   * 
   * @param configuration
   *          the configuration object to customize
   */
  void customize(Configuration configuration);

}
