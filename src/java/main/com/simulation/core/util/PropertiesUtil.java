
package com.simulation.core.util;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
* Description: 配置工具读取类
* @ClassName: PropertiesUtil 
* @author Jalf
* @since 2016年5月31日 上午9:22:47 
* Copyright  983150316 All right reserved.
 */
public class PropertiesUtil {
    private static final String BUNDLE_NAME = "dictionary-entry"; //$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private PropertiesUtil() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1, parm2 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, String parm1, String parm2,
            String parm3) {
        try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),
                    new Object[] { parm1, parm2, parm3 });
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static String getString(String key,String  ... parm){
    	Object[] parms=new Object[]{parm};
    	try {
            return MessageFormat.format(RESOURCE_BUNDLE.getString(key),parms);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    public static void main(String[] args) {
		System.err.println(getString("attachPath"));
	}
}
