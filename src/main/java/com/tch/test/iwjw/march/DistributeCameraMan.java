package com.tch.test.iwjw.march;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tch.test.common.httpcomponent.httpclient.utils.HttpUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DistributeCameraMan {

	public static void main(String[] args) throws Exception {
		new DistributeCameraMan().handleNotPickedUpTask(null, null);
	}
	
    public void handleNotPickedUpTask(Date fromTime, Date toTime) throws Exception {
        //任务超过30分钟未被领取，任务分配给板块内“已预约”任务数量最少的组员
        Set<Long> cityList = new HashSet<Long>();//任务的城市列表
        //1.查询超过30分钟未被领取的任务(key:板块，value：该板块的未被领取的任务列表)
        Map<Long, List<HouseReserveTask>> townTaskMap = getNeedHandleTaskMap(fromTime, toTime, cityList);
        if(MapUtils.isEmpty(townTaskMap)){
        	log.info("没有需要处理的未领取的任务");
        	return;
        }
        Set<Long> townIds = townTaskMap.keySet();//keySet为全部待处理的任务的板块
        //2.查询该板块内“已预约”任务数量最少的组员(key:板块，value：这个版块的摄影师id列表)
        Map<Long, Set<Long>> townCameraManMap = getTownCameraManMap(townIds, cityList);
        System.out.println(townCameraManMap);
        if(MapUtils.isEmpty(townCameraManMap)){
        	log.info("板块-摄影师映射map为空");
        	return;
        }
        //查询这些摄影师的“已预约”任务数量(key:摄影师id， value：摄影师“已预约”任务数量)
        Map<Long, Integer> cameraManTaskNumMap = getCameraManTaskNumMap(townCameraManMap);
        //3.将该任务分配给该组员
        for(Long townId : townIds){
            //按照板块分配task给摄影师
            distributeTask(townId, townTaskMap, townCameraManMap, cameraManTaskNumMap);
        }
    }
    
    /**
     * 按照板块分配task给摄影师
     * @param townId 板块id
     * @param townTaskMap key:板块，value：该板块的未被领取的任务列表
     * @param townCameraManMap key:板块，value：这个版块的摄影师id列表
     * @param cameraManTaskNumMap 摄影师任务数量缓存（key:摄影师id， value：摄影师“已预约”任务数量）
     */
    private void distributeTask(Long townId, Map<Long, List<HouseReserveTask>> townTaskMap, 
                                Map<Long, Set<Long>> townCameraManMap, Map<Long, Integer> cameraManTaskNumMap) {
        //任务分配给板块内“已预约”任务数量最少的组员·
        for(HouseReserveTask task : townTaskMap.get(townId)){
            try {
                //从给定的摄影师中找出任务数最少的
                Long cameraManId = getLeastTaskCameraManId(cameraManTaskNumMap, townCameraManMap.get(townId));
                if(cameraManId == null){
                    log.info("板块{}下未找到可分配的摄影师", townId);
                    continue;
                }
                //将该任务分配给该摄影师
                //将缓存中该摄影师的任务数量+1
                cameraManTaskNumMap.put(cameraManId, cameraManTaskNumMap.get(cameraManId) + 1);
            } catch (Exception e) {
                //打印日志，但不影响下一个任务的分配
                e.printStackTrace();
                log.error(String.format("分配任务%d异常：", task.getId()), e);
            }
        }
    }
    
    /**
     * 从给定的摄影师中找出任务数最少的
     * @param cameraManTaskNumMap 摄影师任务数量缓存（key:摄影师id， value：摄影师“已预约”任务数量）
     * @param cameraMen 给定的摄影师id列表
     * @return
     */
    private Long getLeastTaskCameraManId(Map<Long, Integer> cameraManTaskNumMap, Set<Long> cameraMen) {
        if(CollectionUtils.isEmpty(cameraMen)){
            log.info("cameraMen为空，cameraManTaskNumMap{}", cameraManTaskNumMap);
            return null;
        }
        if(MapUtils.isEmpty(cameraManTaskNumMap)){
            log.info("cameraManTaskNumMap为空，cameraMen{}", cameraMen);
            return null;
        }
        boolean isFirst = true;
        Long cameraManId = null;
        Integer taskNum = null;
        for(Map.Entry<Long, Integer> entry : cameraManTaskNumMap.entrySet()){
            if(! cameraMen.contains(entry.getKey())){
                //不在给定的摄影师中，不算在内
                continue;
            }
            if(isFirst){//suppose the first one is the smallest.
                isFirst = false;
                cameraManId = entry.getKey();
                taskNum = entry.getValue();
                continue;
            }else{
                if(entry.getValue() < taskNum){
                    //current taskNum is smaller
                    taskNum = entry.getValue();
                    cameraManId = entry.getKey();
                }
            }
        }
        return cameraManId;
    }
    
    /**
     * 查询摄影师的“已预约”任务数量
     * @param cameraManId
     * @return
     */
    private int getCameraManTaskNum(Long cameraManId){
        return 1;
    }
    
    /**
     * 查询这些摄影师的“已预约”任务数量(key:摄影师id， value：摄影师“已预约”任务数量)
     * @return
     */
    private Map<Long, Integer> getCameraManTaskNumMap(Map<Long, Set<Long>> townCameraManMap) {
        Map<Long, Integer> result = new HashMap<Long, Integer>();
        Set<Long> cameraManIds = getCameraManIds(townCameraManMap);
        if(CollectionUtils.isEmpty(cameraManIds)){
            log.info("摄影师列表cameraManIds为空");
            return result;
        }
        //这里为了防止摄影师数量太多，导致慢SQL，针对每个摄影师执行一次查询
        for(Long cameraManId : cameraManIds){
            //查询摄影师的“已预约”任务数量
            result.put(cameraManId, getCameraManTaskNum(cameraManId));
        }
        return result;
    }
    
    /**
     * 获取全部摄影师id
     * @param townCameraManMap
     * @return
     */
    private Set<Long> getCameraManIds(Map<Long, Set<Long>> townCameraManMap) {
        Set<Long> cameraManIds = new HashSet<Long>();
        //townCameraManMap key:板块，value：这个版块的摄影师id列表
        Collection<Set<Long>> cameraMenIdCollection = townCameraManMap.values();
        if(CollectionUtils.isEmpty(cameraMenIdCollection)){
            return cameraManIds;
        }
        for(Set<Long> set : cameraMenIdCollection){
            cameraManIds.addAll(set);
        }
        return cameraManIds;
    }
    
    /**
     * 查询板块-摄影师信息（key:板块，value：这个版块的摄影师id列表）
     * @param townIds 所有感兴趣的板块列表
     * @param cityIds 所有感兴趣的板块所属的城市列表
     * @return
     * @throws Exception 
     */
    private Map<Long, Set<Long>> getTownCameraManMap(Set<Long> townIds, Set<Long> cityIds) throws Exception {
        Map<Long, Set<Long>> result = new HashMap<Long, Set<Long>>();
        if(CollectionUtils.isEmpty(cityIds)){
            log.info("城市列表为空。。。");
            return result;
        }
        for(Long cityId : cityIds){
            //查询该城市下全部摄影师列表
        	String allSurveyStr = HttpUtils.doGet("http://121.40.129.114:8116/UumSOA/agent/getAllSurveys.action?cityId=2");
            @SuppressWarnings("unchecked")
			List<JSONObject> agentAreaOrgVos = JSON.parseObject(allSurveyStr).getObject("data", List.class);
            if(CollectionUtils.isEmpty(agentAreaOrgVos)){
                log.info("城市{}的摄影师为空", cityId);
                continue;
            }
            for(JSONObject agentAreaOrgVo : agentAreaOrgVos){
                //key:板块，value：这个版块的摄影师id列表
            	String groupInfoStr = HttpUtils.doGet("http://121.40.129.114:8116/UumSOA/agent/getRelationByGroupIdAndType.action?groupId=" + agentAreaOrgVo.get("agentGroupId") + "&groupType=1");
            	@SuppressWarnings("unchecked")
				List<JSONObject> estateVos = JSON.parseObject(groupInfoStr).getObject("data", List.class);
                //List<GroupAreaRelationVO> estateVos = JSON.parseObject(groupInfoStr, new TypeReference<List<GroupAreaRelationVO>>(){});
                if(CollectionUtils.isEmpty(estateVos)){
                    log.info("estateVos为空, groupId{}", agentAreaOrgVo.get("agentGroupId"));
                    continue;
                }
                for(JSONObject estateVo : estateVos){
                    //只关心未领取的任务所属的板块范围，不在该范围的，忽略
                    if(!townIds.contains(Long.parseLong(String.valueOf(estateVo.get("areaId"))))){
                        continue;
                    }
                    Set<Long> cameraManIds = result.get(Long.parseLong(String.valueOf(estateVo.get("areaId"))));
                    if(cameraManIds == null){
                        cameraManIds = new HashSet<Long>();
                        result.put(Long.parseLong(String.valueOf(estateVo.get("areaId"))), cameraManIds);
                    }
                    //将该摄影师添加到负责该板块的摄影师列表中
                    cameraManIds.add(Long.parseLong(String.valueOf(agentAreaOrgVo.get("agentId"))));
                }
            }
        }
        return result;
    }
    
    /**
     * 查询指定时间内未被领取的任务（key:板块，value：该板块的未被领取的任务列表）
     * @return
     */
    private Map<Long, List<HouseReserveTask>> getNeedHandleTaskMap(Date fromTime, Date toTime, Set<Long> cityList) {
        Map<Long, List<HouseReserveTask>> result = new HashMap<>();
        List<HouseReserveTask> tasks = new ArrayList<>();
        HouseReserveTask task = new HouseReserveTask();
        task.setTownId(23L);
        task.setCityId(2L);
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        tasks.add(task);
        result.put(23L, tasks);
        cityList.add(2L);
        return result;
    }
	
}
