package com.simulation.common.base;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class BaseMybatisController
  implements WebBindingInitializer
{
  public static final String SUCCESS = "success";
  public static final String MSG = "msg";
  public static final String DATA = "data";
  String s = "";
  
  @InitBinder
  public void initBinder(WebDataBinder binder, WebRequest request)
  {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    binder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
  }


}
