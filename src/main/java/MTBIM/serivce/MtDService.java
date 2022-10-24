package MTBIM.serivce;

import MTBIM.mybatis.mtd;
import MTBIM.mybatis.mtd1;

import MTBIM.mybatis.yuzhi;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.linalg.dataset.DataSet;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * 接口层
 * 所有接口都在这里
 */
public interface MtDService {
    List<mtd> selectAll();                                       //响应前端发送所有数据
    List<mtd> select( int currentPage,  int pageSize, mtd1 mtd);//响应前端，分页并且根据条件选择数据
    Map<String,Object> eChartSelect(int currentPage, int pageSize, mtd1 mtd);     //响应eChart返回过去选择元素过去30天的数据内容
    Map<String,Object> upload(MultipartFile file);          //响应前端，获取前端发送的文件并且保存到本地
    List<mtd1> mtdToMtd1(List<mtd> select);            //把mtd格式改为mtd1格式
    List<mtd> mtd1ToMtd(List<mtd1> selcet);            //把mtd1格式改为mtd格式
    Map<String,Object> excelWrite(String filename);     //excel写入用到，已经用其他方法替代了
    String MysqlInsertList(List<mtd> insert);       //批量写入数据库
    List<yuzhi> yuzhi_getAllData();                 //获得所有的阈值数值
    List<yuzhi> yuzhi_getOneData(String eq_name,String eq_property);        //获得单个阈值数值
    Map<String,Object> yuzhi_cal(String eq_name,String eq_property);//阈值计算  （参数1设备名称；参数2设备属性）
    Map<String,Object> AHP(int[] yuzhi); //AHP计算方式
    double score_cal (String eq_name,String eq_property,double u);  //计算单个分数用的
    Map<String,Object> loadHousePrice(File file);      //没啥用，用于参考文件导入数据用的
    MultiLayerNetwork model(int in,int out,int fal);  //神经网络设置层，
    int timeCALC (Date time1,Date time2);       //计算两个时间的间隔并且返回绝对值
    double safe_cal (String eq_name,String eq_property,Date time1);
    Map<String,double[]> safe_all(String eq_name,String eq_property,Date time1); //计算一组
}