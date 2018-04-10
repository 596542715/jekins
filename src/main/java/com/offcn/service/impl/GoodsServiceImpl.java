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
 * ��Ʒҵ��ʵ��
 * 
 */
// �Զ���ӵ�Spring������
@Service
public class GoodsServiceImpl implements GoodsService {

	// �Զ�װ��
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
	
	
	
	// ��õ�����Ʒ����
		@Override
		@Cacheable(value="getGoodsById",key="'getGoodsById_value_'+#id")
		public Goods getGoodsById(int id) {
			return goodsdao.getGoodsById(id);
		}

		// �����Ʒ����
		@Override
		@Cacheable(value="getGoodsCount",key="'getGoodsCount_value'")
		public int getGoodsCount() {
			return goodsdao.getGoodsCount();
		}

		// ���
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int insert(Goods entity) {
			
			return goodsdao.insert(entity);
		}

		// ɾ������
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int delete(int id) {
			
			return goodsdao.delete(id);
		}

		// ɾ�����
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int deletes(int[] ids) {
			int rows = 0;
			for (int id : ids) {
				rows += delete(id);
			}
			return rows;
		}

		// ����
		@Override
		@CacheEvict(value = { "getGoodsPager", "getGoodsById","getGoodsCount" }, allEntries = true)
		public int update(Goods entity) {
			
			return goodsdao.update(entity);
		}
	// ��ҳ
	/*@Override
	public List<Goods> getGoodsPager(int pageNO, int size) {
		int skip = (pageNO - 1) * size;
		String key="GoodsPager:"+pageNO+":"+size;
		Jedis client=null;
		client=pool.getResource();
		//1\�жϻ����Ƿ����,key
		Boolean isnull = client.exists(key);
		List<Goods> list=null;
		if(isnull){
			//���������ҵ�������
			byte[] bytes = client.get(key.getBytes());
			//�����л�
			list=(List<Goods>) SerializationUtils.deserialize(bytes);
			System.out.println("�ӻ����ȡ����:================"+list);
			client.close();
			
		}else{
			//��������û������,�����ݿ��ȡ����,��ŵ�redis����һ��
			list=goodsdao.getGoodsPager(skip, size);
			//Ҫ��list���л�
			byte[] bytes = SerializationUtils.serialize(list);
			System.out.println("�򻺴�д������:================"+list);
			client.set(key.getBytes(), bytes);
			//�趨key����������
			client.expire(key.getBytes(), 300);
			client.close();
		}
		return list;
		

	}

	// ��õ�����Ʒ����
	@Override
	public Goods getGoodsById(int id) {
		return goodsdao.getGoodsById(id);
	}

	// �����Ʒ����
	@Override
	public int getGoodsCount() {
		return goodsdao.getGoodsCount();
	}

	// ���
	@Override
	public int insert(Goods entity) {
		Jedis client=null;
		client=pool.getResource();
		client.flushDB();
		client.close();
		return goodsdao.insert(entity);
	}

	// ɾ������
	@Override
	public int delete(int id) {
		Jedis client=null;
		client=pool.getResource();
		client.flushDB();
		client.close();
		return goodsdao.delete(id);
	}

	// ɾ�����
	@Override
	public int deletes(int[] ids) {
		int rows = 0;
		for (int id : ids) {
			rows += delete(id);
		}
		return rows;
	}

	// ����
	@Override
	public int update(Goods entity) {
		Jedis client=null;
		client=pool.getResource();
		client.flushDB();
		client.close();
		return goodsdao.update(entity);
	}*/
	
	

}
