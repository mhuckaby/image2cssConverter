package com.rf1m.util;

import java.util.List;

public class ValidatorUtils {
   	public static boolean isEmpty(String value){
		if(null == value || 0 == value.length()){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isEmpty(Object[] array){
		if(null == array || 0 == array.length){
			return true;
		}else{
			return false;
		}
	}

	public static boolean isEmpty(List<?> list){
		if(null == list || 0 == list.size()){
			return true;
		}else{
			return false;
		}
	}
}
