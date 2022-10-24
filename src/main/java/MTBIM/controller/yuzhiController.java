package MTBIM.controller;

import MTBIM.mybatis.yuzhi;
import MTBIM.serivce.MtDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/yuzhi")
public class yuzhiController {
    @Autowired
    private MtDService MtDService1;

    @GetMapping
    public Map<String,Object> getAllData(){
        List<yuzhi> yuzhis = MtDService1.yuzhi_getAllData();
        Map<String,Object> data = new HashMap<>();
        data.put("allData",yuzhis);
        System.out.println("---------------");
        return data;
    }
    @GetMapping("{currentPage}")
    public Map<String,Object> select(@PathVariable String currentPage){
        List<yuzhi> oneData = MtDService1.yuzhi_getOneData(currentPage,"null");
        Map<String,Object> data = new HashMap<>();
        data.put("oneData",oneData);
        System.out.println("++++++++++");
        return data;
    }
}
