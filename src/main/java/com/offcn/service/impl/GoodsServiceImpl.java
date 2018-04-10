package com.offcn.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import com.offcn.mapper.GoodsDAO;
import com.offcn.po.Goods;
import com.offcn.service.GoodsService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 商品业务实现
 * 
 */
// 自动添加到Spring容器中
@Service
public class GoodsServiceImpl implements GoodsService {

	// 自动装配
	@Resource
	GoodsDAO goodsdao;
	
	/*@Autowired
	JedisPool pool;*/

	
	@Override
	@Cacheable(value="getGoodsPager",key="'getGoodsPager_value_'+#pageNO+'_'+#size")
	public List<Goods> getGoodsPager(int pageNO, int size) {
		int skip = (pageNO - 1) * size;
		return goodsdao.getGoodsPager(skip, size);
		

	}
	
	
	
	// 获得单个产品对象
		@Override
		@Cacheable(value="getGoodsById",key="'getGoodsById_value_'+#id")
		public Goods getGoodsById(int id) {
			return goodsdao.getGoodsById(id);
		}

		// 获得商品总数
		@Override
		@Cacheable(value="getGoodsCount",key="'getGoodsCount_value'")
		public int getGoodsCount() {
			return goodsdao.getGoodsCount();
		}

		// 添加
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int insert(Goods entity) {
			
			return goodsdao.insert(entity);
		}

		// 删除单个
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int delete(int id) {
			
			return goodsdao.delete(id);
		}

		// 删除多个
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int deletes(int[] ids) {
			int rows = 0;
			for (int id : ids) {
				rows += delete(id);
			}
			return rows;
		}

		// 更新
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int update(Goods entity) {
			
			return goodsdao.update(entity);
		}
	// 分页
	/*@Override
	public List<Goods> getGoodsPager(int pageNO, int size) {
		int skip = (pageNO - 1) * size;
		String key="GoodsPager:"+pageNO+":"+size;
		Jedis client=null;
		client=pool.getResource();
		//1\判断缓存是否存在,key
		Boolean isnull = client.exists(key);
		List<Goods> list=null;
		if(isnull){
			//缓存里面找到了数据
			byte[] bytes = client.get(key.getBytes());
			//反序列化
			list=(List<Goods>) SerializationUtils.deserialize(bytes);
			System.out.println("从缓存读取数据:================"+list);
			client.close();
			
		}else{
			//缓存里面没有数据,从数据库读取数据,存放到redis缓存一份
			list=goodsdao.getGoodsPager(skip, size);
			//要把list序列化
			byte[] bytes = SerializationUtils.serialize(list);
			System.out.println("向缓存写入数据:================"+list);
			client.set(key.getBytes(), bytes);
			//设定key的声明周期
			client.expire(key.getBytes(), 300);
			client.close();
		}
		return list;
		

	}

	// 获得单个产品对象
	@Override
	public Goods getGoodsById(int id) {
		return goodsdao.getGoodsById(id);
	}

	// 获得商品总数
	@Override
	public int getGoodsCount() {
		return goodsdao.getGoodsCount();
	}

	// 添加
	@Override
	public int insert(Goods entity) {
		Jedis client=null;
		client=pool.getResource();
		client.flushDB();
		client.close();
		return goodsdao.insert(entity);
	}

	// 删除单个
	@Override
	public int delete(int id) {
		Jedis client=null;
		client=pool.getResource();
		client.flushDB();
		client.close();
		return goodsdao.delete(id);
	}

	// 删除多个
	@Override
	public int deletes(int[] ids) {
		int rows = 0;
		for (int id : ids) {
			rows += delete(id);
		}
		return rows;
	}

	// 更新
	@Override
	public int update(Goods entity) {
		Jedis client=null;
		client=pool.getResource();
		client.flushDB();
		client.close();
		return goodsdao.update(entity);
	}*/
	
	

}
