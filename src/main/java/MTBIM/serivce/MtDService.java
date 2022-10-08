package MTBIM.serivce;

import MTBIM.mybatis.mtd;
import MTBIM.mybatis.mtd1;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 接口层
 */
public interface MtDService {
    List<mtd> selectAll();  //响应前端发送所有数据
    List<mtd> select( int currentPage,  int pageSize, mtd1 mtd);//响应前端，分页并且根据条件选择数据
    Map<String,Object> eChartSelect(int currentPage, int pageSize, mtd1 mtd);     //响应eChart返回过去选择元素过去30天的数据内容
    Map<String,Object> upload(MultipartFile file);          //响应前端，获取前端发送的文件并且保存到本地
    List<mtd1> mtdToMtd1(List<mtd> select);            //把mtd格式改为mtd1格式
    List<mtd> mtd1ToMtd(List<mtd1> selcet);
    Map<String,Object> excelWrite(String filename);
    String MysqlInsertList(List<mtd> insert);       //批量写入数据库
    Map<String,Object> yuzhi_cal(String eq_name,String eq_property);//阈值计算  （参数1设备名称；参数2设备属性）
    Map<String,Object> AHP(int[] yuzhi); //AHP计算方式
    double score_cal (String eq_name,String eq_property,double u);
    Map<String,Object> loadHousePrice(File file);
    MultiLayerNetwork model();

}
