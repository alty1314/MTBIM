package MTBIM.Test;

import MTBIM.mybatis.allMtdControll;
import MTBIM.mybatis.mtd1;

import java.util.List;

/**
 * 储存你的数据内容
 * 问题本部分内容不能收到springboot管理，导致导进去的包不能用
 *使用全局的变量来接管这部分导入的数据，然后在外部调用springboot的功能进行导入
 **/

public class DemoDAO {
   /* @Autowired
    private bookBao1 bookBao ;
    @Autowired
    private MtDService MtDService;*/
    public List<mtd1> save(List<mtd1> list) {
        // 如果是mybatis,尽量别直接调用多次insert,自己写一个mapper里面新增一个方法batchInsert,所有数据一次性插入
       /* System.out.println("==================="+"\n"+list);*/
        allMtdControll test = new allMtdControll();
        test.setMtdAllControll1(list);

        return list;
    }
}