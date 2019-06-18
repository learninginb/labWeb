package com.simulation.core.util;

import java.io.File;
import javax.servlet.ServletContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
/**
 * 服务应用工具类
 */
public class AppUtil  implements ApplicationContextAware{
 /**
  *  spring 上下文
  */
  private static ApplicationContext applicationContext;
  /**
   * servlet 上下文
   */
  private static ServletContext servletContext;

  /**
   * 初始化
   * @param _servletContext
   */
  public static void init(ServletContext _servletContext)
  {
    servletContext = _servletContext;
  }

  public void setApplicationContext(ApplicationContext contex)
    throws BeansException
  {
    applicationContext = contex;
  }
  /**
   * 获取spring的上下文
   */
  public static ApplicationContext getContext()
  {
    return applicationContext;
  }
  /**
   * 获取servlet的上下文
   */
  public static ServletContext getServletContext()
    throws Exception
  {
    return servletContext;
  }
  /**
   * 通过类获取spring的工厂Bean
   */
  public static Object getBean(Class cls)
  {
    return applicationContext.getBean(cls);
  }

  /**
   * 通过BeanId获取spring的工厂Bean
   */
  public static Object getBean(String beanId)
  {
    return applicationContext.getBean(beanId);
  }
  /**
   * 获取当前绝对路径
   */
  public static String getAppAbsolutePath()
  {
    return servletContext.getRealPath("/");
  }
  /**
   * 获取当前的相对路径
   */
  public static String getRealPath(String path)
  {
    return servletContext.getRealPath(path);
  }
  /**
   * 获取当前类路径
   */
  public static String getClasspath(){
    String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    String rootPath = "";

    if ("\\".equals(File.separator)) {
      rootPath = classPath.substring(1);
      rootPath = rootPath.replace("/", "\\");
    }

    if ("/".equals(File.separator)) {
      rootPath = classPath.substring(1);
      rootPath = rootPath.replace("\\", "/");
    }
    return rootPath;
  }

  public static void main(String[] args) {
    System.out.println("path:" + getClasspath());
  }
}