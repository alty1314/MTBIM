package MTBIM.controller;
/**
 * 数据查询的控制层
 */

import MTBIM.mybatis.mtd;
import MTBIM.mybatis.mtd1;
import MTBIM.serivce.MtDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/data")
public class MTDController {
    @Autowired
    private MtDService MtDService1;

    @GetMapping
    public List<mtd> getAll(){
        return  MtDService1.selectAll();
    }

    @GetMapping("{currentPage}/{pageSize}")
    public List<mtd> select(@PathVariable int currentPage, @PathVariable int pageSize, mtd1 mtd){
        System.out.println(mtd);
        return MtDService1.select(currentPage,pageSize,mtd);
    }




}
