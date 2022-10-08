package MTBIM.controller;
/**
 * 表格的控制层
 */

import MTBIM.mybatis.echart;
import MTBIM.mybatis.mtd;
import MTBIM.mybatis.mtd1;
import MTBIM.serivce.MtDService;
import MTBIM.serivce.bookBao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/echart")
public class echartController {
    @Autowired
    private MtDService MtDService1;
    @Autowired
    private bookBao bookBao;

    private MTBIM.mybatis.mtd1 mtd1 = new mtd1();

    @GetMapping
    public Map<String,Object> chart(){
        List<mtd> select = MtDService1.select(1, 5, mtd1);
        System.out.println("=========》"+select);
        List<Date> List1 = new ArrayList<>();
        List<Double> List2 = new ArrayList<>();
        List<echart> List3 = new ArrayList<>();

        for (int i = 0; i < select.size(); i++) {
            System.out.println(select.get(i).getTime());

            List1.add(select.get(i).getTime());
            List2.add(select.get(i).getTemperature());
            echart e = new echart();
            e.setTime(select.get(i).getTime());
            e.setData(select.get(i).getTemperature());
            List3.add(e);
            System.out.println(select.get(i).getTime().getClass().getTypeName());
        } System.out.println("===============》"+List1);
        Map<String, Object> map = new HashMap<>();
        map.put("time",List1);
        map.put("data",List2);
        map.put("all",List3);
        return map;
    }

    @GetMapping("{currentPage}/{pageSize}")
    public Map<String,Object> select(@PathVariable int currentPage, @PathVariable int pageSize, mtd1 mtd){
        Map<String, Object> map = new HashMap<>();
        List<Date> List1 = new ArrayList<>();
        List<Double> List2 = new ArrayList<>();

        try {
            List<mtd> select = MtDService1.select(1, pageSize, mtd);
            Object[][] a = new Object[select.size()][2];
            for (int i = 0; i < select.size(); i++){
               /* String[] k = new String[2];
                k[0]=String.valueOf(select.get(i).getTime());
                k[1]=String.valueOf(select.get(i).getTemperature());
                List.add(Arrays.deepToString(k));*/
                List1.add(select.get(i).getTime());
                List2.add(select.get(i).getTemperature());
                a[i][0]=String.valueOf(select.get(i).getTime());
                a[i][1]=select.get(i).getTemperature();
            }
            map.put("time",List1);
            map.put("data",List2);
            map.put("code",200);
            map.put("shuzu",a);
            map.put("alldata",select);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",400);
        }

        return map;
    }
}
