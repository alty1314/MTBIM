package MTBIM.mybatis;

import java.util.List;

//忘了哪里用到的。当时没注释，但是好像是用到了，好像是全局变量的一部分，别动。
public class allMtdControll {
    public static List<mtd> mtdAllControll ;
    public static List<mtd1> mtdAllControll1 ;

    public static List<mtd> getMtdAllControll() {
        return mtdAllControll;
    }
    public static void setMtdAllControll(List<mtd> mtdAllControll) {
        allMtdControll.mtdAllControll = mtdAllControll;
    }

    public static List<mtd1> getMtdAllControll1() {
        return mtdAllControll1;
    }

    public static void setMtdAllControll1(List<mtd1> mtdAllControll1) {
        allMtdControll.mtdAllControll1 = mtdAllControll1;
    }
}
