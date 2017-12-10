package com.shadow.Utils.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoadClass {
	private static Map<String,Class<?>> mapping=new LinkedHashMap<>();
	static{
		mapping.put("user", User.class);
		mapping.put("song", Song.class);
		mapping.put("topic", Topic.class);
		mapping.put("user", Bundle.class);
	}
	public Class<?> Loadclass(String parameter){
		return mapping.get(parameter);
	}
}
