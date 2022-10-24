package MTBIM.controller;
/**
 * 阈值计算控制
 * 内容：导出运行数据和计算指定设备的运行阈值并且导出到前端
 */

import MTBIM.mybatis.mtd;
import MTBIM.mybatis.yuzhi;
import MTBIM.serivce.MtDService;
import MTBIM.serivce.bookBao;
import MTBIM.serivce.yuzhi_interface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/yuzhi_chart")
public class yuzhi_chart {
    @Autowired
    private MtDService MtDService1;
    @Autowired
    private MTBIM.serivce.bookBao bookBao;

    @Autowired
    private MTBIM.serivce.yuzhi_interface yuzhi_interface;

    @GetMapping("{currentPage}/{pageSize}")     //第一个为设备名称，第二个为属性名称
    public Map<String,Object> select(@PathVariable String currentPage,@PathVariable String pageSize){
        Map<String, Object> map2 = MtDService1.yuzhi_cal(currentPage, pageSize);
        yuzhi yuzhi_insert = new yuzhi();
        yuzhi_insert.setName(currentPage);
        yuzhi_insert.setA((Double) map2.get("zero_five"));
        yuzhi_insert.setB((Double) map2.get("four_five"));
        yuzhi_insert.setC((Double) map2.get("ninety_per"));
        yuzhi_insert.setD((Double) map2.get("seventy_per"));
        yuzhi_insert.setE((Double) map2.get("fifty_per"));

        Map<String, Object> map3 =new HashMap<>();
        map3.put("name",currentPage);
        if (yuzhi_interface.selectByMap(map3) != null){
            UpdateWrapper<yuzhi> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",currentPage);
            yuzhi_interface.update(yuzhi_insert, updateWrapper);
        }else{
            yuzhi_interface.insert(yuzhi_insert);
        }

        QueryWrapper<mtd> wrapper = new QueryWrapper<>();
        wrapper.eq("BL",currentPage);
        wrapper.orderByAsc(true,"time"); //Desc降序，重上往下，Asc升序
        IPage page = new Page(1,1000);
        IPage iPage = bookBao.selectPage(page, wrapper);
        List<mtd> records = page.getRecords();

        Object[][] Data = new Object[records.size()][2];
        Object[][] yuzhi1 = new Object[records.size()][2];
        Object[][] yuzhi2 = new Object[records.size()][2];
        Object[][] yuzhi3 = new Object[records.size()][2];

        HashMap<String ,Object> map1=new HashMap<>();
        map1.put("name",currentPage);
        List<yuzhi> yuzhis = yuzhi_interface.selectByMap(map1);

        for (int i = 0; i < records.size(); i++){

            Data[i][0]=String.valueOf(records.get(i).getTime());
            yuzhi1[i][0]=String.valueOf(records.get(i).getTime());
            yuzhi2[i][0]=String.valueOf(records.get(i).getTime());
            yuzhi3[i][0]=String.valueOf(records.get(i).getTime());
            yuzhi1[i][1]=yuzhis.get(0).getA();
            yuzhi2[i][1]=yuzhis.get(0).getB();
            yuzhi3[i][1]=yuzhis.get(0).getC();
            switch (pageSize){
                case "temperature":
                    Data[i][1]=records.get(i).getTemperature();
                    break;
                case "waterlevel":
                    Data[i][1]=records.get(i).getWaterlevel();
                    break;
                case "displacement":
                    Data[i][1]=records.get(i).getDisplacement();
                    break;
                case "settling":
                    Data[i][1]=records.get(i).getSettling();
                    break;
                case "stable":
                    Data[i][1]=records.get(i).getStable();
                    break;
                case "flow":
                    Data[i][1]=records.get(i).getFlow();
                    break;
            }
        }
        HashMap<String ,Object> map=new HashMap<>();
        map.put("data1",Data);               //设备运行数据曲线信息
        map.put("yuzhi",yuzhis);            //阈值信息
        map.put("yuzhi1",yuzhi1);           //阈值a曲线信息
        map.put("yuzhi2",yuzhi2);           //阈值b曲线信息
        map.put("yuzhi3",yuzhi3);           //阈值c曲线信息

        return map;
    }
}
