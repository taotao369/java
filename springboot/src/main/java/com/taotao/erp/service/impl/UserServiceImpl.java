package com.taotao.erp.service.impl;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.taotao.erp.common.Model;
import com.taotao.erp.domain.User;
import com.taotao.erp.service.UserService;
@Service
@SuppressWarnings({"rawtypes","unchecked"})
public class UserServiceImpl implements UserService {

	
	@Autowired
	private RedisTemplate  redisTemplate;

	@Override
	public void addUser(User user) {
		
		Long id = getId();
		redisTemplate.execute(new RedisCallback<User>() {

			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				
				byte[] key = redisTemplate.getStringSerializer().serialize(User.class.getName()+":"+id);
				byte[] value = redisTemplate.getValueSerializer().serialize(user);
				connection.set(key, value);
				return null;
			}
		});
		
	}
	
	public Long getId()
	{
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(User.class.getName());
				return connection.incr(key);
			}
		});
	}

	@Override
	public User getUserById(Long id) {
		return (User) redisTemplate.execute(new RedisCallback<User>() {

			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				
				byte[] key = redisTemplate.getStringSerializer().serialize(User.class.getName()+":"+id);
				byte[] userBy = connection.get(key);
				return (User) redisTemplate.getValueSerializer().deserialize(userBy);
			}
		});
	}

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public void addUserByMongodb(User user) {
		user.setId(getIdMongodb());	
		mongoTemplate.insert(user);
	}

	@Override
	public User findUserByIdMongodb(Long id) {
		// TODO Auto-generated method stub
		return mongoTemplate.findById(id, User.class);
	}

	public Long getIdMongodb()
	{
		Model model = mongoTemplate.findAndModify(Query.query(Criteria.where("_id").is(User.class.getName()))
				, new Update().inc("sequence", 1), Model.class);
		if(model == null)
		{
			mongoTemplate.insert(new Model().set_id(User.class.getName()).setSequence(1L));
			return 1L;
		}
		return model.getSequence()+1;
	}

}
