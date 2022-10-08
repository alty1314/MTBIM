package MTBIM.mybatis;
/**
 * 庙堂数据格式和数据库连接口
 */

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data

public class mtd {
    @ExcelProperty("id")
    private Integer  id          ;
    @ExcelProperty("设备名称")
    private String   bl           ;
    @ExcelProperty("时间")
    private Date  time        ;
    @ExcelProperty("温度")
    private Double  temperature ;
    @ExcelProperty("水位")
    private Double  waterlevel  ;
    @ExcelProperty("沉降")
    private Double  displacement;
    @ExcelProperty("位移")
    private Double  settling    ;
    @ExcelProperty("稳定性")
    private Double  stable      ;
    @ExcelProperty("渗流")
    private Double  flow        ;
}
