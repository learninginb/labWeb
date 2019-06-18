package com.simulation.service.sys.impl;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simulation.common.base.BaseRedisDao;
import com.simulation.service.sys.RedisService;

@Transactional
@Service("redisService")
public class RedisServiceImpl extends BaseRedisDao<String, String> implements
		RedisService {
	/**
	 * 新增
	 * 
	 * @param
	 * @return
	 */
	@Override
	public boolean add(final String key, final String value) {
		// TODO Auto-generated method stub
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				// TODO Auto-generated method stub
				RedisSerializer<String> serializer = getRedisSerializer();
				// 序列化
				byte[] key2 = serializer.serialize(key);
				byte[] value2 = serializer.serialize(value);
				return connection.setNX(key2, value2);
			}
		});
		return result;
	}

	/**
	 * 批量新增 使用pipeline方式
	 * 
	 * @param
	 * @return
	 */
	@Override
	public boolean add(final List<String> key, final List<String> values) {
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				for (int i = 0; i < values.size(); i++) {
					byte[] key2 = serializer.serialize(key.get(i));
					byte[] value2 = serializer.serialize(values.get(i));
					connection.setNX(key2, value2);
				
				}
				return true;
			}
		}, false, true);
		return result;
	}

	@Override
	public void delete(String key) {
		List<String> list = new ArrayList<>();
		list.add(key);
		delete(list);
	}

	@Override
	public void delete(final List<String> keys) {
		redisTemplate.execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				// TODO Auto-generated method stub
				RedisSerializer<String> serializer = getRedisSerializer();
				// 序列化
				for (String k : keys) {
					byte[] key = serializer.serialize(k);
					connection.del(key);
				}
				return true;
			}
		});

	}

	@Override
	public boolean update(final String key, final String value) {
		// TODO Auto-generated method stub
		if (get(key) == null) {
			throw new NullPointerException("数据行不存在, key = " + key);
		}
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key2 = serializer.serialize(key);
				byte[] value2 = serializer.serialize(value);
				connection.set(key2, value2);
				return true;
			}
		});
		return result;
	}

	@Override
	public String get(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key2 = serializer.serialize(key);
				byte[] value = connection.get(key2);
				if (value == null) {
					return null;
				}
				String value2 = serializer.deserialize(value);
				return value2;
			}
		});
		return result;
	}
	
	
	/**
     * @param pattern
     * @return
     */
	@Override
	public Set<String> setkeys(String pattern) {
        return redisTemplate.keys(pattern);

    }

    /**
     * @param key
     * @return
     */
	@Override
    public boolean exists(final String key) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * @return
     */
	@Override
    public String flushDB() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * @return
     */
	@Override
    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * @return
     */
    public String ping() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }
	
	@Override
	public List<String> find(final String key) {
		String result = redisTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(RedisConnection connection)
					throws DataAccessException {
				RedisSerializer<String> serializer = getRedisSerializer();
				List<String> list=new ArrayList<String>();
				byte[] key2 = serializer.serialize(key+"*");
				Set<byte[]> value = connection.keys(key2);
				StringBuilder result = new StringBuilder();
				for(byte[] tmp:value){
					key2=connection.get(tmp);
					if(key2!=null){
						 result.append(serializer.deserialize(key2)+"zqxj");
					}
				}
				//String value2 = serializer.deserialize(value);
				return result.toString();
			}
		});
		if(result==null||result.length()<4){
			return null;
		}
		result=result.substring(0,result.length()-4);
		String str[] = result.split("zqxj");
		return Arrays.asList(str);
	}

	@Override
	public void deleteKeys(final String key) {
		
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection)
					throws DataAccessException {
				// TODO Auto-generated method stub
				RedisSerializer<String> serializer = getRedisSerializer();
				byte[] key2 = serializer.serialize(key+"*");
				Set<byte[]> value = connection.keys(key2);
				// 序列化
				for (byte[] k : value) {
					connection.del(k);
				}
				return true;
			}
		});
	}

}
