package MTBIM.mybatis;

import lombok.Data;

import java.sql.Date;

//好像是没用的，但是不敢删除，我也不知道哪里有用到
@Data
public class echart {
    private Date time   ;
    private Double data;
}
