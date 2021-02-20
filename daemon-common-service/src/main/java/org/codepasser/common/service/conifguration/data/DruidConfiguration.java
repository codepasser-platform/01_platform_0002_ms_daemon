package org.codepasser.common.service.conifguration.data;

import com.alibaba.druid.support.http.StatViewServlet;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;

/**
 * DruidConfiguration.
 *
 * @author codepasser.
 * @version 0.0.1.
 * @serial 2021/2/20 : base version.
 */
@Configuration
public class DruidConfiguration {

  /**
   * 注册Servlet信息， 配置监控视图.
   *
   * @return servlet
   */
  @Bean
  @ConditionalOnMissingBean
  public ServletRegistrationBean<Servlet> druidServlet() {
    ServletRegistrationBean<Servlet> servletRegistrationBean =
        new ServletRegistrationBean<Servlet>(new StatViewServlet(), "/druid/*");
    // 白名单：
    servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
    // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
    servletRegistrationBean.addInitParameter("deny", "192.168.1.1/24");
    // 登录查看信息的账号密码, 用于登录Druid监控后台
    servletRegistrationBean.addInitParameter("loginUsername", "druid");
    servletRegistrationBean.addInitParameter("loginPassword", "druid_pw");
    // 是否能够重置数据.
    servletRegistrationBean.addInitParameter("resetEnable", "true");
    return servletRegistrationBean;
  }
}
