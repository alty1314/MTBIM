package MTBIM.Test;

import MTBIM.mybatis.allMtdControll;
import MTBIM.mybatis.mtd;

import java.util.List;

public class allMtdSet {
    public void tesata(List<mtd> mtd){
        allMtdControll test1 = new allMtdControll();
        test1.setMtdAllControll(mtd);
    }
}
