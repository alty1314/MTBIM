package MTBIM.serivce;

import Jama.Matrix;
import MTBIM.Test.DemoDataListener;
import MTBIM.mybatis.allMtdControll;
import MTBIM.mybatis.mtd;
import MTBIM.mybatis.mtd1;
import MTBIM.mybatis.yuzhi;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.BackpropType;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class Mtd implements MtDService{
    @Autowired
    private bookBao bookBao;
    @Autowired
    private yuzhi_interface yuzhi_interface;

    @Override
    public List<mtd> selectAll() {
         return bookBao.selectList(null);
    }

    @Override
    public List<mtd> select(int currentPage, int pageSize, mtd1 mtd) {
             //IPage 里面封装了所有的数据，包括页数，码数，查询的数据内容等等
            System.out.println(mtd);
            QueryWrapper<mtd> wrapper = new QueryWrapper<>();
            String bl = mtd.getBl();

            String time1 = mtd.getTime();
        Date time = null;
        if (time != null) {
            time = java.sql.Date.valueOf(time1);
        }
        /*System.out.println("时间类型为==========》》》"+time);    //测试用代码
            System.out.println("=========>"+time);*/
            Double temperature = mtd.getTemperature();
            Double waterlevel = mtd.getWaterlevel();
            Double displacement = mtd.getDisplacement();
            Double settling = mtd.getSettling();
            Double stable = mtd.getStable();
            Double flow = mtd.getFlow();
            Integer id = mtd.getId();


            if(bl !=null & bl!=""){
                wrapper.eq("BL",bl);
                System.out.println("Bl调用成功");
            }
            if(time !=null ){
                wrapper.eq("time",time);
                System.out.println("time调用成功");
            }
            if(temperature!=null){
                wrapper.eq("temperature",temperature);
                System.out.println("调用成功");
            }
            if(settling !=null){
                wrapper.eq("settling",settling);
                System.out.println("调用成功");
            }
            if(stable !=null){
                wrapper.eq("stable",stable);
                System.out.println("调用成功");
            }
            if(flow !=null){
                wrapper.eq("flow",flow);
                System.out.println("调用成功");
            }
            if(id !=null ){
                wrapper.eq("id",id);
                System.out.println("id调用成功");
            }
            wrapper.orderBy(true,true,"time");

            IPage page = new Page(currentPage,pageSize);

            bookBao.selectPage(page,wrapper);
            /*//当前页数
            System.out.println(page.getCurrent());
            // 码数
            System.out.println(page.getSize());
            //数据库数据总数
            System.out.println(page.getTotal());
            //总共多少页
            System.out.println(page.getPages());
            //获得查询的所有的数据内容
            System.out.println(page.getRecords());*/
            return page.getRecords();
        }

    @Override
    public Map<String, Object> eChartSelect(int currentPage, int pageSize, mtd1 mtd) {
        QueryWrapper<mtd> wrapper = new QueryWrapper<>();
        IPage page = new Page(1,pageSize);  //pageSize传入的时候默认30
        String bl = mtd.getBl();
        if(bl !=null & bl!=""){
            wrapper.eq("BL",bl);
            System.out.println("Bl调用成功");}
        IPage iPage = bookBao.selectPage(page, wrapper);
        System.out.println(iPage);
        return null;
    }
    //上传文件保持的位置 方法1
    /*ApplicationHome applicationHome = new ApplicationHome(this.getClass());
    String s= applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img";
    private final String saveFilePath = s + File.separator+"files";*/
    //方法二

    private final String saveFilePath = System.getProperty("user.dir")+ File.separator+"upFiles";
    @Override
    public Map<String, Object> upload(MultipartFile file) {

        //返回端口
        Map<String, Object> result = new HashMap<>();
        //获取文件上传名字,并且加上时间前缀名字
        SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String format = formatter.format(date);
        final String filename =format+ file.getOriginalFilename();
        //测试文件后缀名是不是excel格式
        String originName = file.getOriginalFilename();
        if (!(originName.endsWith(".xlsx")||originName.endsWith(".xls"))){
            result.put("status","error");
            result.put("msg","文件类型不对");
            result.put("saveFilePath","");
            return result;
        }

        try {
            //判断有没有该文件夹，没有就创建一个
            if(!Files.exists(Paths.get(saveFilePath))){
                Files.createDirectory(Paths.get(saveFilePath));
                log.warn("创建文件夹{}",saveFilePath);
            }
            //上传文件保存位置
            final Path saveFile = Paths.get(saveFilePath+File.separator+filename);
            //给文件写入内容
            file.transferTo(saveFile);
            //成功时候回传数据
            result.put("status","success");
            result.put("msg","文件上传成功");
            result.put("fileName",filename);
            result.put("saveFilePath",String.valueOf(saveFile));


            String fileName = String.valueOf(saveFile);
            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
            allMtdControll test1 = new allMtdControll();
            //读取数据前把全局变量清空
            test1.setMtdAllControll1(null);
            System.out.println("<=========================>"+test1.getMtdAllControll1());
            //读取文件内容并且写入到全局变量中
            EasyExcel.read(fileName, mtd1.class, new DemoDataListener()).sheet().doRead();
            result.put("data",test1.getMtdAllControll1());
            System.out.println("<=========================>"+test1.getMtdAllControll1());
            //清空全局变量里面的数据
            test1.setMtdAllControll1(null);
            System.out.println("<=========================>"+test1.getMtdAllControll1());
        } catch (IOException e) {
            e.printStackTrace();

            result.put("status","error");
            result.put("msg","上传服务器失败");
            result.put("saveFilePath","");
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

       /* System.out.println(saveFile); //saveFile是保存位置
        System.out.println(String.valueOf(saveFile));
        System.out.println(saveFilePath);*/

        return result;
    }

    @Override
    public List<mtd1> mtdToMtd1(List<mtd> select) {
        List<mtd1> ss = new ArrayList<>();
        for (int i = 0; i < select.size(); i++) {
            mtd1 mtd1 = new mtd1();
            mtd1.setTime(String.valueOf(select.get(i).getTime()));
            mtd1.setBl(select.get(i).getBl());
            mtd1.setTemperature(select.get(i).getTemperature());
            mtd1.setId(select.get(i).getId());
            mtd1.setWaterlevel(select.get(i).getWaterlevel());
            mtd1.setDisplacement(select.get(i).getDisplacement());
            mtd1.setSettling(select.get(i).getSettling());
            mtd1.setStable(select.get(i).getStable());
            mtd1.setFlow(select.get(i).getFlow());
            //"BL","temperature","id","waterlevel","displacement","settling","stable","flow"
            ss.add(mtd1);

        }
        return ss;
    }

    @Override
    public List<mtd> mtd1ToMtd(List<mtd1> select) {
        List<mtd> ss = new ArrayList<>();
        for (int i = 0; i < select.size(); i++) {
            mtd mtd1 = new mtd();
            mtd1.setTime(java.sql.Date.valueOf(select.get(i).getTime()));
            mtd1.setBl(select.get(i).getBl());
            mtd1.setTemperature(select.get(i).getTemperature());
            mtd1.setId(select.get(i).getId());
            mtd1.setWaterlevel(select.get(i).getWaterlevel());
            mtd1.setDisplacement(select.get(i).getDisplacement());
            mtd1.setSettling(select.get(i).getSettling());
            mtd1.setStable(select.get(i).getStable());
            mtd1.setFlow(select.get(i).getFlow());
            //"BL","temperature","id","waterlevel","displacement","settling","stable","flow"
            ss.add(mtd1);

        }
        return ss;
    }

    @Override
    public Map<String, Object> excelWrite(String filename) {
        try {
            //先使全局变量清空
            allMtdControll test1 = new allMtdControll();
            test1.setMtdAllControll1(null);
            //读取的文件数据导入到全局文件中
            EasyExcel.read(filename, mtd1.class, new DemoDataListener()).sheet().doRead();
            //转变数据部分中时间格式
            List<mtd> XieRu =  mtd1ToMtd(test1.getMtdAllControll1());
            //插入数据到数据库中
            MysqlInsertList(XieRu);
            //清空全局变量
            test1.setMtdAllControll1(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String MysqlInsertList(List<mtd> insert) {
        mtd mtd = null;
        try {
            for (int i = 0; i < insert.size(); i++){
                mtd = insert.get(i);
                bookBao.insert(mtd);
            }
        } catch (Exception e) {
            return "写入失败";
        }
        return "写入成功";
    }

    @Override        //参数1设备名称；参数2设备属性
    public Map<String, Object> yuzhi_cal(String eq_name,String eq_property) {
        //返回数据

        Map<String, Object> RTInformation = new HashMap<>() ;
        //创建数据数组,从数据库中提取数据
        HashMap<String,Object> map = new HashMap<>();
        map.put("bl",eq_name);
        List<mtd> select = bookBao.selectByMap(map);
        if (select.size() == 0){
            RTInformation.put("code","404");
            RTInformation.put("info","数据库中没有数据内容");
            return RTInformation;
        }
        //还没有实现定向选取数据
        //提取特定数据内容，用于阈值计算
        double[][] array=new double[1][select.size()];
        mtd mtd ;
        String input = eq_property;
        switch (input){
            case "temperature":
                for (int i = 0; i < select.size(); i++) {
                mtd = select.get(i);array[0][i] = mtd.getTemperature(); }
                RTInformation.put("eq_property","temperature");
                break;
            case "waterlevel":
                for (int i = 0; i < select.size(); i++) {
                    mtd = select.get(i);array[0][i] = mtd.getWaterlevel(); }
                RTInformation.put("eq_property","waterlevel");
                break;
            case "displacement":
                for (int i = 0; i < select.size(); i++) {
                    mtd = select.get(i);array[0][i] = mtd.getDisplacement(); }
                RTInformation.put("eq_property","displacement");
                break;
            case "settling":
                for (int i = 0; i < select.size(); i++) {
                    mtd = select.get(i);array[0][i] = mtd.getSettling(); }
                RTInformation.put("eq_property","settling");
                break;
            case "stable":
                for (int i = 0; i < select.size(); i++) {
                    mtd = select.get(i);array[0][i] = mtd.getStable(); }
                RTInformation.put("eq_property","stable");
                break;
            case "flow":
                for (int i = 0; i < select.size(); i++) {
                    mtd = select.get(i);array[0][i] = mtd.getFlow(); }
                RTInformation.put("eq_property","flow");
                break;
            default:
                RTInformation.put("code","404");
                RTInformation.put("info","没有该类型数据");
                return RTInformation;
        }
        //矩阵 jama包的
        System.out.println(array);
        Matrix A = new Matrix(array);

        double[] arr01=new double[select.size()];
        for (int i = 0; i < select.size(); i++){
            arr01[i] = A.get(0, i);
        }
        //这是排序 小到大排序
        Arrays.sort(arr01);
        double MinA = arr01[(int) Math.floor(select.size()*0.7)];//floor向下取整
        double MaxA = arr01[(int) Math.ceil (select.size()*0.9)];//ceil向下取整
        double fifty_per =arr01[(int) Math.ceil (select.size()*0.5)];
        double seventy_per =arr01[(int) Math.ceil (select.size()*0.7)];
        double ninety_per =arr01[(int) Math.ceil (select.size()*0.9)];

        //分别为50，70，90%的值
        RTInformation.put("fifty_per",fifty_per);
        RTInformation.put("seventy_per",seventy_per);
        RTInformation.put("ninety_per",ninety_per);

        //u的序列步距   num为序列长度 u为步距 dist为数列
        double u = 0.01;
        int num = (int) Math.ceil((MaxA-MinA)/u);// num为序列长度
        System.out.println("这是num"+num);
        double  distp = MinA;
        double[] dist=new double[num];
        dist[num-1] = MaxA;
        int z =0;
        for (int i = 0; distp <MaxA ; i++) {
            dist[i] = distp;
            distp = distp + u;
            z = i;
        }
        //u的步距数列结束
        //计算均值和均方差
        double [][] sl = new double[num][3000];
        int k = 0;
        double [][] all = new double[num][3000];
        for (int i = 0; i <num ; i++) {
            k = 0;
            double sum = 0; //超阈值序列总和
            double sum2  = 0;  //超阈值序列方差总和
            //System.out.println("这是第" + i + "组的数据");
            for (int j = 0; j <select.size() ; j++) {
                if (dist[i] <=  arr01[j] ){
                    sl[i][k] = arr01[j];
                    //  求均值
                    sum = sum+arr01[j];
                    k = k+1;
                }
            }
            //均值
            double k11 = 0;
            k11 = sum/k;
            for (int j = 0; j <select.size() ; j++){
                if (dist[i] <=  arr01[j] ){
                    sum2 =   sum2 + ( arr01[j]-k11 )*( arr01[j]-k11 );
                }
            }
            //均方差
            double k22 = 0;
            k22 =  sum2/(k-1);

            //参数1
            double k3 =0.5*(1-k11*k11/k22);
            //参数2
            double k4=0.5*k11*(k11*k11/k22+1);

            all[i][0] = k11;
            all[i][1] = k22;
            all[i][2] = k3;
            all[i][3] = k4;
            all[i][5] = k;
        }
        double ave = 0;

        for (int j = 0; j <select.size() ; j++){
            ave = ave +arr01[j];
        }
        ave = ave/select.size();

        double fangcha =0;
        for (int j = 0; j <select.size() ; j++){
            fangcha = (arr01[j]-ave)*(arr01[j]-ave);
        }
        fangcha = Math.pow(fangcha/(select.size()-1),0.5);
        System.out.println(fangcha + "   " + ave);
        //阈值计算
        double[][] yuzhi = new double[3][num];
        for (int i = 0; i <num ; i++) {
            int nu = (int) all[i][5];
            int n =select.size();
            double E = all[i][2];
            double B = all[i][3];
            u = dist[i];
            double p=0.045;
            double p2 =0.005;
            double xm = 0.9*u + (B/E)*((n/nu*p)-1); //4.5
            double xm2 =  0.95*u + (B/E)*((n/nu*p2)-1);   //0.5
            double xm3 = xm2-xm;
            double xm4 = xm2-xm-fangcha;
            yuzhi[0][i] = xm;
            yuzhi[1][i] = xm2;
            yuzhi[2][i] = xm4;
        }
        double max=yuzhi[0][0];
        int max_inx = 0;
        for (int i = 0; i <num ; i++) {
            if(max<yuzhi[2][i]) { max=yuzhi[0][i];max_inx=i;}
        }
        RTInformation.put("four_five",yuzhi[0][max_inx]);
        RTInformation.put("zero_five",yuzhi[1][max_inx]);
        return RTInformation;
    }

    @Override
    public Map<String, Object> AHP(int[] z) {

        int num = z.length;
        double[] x = new double[num];
        for (int i = 0; i < z.length; i++) {
            int u = z[i];
            x[i] = (double)1/(double)u;
            System.out.println(x[i]);
        }
        double y =0;
        for (int i = 0; i <  num ; i++) {
            y=y+ x[i];
            System.out.println(y);
        }

        for (int i = 0; i <  num ; i++){
            x[i] = x[i]/y;
            System.out.println(x[i]);
        }
        Map<String, Object> ahp = new HashMap<>();
        ahp.put("result",x);
        ahp.put("info","success");
        return ahp;
    }

    @Override
    public double score_cal(String eq_name, String eq_property,double u) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",eq_name);
        map.put("property",eq_property);
        List<yuzhi> yuzhis = yuzhi_interface.selectByMap(map);
        yuzhi yuzhi1 =yuzhis.get(0);
        if (u >= yuzhi1.getA()) {
            System.out.println("u>=A");
        }else if (u < yuzhi1.getA() && u >= yuzhi1.getB() ){
            System.out.println("A > u >=B");
        }else if (u < yuzhi1.getB() && u >= yuzhi1.getC() ){
            System.out.println("B > u >=C");
        }else if (u < yuzhi1.getC() && u >= yuzhi1.getD() ){
            System.out.println("C > u >=D");
        }else if (u < yuzhi1.getD() && u >= yuzhi1.getE() ){
            System.out.println("D > u >=E");
        }else if (u < yuzhi1.getE() ){
            System.out.println("E > u ");
        }else {
            System.out.println("程序问题，请检查数据库和输入内容");
        }

        return 0;
    }

    @Override
    public  Map<String,Object> loadHousePrice(File file) {
        Map<String, Object> DL = new HashMap<>();
        try {
            //读取文本数据
            BufferedReader br = new BufferedReader(new FileReader(file));
            //System.out.println("这是br中的内容"+br);
            String line = null;
            List<DataSet> totalDataSetList = new LinkedList<DataSet>();
            //读取每一行的数据内容
            while( (line = br.readLine()) != null ){
                String[] token = line.split(",");
                //这个是输入的标签
                double[] featureArray = new double[token.length - 1];
                //这是输出的标签
                double[] labelArray = new double[1];
                for( int i = 0; i < token.length - 1; ++i ){
                    featureArray[i] = Double.parseDouble(token[i]);
                }
                labelArray[0] = Double.parseDouble(token[token.length - 1]);
                //
                INDArray featureNDArray = Nd4j.create(featureArray);
                System.out.println("这是输入流"+featureNDArray);
                INDArray labelNDArray = Nd4j.create(labelArray);
                System.out.println("这是输出流"+labelNDArray);
                totalDataSetList.add(new DataSet(featureNDArray, labelNDArray));
            }
            br.close();
            DL.put("data",totalDataSetList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return DL;
    }

    @Override
    public MultiLayerNetwork model() {
        MultiLayerConfiguration.Builder builder = new NeuralNetConfiguration.Builder()
                .seed(12345L)
                .updater(new Adam(0.01))
                .miniBatch(false)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .list()
                .layer(0, new DenseLayer.Builder().activation(Activation.LEAKYRELU)
                        .nIn(13).nOut(10).build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.MEAN_SQUARED_LOGARITHMIC_ERROR)
                        .activation(Activation.IDENTITY)
                        .nIn(10).nOut(1).build())
                .backpropType(BackpropType.TruncatedBPTT);
        MultiLayerConfiguration conf = builder.build();
        MultiLayerNetwork model = new MultiLayerNetwork(conf);
        model.init();
        return model;
    }
}



