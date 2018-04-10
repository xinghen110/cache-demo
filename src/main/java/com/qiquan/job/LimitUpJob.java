package com.qiquan.job;


import com.alibaba.fastjson.JSON;
import com.qiquan.model.Limited;
import com.qiquan.model.Stock;
import com.qiquan.model.ZSData;
import com.qiquan.service.LimitedService;
import com.qiquan.utils.ZSDataUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;

@Service("LimitUpJob")
public class LimitUpJob {

    private LimitedService limitedService;
    private Logger logger = LoggerFactory.getLogger(LimitUpJob.class);

    @Autowired
    public LimitUpJob(LimitedService limitedService) {
        this.limitedService = limitedService;
    }


    /**
     * 每天清空数据前，取出当天所有股票价格数据，判断涨停板、一字板，并将结果回写数据库
     * */
    @SuppressWarnings({"ConstantConditions", "unchecked", "BigDecimalMethodWithoutRoundingCalled"})
    public void doJob(Cache zsData, List<Stock> stocks){
        long start = System.currentTimeMillis();
        for(Stock s : stocks){
            Cache.ValueWrapper wrapper = zsData.get(s.getSymbol()+"."+s.getSymbolPrefix().toUpperCase());

            if(wrapper == null){
                continue;
            }

            List<ZSData> list = (List<ZSData>)wrapper.get();

            if(list == null || list.isEmpty()){
                continue;
            }
            //使用排重，清空null节点，使用linkedHashSet可以保证顺序
            LinkedHashSet<ZSData> set = new LinkedHashSet<>(list);
            list.clear();
            list.addAll(set);
            list.remove(null);

            //获取昨天收盘价
            String close_price;
            try{
                //如果昨天收盘价为空或0（新股上市），则给开盘价格
                close_price = list.get(0).getClose_price();
                if(close_price == null || close_price.equals("")){
                    close_price = list.get(0).getOpen_price();
                }

                Double d = Double.parseDouble(close_price);
                if(d == 0d){
                    d = 0.01d;   //防止除零错误
                }
                close_price = d.toString();
            }catch (Exception e){
                close_price = "0.01";
            }

            BigDecimal yesterdayClosing = new BigDecimal(close_price);

            //获取最新收盘价
            BigDecimal todayClosing = new BigDecimal(list.get(list.size()-1).getLast_px());

            //判断是否是涨停板(保留两位小数，第三位四舍五入)
            double rate = todayClosing.divide(yesterdayClosing)
                    .subtract(new BigDecimal(1))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            logger.info(s.getSymbolName()+"-today:"+todayClosing.doubleValue()+" yesterday:"+yesterdayClosing.doubleValue() + " rate:"+rate);
            if(s.getSymbolName().toUpperCase().startsWith("ST")){ //如果是ST股票，则5%为涨停
                if(rate > 0.05d){    //涨停
                    //判断是否是一字板
                    if(oneUp(todayClosing, list)){
                        //一字板
                        this.updateOne(s);
                    }else{
                        //涨停板
                        this.updateUp(s);
                    }
                }else{
                    //没有涨停则删除该股票记录
                    this.delete(s);
                }
            }else{//如果是非ST股票，则10%为涨停
                if(rate >= 0.10d){    //涨停
                    //判断是否是一字板
                    if(oneUp(todayClosing, list)){
                        //一字板
                        this.updateOne(s);
                    }else{
                        //涨停板
                        this.updateUp(s);
                    }
                }else{
                    //没有涨停则删除该股票记录
                    this.delete(s);
                }
            }

        }
        long end = System.currentTimeMillis();
        logger.info("function doJob in limited finished on " + (end - start)/1000 + " seconds");
    }
    /**
     * 判断是否是一字板
     *
     * */
    private boolean oneUp(BigDecimal todayClosing, List<ZSData> list){
        //遍历价格列表
        for(ZSData z : list){
            //获取所有的价格与今天收盘价相减
            BigDecimal subtract = todayClosing.subtract(new BigDecimal(z.getLast_px()));
            //如果有价格与今天的收盘价低超过1分钱，则说明不是一字板
            if(subtract.doubleValue() > 0.01d){
                logger.warn(z.getMin_time() + "ZSData :" + z.getProductCode() + ", closing price is "
                        + todayClosing.doubleValue() + " last_px is " + z.getLast_px()
                        + " yesterday closing price is " + z.getClose_price());
                return false;
            }
        }
        return true;
    }


    /**
     * 一字板更新数据
     * */
    private void updateOne(Stock s){
        Limited limited = limitedService.getBySymbol(s.getSymbol());

        //没有涨停记录，则需要添加
        if(limited == null){
            limited = new Limited();
            limited.setSymbol(s.getSymbolPrefix()+s.getSymbol());
            limited.setOne("1");
            limited.setSymbolname(s.getSymbolName());
            limited.setDate(ZSDataUtil.getToday());
            limited.setTemp("");
            limited.setUp("0");
            limitedService.insert(limited);
            logger.info("updateOne : " + JSON.toJSONString(limited));
            return;
        }

        //有涨停记录，则需要更新。判断字段日期，如果跟今天日期一致说明已经更新过了，不再重复更新
        if(!ZSDataUtil.getToday().equals(limited.getDate())){
            String one = limited.getOne();
            //如果之前没有值，则设置为1
            if(one == null || one.equals("")){
                limited.setOne("1");
            }else{
                //如果之前有值，则加1
                int i = Integer.parseInt(one);
                limited.setOne(Integer.toString(i+1));
            }
            limitedService.update(limited);
            logger.info("updateOne : " + JSON.toJSONString(limited));
        }
    }

    /**
     * 涨停板更新数据
     * */
    private void updateUp(Stock s){
        Limited limited = limitedService.getBySymbol(s.getSymbol());

        //没有涨停记录，则需要添加
        if(limited == null){
            limited = new Limited();
            limited.setSymbol(s.getSymbolPrefix()+s.getSymbol());
            limited.setOne("0");
            limited.setSymbolname(s.getSymbolName());
            limited.setDate(ZSDataUtil.getToday());
            limited.setTemp("");
            limited.setUp("1");
            limitedService.insert(limited);
            logger.info("updateUp : " + JSON.toJSONString(limited));
            return;
        }

        //有涨停记录，则需要更新。判断字段日期，如果跟今天日期一致说明已经更新过了，不再重复更新
        if(!ZSDataUtil.getToday().equals(limited.getDate())){
            String up = limited.getUp();
            //如果之前没有值，则设置为1
            if(up == null || up.equals("")){
                limited.setOne("1");
            }else{
                //如果之前有值，则加1
                int i = Integer.parseInt(up);
                limited.setUp(Integer.toString(i+1));
            }
            limitedService.update(limited);
            logger.info("updateUp : " + JSON.toJSONString(limited));
        }
    }

    /**
     * 涨停板更新数据
     * */
    private void delete(Stock s){
        Limited limited = limitedService.getBySymbol(s.getSymbol());

        //没有涨停记录，则返回
        if(limited == null){
            return;
        }

        //有涨停记录，由于今天没有涨停，不连续，所以删除该记录
        limitedService.delete(limited);
        logger.info("delete : " + JSON.toJSONString(limited));
    }
}
