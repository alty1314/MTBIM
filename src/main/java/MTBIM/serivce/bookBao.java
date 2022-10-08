package MTBIM.serivce;

import MTBIM.mybatis.mtd;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

//Sql的映射必须要加上@Mapper
/*这里映射的是调用数据库的包，后续调用mysql都有要都用到这个包*/
//庙堂的运行数据库的连接类
@Mapper
public interface bookBao extends BaseMapper<mtd> {

}
