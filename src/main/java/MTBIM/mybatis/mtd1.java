package MTBIM.mybatis;
/*
由于部分时间格式不支持所用的，导入数据库需要转换成mtd
 */
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@EqualsAndHashCode
public class mtd1 {
    @ExcelProperty("id")
    private Integer  id          ;
    @ExcelProperty("设备名称")
    private String   bl           ;
    @ExcelProperty("时间")
    private String  time        ;
    @ExcelProperty("温度")
    private Double  temperature ;
    @ExcelProperty("水位")
    private Double  waterlevel  ;
    @ExcelProperty("沉降")
    private Double  displacement;
    @ExcelProperty("位移")
    private Double  settling    ;
    @ExcelProperty("位移")
    private Double  stable      ;
    @ExcelProperty("渗流")
    private Double  flow        ;

}
