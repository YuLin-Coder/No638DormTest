package org.wdl.dormTest.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyHttpServletRequestWrapper1 extends HttpServletRequestWrapper{

	// 用于存储请求参数
		 private Map<String , String[]> params = new HashMap<String, String[]>();  
		 // 构造方法
	     public MyHttpServletRequestWrapper1(HttpServletRequest request) throws IOException {    
	         super(request);
	         // 把请求参数添加到我们自己的map当中
	         this.params.putAll(request.getParameterMap()); 
	     }    
	     
	     
	     /**
	      * 添加参数到map中
	      * @param extraParams
	      */
	     public void setParameterMap(Map<String, String[]> extraParams) {
	         for (Map.Entry<String, String[]> entry : extraParams.entrySet()) {
	             setParameter(entry.getKey(), entry.getValue());
	         }
	     }
	     
	     /**
	      * 添加参数到map中
	      * @param name 
	      * @param value
	      */
	     public void setParameter(String name, Object value) {
	         if (value != null) {
	             System.out.println(value);
	             if (value instanceof String[]) {
	                 params.put(name, (String[]) value);
	             } else if (value instanceof String) {
	                 params.put(name, new String[]{(String) value});
	             } else {
	                 params.put(name, new String[]{String.valueOf(value)});
	             }
	         }
	     }
	     
	     /**
	      * 重写getParameter，代表参数从当前类中的map获取
	      * @param name
	      * @return
	      */
	     @Override
	     public String getParameter(String name) {
	         String[]values = params.get(name);
	         if(values == null || values.length == 0) {
	             return null;
	         }
	         return values[0];
	     }

	     /**
	      * 重写getParameterValues方法，从当前类的 map中取值
	      * @param name
	      * @return
	      */
	     @Override
	     public String[] getParameterValues(String name) {
	         return params.get(name);
	     }

}
