package hbase.springdata.util;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JsonUtil辅助类
 * 
 * @author Geosmart
 */
public class JsonUtil {
	private static Logger log = LoggerFactory.getLogger("JsonUtil数据处理：");

	/**
	 * 将C#中的Dicionaty转换成HashMap
	 * 
	 * @param dictionaryParam
	 * @return
	 */
	public static Map<String, Object> ConvertDictionary2Map(
			List<Map<String, Object>> dictionaryParam) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		for (Map<String, Object> map : dictionaryParam) {
			resultMap.put(map.get("Key").toString(), map.get("Value"));
		}
		return resultMap;
	}

	/**
	 * 将C#中的Dicionaty转换成指定POJO
	 * 
	 * @param dictionaryParam
	 * @param typeRef 类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T ConvertDictionary2POJO(List<Map<String, Object>> dictionaryParam,
			TypeReference<T> typeRef) {
		try {
			Map<String, Object> resultMap = ConvertDictionary2Map(dictionaryParam);
			String jsonStr = convertObject2String(resultMap);
			return (T) getObjectMapperWithNull().readValue(jsonStr, typeRef);
		} catch (JsonParseException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (IOException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		}
	}

	/**
	 * 将Map转换成指定POJO
	 * 
	 * @param dictionaryParam
	 * @param typeRef 类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T ConvertStrMap2POJO(Map<String, String> map, TypeReference<T> typeRef) {
		try {
			String jsonStr = convertObject2String(map);
			return (T) getObjectMapperWithNull().readValue(jsonStr, typeRef);
		} catch (JsonParseException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (IOException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		}
	}

	/**
	 * 将Map转换成指定POJO
	 * 
	 * @param dictionaryParam
	 * @param typeRef 类型
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T ConvertMap2POJO(Map<String, Object> map, TypeReference<T> typeRef) {
		try {
			String jsonStr = convertObject2String(map);
			return (T) getObjectMapperWithNull().readValue(jsonStr, typeRef);
		} catch (JsonParseException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (IOException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		}
	}

	/**
	 * 将MapList转换成指定POJOList
	 * 
	 * @param mapList
	 * @param typeRef 类型
	 * @return
	 */
	public static <T> List<T> ConvertMapList2POJOList(List<Map<String, Object>> mapList,
			TypeReference<T> typeRef) {
		List<T> result = new ArrayList<T>();
		try {
			for (Map<String, Object> map : mapList) {
				result.add(ConvertMap2POJO(map, typeRef));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将sourceObj和targetObj对象属性合并，targetObj属性会覆盖sourceObj属性
	 * 
	 * @param targetObj 目标对象
	 * @param sourceObj 源对象
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map mergeObject(Object targetObj, Object sourceObj) {
		Map map = new HashMap<String, Object>();
		try {
			map.putAll(convertEntityObj2Map(sourceObj, true));
			map.putAll(convertEntityObj2Map(targetObj, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 将sourceObj和targetObj对象属性合并，targetObj属性会覆盖sourceObj属性
	 * 
	 * @param targetObj 目标Enitty
	 * @param List<sourceObj> 源Enitty
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map mergeObject(Object targetObj, List<Object> sourceObjs) {
		Map map = new HashMap<String, Object>();
		try {
			for (Object object : sourceObjs) {
				if (object instanceof Map) {
					map.putAll((Map) object);
				} else {
					map.putAll(convertEntityObj2Map(object, true));
				}
			}
			map.putAll(JsonUtil.convertEntityObj2Map(targetObj, true));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/***
	 * 读取客户端传递的form对象,转换成json字符串
	 * 
	 * @param request
	 * @return json字符串
	 */
	// public static String readJSONString(HttpServletRequest request) {
	// StringBuffer json = new StringBuffer();
	// String line = null;
	// try {
	// BufferedReader reader = request.getReader();
	// while (reader != null && (line = reader.readLine()) != null) {
	// json.append(line);
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// log.error(e.toString());
	// }
	// return json.toString();
	// }

	/**
	 * Entity转换成Map
	 * 
	 * @param entityObj java对象
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> convertEntityObj2Map(Object entityObj,
			boolean isUserAnnotations) {
		ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
		// 禁用注释，如jsonIgnore
		objectMapper.configure(MapperFeature.USE_ANNOTATIONS, isUserAnnotations);
		Map<String, Object> objMap = new HashMap<String, Object>();
		try {
			String jsonString = objectMapper.writeValueAsString(entityObj);
			objMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objMap;
	}

	/**
	 * Entity转换成Map
	 * 
	 * @param entryObj java对象
	 * @return Map<String, String>
	 */
	public static Map<String, String> convertEntityObj2StrMap(Object entryObj) {
		ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
		// 禁用注释，如jsonIgnore
		objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
		Map<String, String> objMap = new HashMap<String, String>();
		try {
			String jsonString = objectMapper.writeValueAsString(entryObj);
			objMap = objectMapper.readValue(jsonString, new TypeReference<Map<String, String>>() {
			});
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objMap;
	}

	/**
	 * 从json字符串中获取指定键值
	 * 
	 * @param jsonString json字符串
	 * @param keyStr 属性
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public static Object getObjectFromJSONString(String jsonString, String keyStr) {
		Object keyValue = null;
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapper();
			Map<String, Object> maps = objectMapper.readValue(jsonString, Map.class);
			Set<String> key = maps.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String field = iter.next();
				if (field == keyStr) {
					keyValue = maps.get(field);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return keyValue;
	}

	/**
	 * 从json字符串中获取指定键值转换成List（用以处理可能是string，可能是List<string>的参数）
	 * 
	 * @param jsonString json字符串
	 * @param keyStr 属性
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getListFromJSONString(String jsonString, String keyStr) {
		List<String> list = new ArrayList<String>();
		try {
			Object idsObject = getObjectFromJSONString(jsonString, keyStr);
			if (idsObject instanceof String) {
				list.add(String.valueOf(idsObject));
			} else if (idsObject instanceof ArrayList) {
				list = (ArrayList<String>) idsObject;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return list;
	}

	/**
	 * 从json字符串中获取Map的字符串
	 * 
	 * @param jsonString json字符串
	 * @param keyStr 属性
	 * @return 序列化MapStr
	 */
	@SuppressWarnings("unchecked")
	public static String getMapStrFromJSONStr(String jsonString, String keyStr) {
		String keyValue = null;
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapper();
			Map<String, Object> maps = objectMapper.readValue(jsonString, Map.class);
			Set<String> key = maps.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String field = iter.next();
				if (field == keyStr) {
					keyValue = convertObject2String(maps.get(field));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return keyValue;
	}

	/**
	 * 从json字符串中移除指定键值
	 * 
	 * @param jsonString json字符串
	 * @param keyStr 属性
	 * @return 值
	 */
	@SuppressWarnings("unchecked")
	public static void removeFieldFromJSONString(String jsonString, String keyStr) {
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapper();
			Map<String, Object> maps = objectMapper.readValue(jsonString, Map.class);
			Set<String> key = maps.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				String field = iter.next();
				if (field == keyStr) {
					iter.remove();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
	}

	/**
	 * 将Map<String, List<Object>>转换成Map<String, List<classType>>
	 * 
	 * @param objectMap Map<String, List<Object>>
	 * @return Map<String, List<classType>>
	 */
	public static Map<String, List<Object>> convertGenericMap2EntityMap(
			Map<String, List<Object>> objectMap) {
		Map<String, List<Object>> entityMap = new HashMap<String, List<Object>>();
		// 根据entity名称将List<Object>转换List<Entity>
		for (String key : objectMap.keySet()) {
			Class<?> entityClass;
			try {
				entityClass = Class.forName(key);
				List<Object> entityObjects = new ArrayList<Object>();
				for (Object obj : objectMap.get(key)) {
					Object entityObject = JsonUtil.getObjectMapper().convertValue(obj, entityClass);
					entityObjects.add(entityObject);
				}
				entityMap.put(key, entityObjects);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				log.debug("Class类型不存在：" + e.toString());
			}
		}
		return entityMap;
	}

	/**
	 * 将JSON字符串转换成目标对象List
	 * 
	 * @param jsonString JSOn字符串，可为单个对象/多个对象的List<Map<String, Object>>
	 * @param className 需要转换的实体类的全名
	 * @return List<Object>
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<Object> convertString2EntityList(String jsonString, String className) {
		// jsonString，单个对象/多个对象转换;Entity:Map<String, Object>
		List<Object> entityObjects = new ArrayList<Object>();
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
			List<Map<String, Object>> objectMap = objectMapper.readValue(jsonString, List.class);
			for (Map<String, Object> map : objectMap) {
				Class<?> entityClass = Class.forName(className);
				Object entityObject = objectMapper.convertValue(map, entityClass);
				entityObjects.add(entityObject);
			}
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entityObjects;
	}

	/**
	 * 将JSON字符串转换成目标对象List
	 * 
	 * @param objectMapper 配置好的objectMapper（设置date序列化参数等）
	 * @param jsonString JSOn字符串，可为单个对象/多个对象的List<Map<String, Object>>
	 * @param className 需要转换的实体类的全面
	 * @return List<Object>
	 */
	@SuppressWarnings({ "unchecked" })
	public static List<Object> convertString2EntityList(ObjectMapper objectMapper,
			String jsonString, String className) {
		// jsonString，单个对象/多个对象转换;Entity:Map<String, Object>
		List<Object> entityObjects = new ArrayList<Object>();
		try {
			List<Map<String, Object>> objectMap = objectMapper.readValue(jsonString, List.class);
			for (Map<String, Object> map : objectMap) {
				Class<?> entityClass = Class.forName(className);
				Object entityObject = objectMapper.convertValue(map, entityClass);
				entityObjects.add(entityObject);
			}
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entityObjects;
	}

	/**
	 * 将JSON字符串转换成目标对象List
	 * 
	 * @param jsonString JSOn字符串，可为单个对象/多个对象的List<Map<String, Object>>
	 * @param className 需要转换的实体类的全面
	 * @return List<Object>
	 */
	@SuppressWarnings({ "unchecked" })
	public static Set<Object> convertString2EntitySet(String jsonString, String className) {
		// jsonString，单个对象/多个对象转换;Entity:Map<String, Object>
		Set<Object> entityObjects = new HashSet<Object>();
		try {
			List<Map<String, Object>> objectMap = JsonUtil.getObjectMapper().readValue(jsonString,
					List.class);
			for (Map<String, Object> map : objectMap) {
				Class<?> entityClass = Class.forName(className);
				Object entityObject = JsonUtil.getObjectMapper().convertValue(map, entityClass);
				entityObjects.add(entityObject);
			}
		} catch (JsonParseException e1) {
			e1.printStackTrace();
		} catch (JsonMappingException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return entityObjects;
	}

	/**
	 * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大
	 * 
	 * @param <T>
	 * @param jsonString JSON字符串
	 * @param typeRef TypeReference,例如: new TypeReference< List<FamousUser> >(){}
	 * @return 泛型对象，eg:List
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T convertString2GenericObject(String jsonString, TypeReference<T> typeRef) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (T) mapper.readValue(jsonString, typeRef);
		} catch (JsonParseException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (JsonMappingException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		} catch (IOException e) {
			throw new RuntimeException("Deserialize from JSON failed.", e);
		}
	}

	/**
	 * json字符串转换成Map
	 * 
	 * @param variableParam json字符串
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertString2Map(String variableParam) {
		// 任务处理
		Map<String, Object> variables = new HashMap<String, Object>();
		try {
			if (StringUtils.isNotEmpty(variableParam)) {
				variables = new ObjectMapper().readValue(variableParam, Map.class);
				// JsonNode startJSON = new ObjectMapper().readTree(variableParam);
				// Iterator<String> itName = startJSON.getFieldNames();
				// while (itName.hasNext())
				// {
				// String name = itName.next();
				// JsonNode valueNode = startJSON.path(name);
				// variables.put(name, valueNode.asText());
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return variables;
	}

	/**
	 * 从Map中获取指定键值
	 * 
	 * @param variables Map对象
	 * @param keyStr 属性值
	 * @return 键值
	 */
	public static String getFieldFromMap(Map<String, Object> variables, Object keyStr) {
		String keyValue = null;
		try {
			if (variables != null) {
				Set<String> key = variables.keySet();
				Iterator<String> iter = key.iterator();
				while (iter.hasNext()) {
					Object field = iter.next();
					if (field.equals(keyStr)) {
						Object filedValue = variables.get(field);
						if (filedValue != null) {
							keyValue = filedValue.toString();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.toString());
		}
		return keyValue;
	}

	/**
	 * Object转换成json序列化对象
	 * 
	 * @param object java对象
	 * @return 序列化json字符串
	 */
	public static String convertObject2String(Object object) {
		String formData = null;
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
			formData = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return formData;
	}

	/**
	 * Object转换成json序列化对象
	 * 
	 * @param object java对象
	 * @param isUserAnnotations 是否启用标注参数
	 * @return 序列化json字符串
	 */
	public static String convertObject2String(Object object, boolean isUserAnnotations) {
		String formData = null;
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
			// 禁用注释，如jsonIgnore
			objectMapper.configure(MapperFeature.USE_ANNOTATIONS, isUserAnnotations);
			formData = objectMapper.writeValueAsString(object);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return formData;
	}

	/**
	 * string转换成java对象 反序列化json字符串
	 * 
	 * @param object java对象
	 * @return 对象List
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, List<Object>> convertString2Object(String jsonString) {
		Map<String, List<Object>> object = null;
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
			object = objectMapper.readValue(jsonString, Map.class);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return object;
	}

	/**
	 * string转换成java对象 反序列化json字符串
	 * 
	 * @param object java对象
	 * @return 对象List
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertString2MapObject(String jsonString,
			boolean isUserAnnotations) {
		Map<String, Object> object = null;
		try {
			ObjectMapper objectMapper = JsonUtil.getObjectMapperWithNull();
			// 禁用注释，如jsonIgnore
			objectMapper.configure(MapperFeature.USE_ANNOTATIONS, isUserAnnotations);
			object = objectMapper.readValue(jsonString, Map.class);
		} catch (Exception e) {
			log.error(e.toString());
		}
		return object;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	/**
	 * 由于json序列化时，对于list中只有一个值时式String类型，需要手动转换成List<string>
	 * @param objMap Map<String, Object>
	 * @return Map<String, List<String>>
	 */
	public static Map<String, List<String>> convertMapOfStringToList(Map<String, Object> objMap) {
		Map<String, List<String>> selectedIds = new HashMap<String, List<String>>();
		Iterator it = objMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = String.valueOf(entry.getKey());
			Object value = entry.getValue();

			List<String> ids = new ArrayList<String>();
			if (value instanceof String) {
				ids.add(String.valueOf(value));
			} else if (value instanceof ArrayList) {
				ids = (ArrayList<String>) value;
			}
			selectedIds.put(key, ids);
		}
		return selectedIds;
	}

	/***
	 * 初始化ObjectMapper
	 * 
	 * @return ObjectMapper
	 */
	public static ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD,
				Visibility.ANY);
		// 去除不存在的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 空字符串转换null对象
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		// 去除值为null的值
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
		// 默认时间戳改成自定义日期格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dFormat);
		return objectMapper;
	}

	public static ObjectMapper SetObjectMapperDateFormat(ObjectMapper objectMapper,
			DateFormat dFormat) {
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		objectMapper.setDateFormat(dFormat);
		return objectMapper;
	}

	/***
	 * 保留空值的ObjectMapper
	 * 
	 * @return ObjectMapper
	 */
	public static ObjectMapper getObjectMapperWithNull() {
		ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD,
				Visibility.ANY);
		objectMapper.setSerializationInclusion(Include.ALWAYS);
		// 去除不存在的属性
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		// 空字符串转换null对象
		objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
		// 不去除值为null的值
		objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
		// 默认时间戳改成自定义日期格式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		DateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		objectMapper.setDateFormat(dFormat);
		return objectMapper;
	}
}
