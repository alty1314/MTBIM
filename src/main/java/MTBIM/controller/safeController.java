package MTBIM.controller;
/**
 * 计算大坝整体安全度的
 * 包括子部件和总部件
 *
 */
import MTBIM.mybatis.mtd;
import MTBIM.mybatis.yuzhi;
import MTBIM.serivce.MtDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/safe_cal")
public class safeController {
    @Autowired
    private MtDService MtDService1;

    @Autowired
    private MTBIM.serivce.bookBao bookBao;

    @Autowired
    private MTBIM.serivce.yuzhi_interface yuzhi_interface;

    @GetMapping("{currentPage}")
    public Map<String,Object> getAllData(String currentPage){
        Date date ;
        if (currentPage == null){
            date = Date.valueOf("2021-12-29");
        }else {
            date = Date.valueOf(currentPage);
            System.out.println("===================="+date);
        }
        Map<String,double[]> map1 = MtDService1.safe_all("SR", "stable", date);
        Map<String,double[]> map2 = MtDService1.safe_all("DP", "displacement", date);
        Map<String,double[]> map3 = MtDService1.safe_all("ST", "settling", date);
        Map<String,double[]> map4 = MtDService1.safe_all("FL", "flow", date);
        double[] s1 =  map1.get("data");
        double[] s2 =  map2.get("data");
        double[] s3 =  map3.get("data");
        double[] s4 =  map4.get("data");
        double s5 = s1[4]*0.5+s1[4]*0.25+s1[4]*0.15+s1[4]*0.1;
        for (int i = 0; i < 5; i++) {
            System.out.println("这是s1  "+s1[i]);
            System.out.println("这是s2  "+s2[i]);
            System.out.println("这是s3  "+s3[i]);
            System.out.println("这是s4  "+s4[i]);
        }
        System.out.println(s5);

        Map<String,Object> map = new HashMap<>();
        map.put("data1",s1);
        map.put("data2",s2);
        map.put("data3",s3);
        map.put("data4",s4);
        map.put("data5",s5);
        System.out.println(currentPage);
        return map;
    }
}
