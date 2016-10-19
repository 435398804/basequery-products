package com.eenet.basequery.pri;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eenet.base.SimpleResultSet;
import com.eenet.base.biz.SimpleBizImpl;
import com.eenet.base.query.QueryCondition;
import com.eenet.basequery.pri.dao.PriTreeDaoService;
import com.eenet.common.cache.RedisClient;
import com.eenet.common.exception.RedisOPException;

public class PriTreeServiceImpl extends SimpleBizImpl implements PriTreeService {
	private PriTreeDaoService priTreeDaoService;
	private RedisClient RedisClient;

	public void setPriTreeDaoService(PriTreeDaoService priTreeDaoService) {
		this.priTreeDaoService = priTreeDaoService;
	}

	public void setRedisClient(RedisClient redisClient) {
		RedisClient = redisClient;
	}

	@Override
	public SimpleResultSet<PriTree> getPriTree(QueryCondition condition, int type) {

		SimpleResultSet<PriTree> resultSet = null;
		String id = condition.getConditions().get(0).getRangeFrom();
		SimpleResultSet cache;
		try {
			switch (type) {
			case 1:
				cache = RedisClient.getObject("PRI_TREE" + PrivilegeType.AREAPRI.name() + id, SimpleResultSet.class);
				if (cache != null) {
					return cache;
				}
				resultSet = priTreeDaoService.getAreaPriTree(condition);

				if (resultSet != null && resultSet.getResultSet().size() > 0) {
					RedisClient.setObject("PRI_TREE" + PrivilegeType.AREAPRI.name() + id, resultSet, -1);
				}
				return resultSet;
			case 2:
				cache = RedisClient.getObject("PRI_TREE" + PrivilegeType.NETWORKPRI.name() + id, SimpleResultSet.class);
				if (cache != null) {
					return cache;
				}
				resultSet = priTreeDaoService.getNetWorkPriTree(condition);

				if (resultSet != null && resultSet.getResultSet().size() > 0) {
					RedisClient.setObject("PRI_TREE" + PrivilegeType.NETWORKPRI.name() + id, resultSet, -1);
				}
				return resultSet;
			case 3:
				cache = RedisClient.getObject("PRI_TREE" + PrivilegeType.STAFFPRI.name() + id, SimpleResultSet.class);
				if (cache != null) {
					return cache;
				}
				resultSet = priTreeDaoService.getStaffPriTree(condition);

				if (resultSet != null && resultSet.getResultSet().size() > 0) {
					RedisClient.setObject("PRI_TREE" + PrivilegeType.STAFFPRI.name() + id, resultSet, -1);
				}
				return resultSet;

			case 4:
				cache = RedisClient.getObject("PRI_TREE" + PrivilegeType.CHANELPRI.name() + id, SimpleResultSet.class);
				if (cache != null) {
					return cache;
				}
				resultSet = priTreeDaoService.getChanelPriTree(condition);

				if (resultSet != null && resultSet.getResultSet().size() > 0) {
					RedisClient.setObject("PRI_TREE" + PrivilegeType.CHANELPRI.name() + id, resultSet, -1);
				}
				return resultSet;
			case 5:
				cache = RedisClient.getObject("PRI_TREE" + PrivilegeType.LEARNCENTERPRI.name() + id,
						SimpleResultSet.class);
				if (cache != null) {
					return cache;
				}
				resultSet = priTreeDaoService.getLearnCenterPriTree(condition);

				if (resultSet != null && resultSet.getResultSet().size() > 0) {
					RedisClient.setObject("PRI_TREE" + PrivilegeType.LEARNCENTERPRI.name() + id, resultSet, -1);
				}
				return resultSet;
			}
		} catch (RedisOPException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	@Override
	public Map<String, List<String>> getWholePri(String uid) {

		return priTreeDaoService.getWholePri(uid);
	}

	@Override
	public List<String> getPriTreeByType(String uid, PrivilegeType type) {
		return handlePriTree(uid, type);
	}

	private List<String> handlePriTree(String uid, PrivilegeType type) {
		List<String> list = null;
		Object cache;
		try {
			cache = RedisClient.getMapValue("WHOLE_PRI_TREE" + uid, type.name());
			if (cache != null && cache instanceof List<?>) {
				return (List<String>) cache;
			}

			if (cache == null) {
				list = priTreeDaoService.getMyPriTree(uid, type.ordinal() + 1);
				if (list != null && list.size() > 0) {
					RedisClient.addMapItem("WHOLE_PRI_TREE" + uid, type.name(), list, -1);
				}
			}
		} catch (RedisOPException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Class<PriTree> getPojoCLS() {
		return PriTree.class;
	}

	@Override
	public int savePri(String uid, String pId, String addIds, int type) {
		int result = 0;
		switch (type) {
		case 1:
			result = priTreeDaoService.saveAreaPriTree(uid, addIds.split(","));
			break;
		case 2:
			result = priTreeDaoService.saveNetWorkPriTree(uid, addIds.split(","));
			break;
		case 3:
			result = priTreeDaoService.saveStaffPriTree(uid, addIds.split(","));
			break;
		case 4:
			result = priTreeDaoService.saveChanelPriTree(uid, addIds.split(","));
			break;
		case 5:
			result = priTreeDaoService.saveLearnCenterPriTree(uid, addIds.split(","));
			break;
		}
		if (result > 0)
			addCachedPriTree(uid, pId, addIds, type);
		return result;
	}

	private void addCachedPriTree(String uid, String pId, String addIds, int type) {
		System.out.println("addCachedPriTree :　uid：" + uid + ",pId:" + pId + ",addIds:" + addIds + ",type:" + type);
		try {
			List<String> cache = RedisClient.getObject("PRI_TREE" + uid + pId + type, List.class);

			if (cache == null) {
				cache = new ArrayList<String>();
			}
			String[] arr = addIds.split(",");
			if (arr.length > 0) {
				for (int i = 0; i < arr.length; i++) {
					cache.add(arr[i]);
				}
			}

			if (cache.size() > 0) {
				RedisClient.setObject("PRI_TREE" + uid + pId + type, cache, -1);
			}

		} catch (RedisOPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int removePri(String uid, String pId, String removeIds, int type) {
		int result = 0;
		switch (type) {
		case 1:
			result = priTreeDaoService.removeAreaPriTree(uid, removeIds.split(","));
			break;
		case 2:
			result = priTreeDaoService.removeNetWorkPriTree(uid, removeIds.split(","));
			break;
		case 3:
			result = priTreeDaoService.removeStaffPriTree(uid, removeIds.split(","));
			break;
		case 4:
			result = priTreeDaoService.removeChanelPriTree(uid, removeIds.split(","));
			break;
		case 5:
			result = priTreeDaoService.removeLearnCenterPriTree(uid, removeIds.split(","));
			break;
		}
		if (result > 0) {
			removeCachedPriTree(uid, pId, removeIds, type);
		}
		return result;
	}

	private void removeCachedPriTree(String uid, String pId, String removeIds, int type) {
		System.out.println(
				"removeCachedPriTree :　uid：" + uid + ",pId:" + pId + ",removeIds:" + removeIds + ",type:" + type);
		try {
			List<String> cache = RedisClient.getObject("PRI_TREE" + uid + pId + type, List.class);

			if (cache != null) {
				String[] arr = removeIds.split(",");
				if (arr.length > 0) {
					for (int i = 0; i < arr.length; i++) {
						cache.remove(arr[i]);
					}
				}

				if (cache.size() > 0) {
					RedisClient.setObject("PRI_TREE" + uid + pId + type, cache, -1);
				} else {

					RedisClient.remove("PRI_TREE" + uid + pId + type);
				}
			}

		} catch (RedisOPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> getMyPriTree(String uid, String pid, int type) {
		System.out.println("getMyPriTree :　uid：" + uid + ",pId:" + pid + ",type:" + type);
		List<String> result = null;

		try {
			List cache = RedisClient.getObject("PRI_TREE" + uid + pid + type, List.class);
			if (cache != null) {
				System.out.println("getMyPriTree: cache" + cache);
				return cache;
				// 143 71 9.7
				// 145 71.2 9.5
				// 146.8x73.3 8.2mm

			}
			result = priTreeDaoService.getMyPriTree(uid, pid, type);

			if (result != null && result.size() > 0) {
				System.out.println("getMyPriTree: result" + result);
				RedisClient.setObject("PRI_TREE" + uid + pid + type, result, -1);
			}
		} catch (RedisOPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

}