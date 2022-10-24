package MTBIM;

import Jama.Matrix;
import MTBIM.Test.DemoData;
import MTBIM.Test.DemoDataListener;
import MTBIM.mybatis.*;
import MTBIM.Test.allMtdSet;
import MTBIM.serivce.MtDService;
import MTBIM.serivce.bookBao;
import MTBIM.serivce.bookBaospare;
import MTBIM.serivce.yuzhi_interface;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import org.deeplearning4j.datasets.iterator.utilty.ListDataSetIterator;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
import org.junit.jupiter.api.Test;


import org.nd4j.evaluation.regression.RegressionEvaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.SplitTestAndTrain;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.NormalizerMinMaxScaler;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class ss {
    @Autowired
    private MtDService MtDService1;

    @Autowired
    private bookBao bookBao;

    private MTBIM.mybatis.mtd1 mtd1 = new mtd1();

    @Test
    public void selectAll(){
        System.out.println(MtDService1.selectAll());
    }

    @Test       //past
    public void selectTest(){

    /*  mtd1.setBl("D2");

        System.out.println(mtd1.getBl());*/
/*
        System.out.println(MtDService1.select(1, 5, mtd1));
*/
        List<mtd> select = MtDService1.select(1, 5, mtd1);
        System.out.println("=========》"+select);
        List<Date> List1 = new ArrayList<>();
        List<Double> List2 = new ArrayList<>();
        List<echart> List3 = new ArrayList<>();
        Object[][] a = new Object[30][2];
        List<Object> List4 = new ArrayList<>();

      for (int i = 0; i < select.size(); i++) {
          Object[][] k = new Object[1][2];
          k[0][0]=String.valueOf(select.get(i).getTime()) ;
          k[0][1]=select.get(i).getTemperature();

          a[i][0]=select.get(i).getTime();
          a[i][1]=select.get(i).getTemperature();
          System.out.println("数组内容"+a[i][0]+"+"+a[i][1]);
          System.out.println(select.get(i).getTime());

            List1.add(select.get(i).getTime());
            List2.add(select.get(i).getTemperature());
            List4.add(Arrays.deepToString(k));
            echart e = new echart();
            e.setTime(select.get(i).getTime());
            e.setData(select.get(i).getTemperature());
            List3.add(e);
          System.out.println(select.get(i).getTime().getClass().getTypeName());
      } System.out.println("===============》"+List1);
        Map<String, Object> map = new HashMap<>();
        map.put("time",List1);
        map.put("data",List2);
        map.put("all",List3);
        map.put("shuzu",Arrays.deepToString(a));

        System.out.println("******"+List4);
        System.out.println("数组内容"+Arrays.deepToString(a));
        System.out.println("map的展示========"+map);


        switch(select.get(0).getTime().getClass().getTypeName()){
            case "java.sql.Date":
                System.out.println(select.getClass().getTypeName()+"=============");
                break;
            case "String":
                break;
        }


    }
    @Test
    public void echart(){
        QueryWrapper<mtd> wrapper = new QueryWrapper<>();
        wrapper.select("BL","time","temperature");
        wrapper.eq("BL","SR1");
        wrapper.orderByDesc(true,"time"); //Desc降序，重上往下，Asc升序
        IPage page = new Page(1,30);
        IPage iPage = bookBao.selectPage(page, wrapper);

        System.out.println("这里---"+iPage);
        List<String> a = new ArrayList<>();

        HashMap<String, Object> map = new HashMap<>();

        List<mtd> records = page.getRecords();
        System.out.println(page.getRecords().getClass()+ "========="+records);
    }
    @Test
    public void upload(){

        ApplicationHome applicationHome = new ApplicationHome(this.getClass());
        String s= applicationHome.getDir().getParentFile().getParentFile().getAbsolutePath() + "\\src\\main\\resources\\static\\img";
         final String saveFilePath = s + File.separator+"files";
        String sa = System.getProperty("user.dir")+ File.separator+"files";
        System.out.println(saveFilePath);
        System.out.println(sa);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
    }
    private List<DemoData> data() {
        List<DemoData> list = ListUtils.newArrayList();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate("1111"+i);
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
    @Test
    public void simpleWrite() {
        String fileName =  "D:\\game\\"+System.currentTimeMillis()+"new水化热.xls";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
/*        System.out.println(fileName);
        List<mtd> select = MtDService1.select(1, 30, mtd1);



        QueryWrapper<mtd> wrapper = new QueryWrapper<>();
        wrapper.select("BL","temperature","id","waterlevel","displacement","settling","stable","flow");
        IPage page = new Page(1,30);
        bookBao.selectPage(page, wrapper);
        List<mtd1> s = new ArrayList<>();
        s = page.getRecords();



        List<DemoData> data = data();
        System.out.println(data);


        String ss = String.valueOf(select);
        System.out.println(ss);
        // 如果这里想使用03 则 传入excelType参数即可
        List<mtd> mtds = MtDService1.selectAll();*/

        List<mtd> select = MtDService1.select(1, 30, mtd1);
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
        try {
            EasyExcel.write(fileName, mtd1.class)
                    .sheet("模板").doWrite(ss);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    @Test
    void ad(){
        List<mtd> select = MtDService1.select(1, 30, mtd1);
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
        List<mtd> sst = MtDService1.mtd1ToMtd(ss);
        for (int i = 0; i < select.size(); i++) {
            mtd mtd1 = new mtd();
            mtd1.setTime(select.get(i).getTime());
            mtd1.setBl(select.get(i).getBl());
            mtd1.setTemperature(select.get(i).getTemperature());
            mtd1.setId(select.get(i).getId());
            mtd1.setWaterlevel(select.get(i).getWaterlevel());
            mtd1.setDisplacement(select.get(i).getDisplacement());
            mtd1.setSettling(select.get(i).getSettling());
            mtd1.setStable(select.get(i).getStable());
            mtd1.setFlow(select.get(i).getFlow());
            bookBao.insert(mtd1);
        }
        System.out.println("sst的数据"+sst);
    }

    @Autowired
    private bookBaospare bookBaospare;
    private allMtdSet tesata;
    @Test
    void ssss(){
        /*String fileName = "1655901244782new水化热.xls";*/

        String fileName = "D:\\javacode\\springdemo\\upFiles\\SR1数据xls.xls";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        allMtdControll test1 = new allMtdControll();
        //读取数据前把全局变量清空
        test1.setMtdAllControll1(null);
        System.out.println("<=========================>"+test1.getMtdAllControll1());
        //读取文件内容并且写入到全局变量中
        EasyExcel.read(fileName, mtd1.class, new DemoDataListener()).sheet().doRead();
        List<mtd> XieRu =  MtDService1.mtd1ToMtd(test1.getMtdAllControll1());
        MtDService1.MysqlInsertList(XieRu);
        System.out.println("<这是转换格式之后的=========================>"+XieRu);
        System.out.println("<=========================>"+test1.getMtdAllControll1());
        //清空全局变量里面的数据
        test1.setMtdAllControll1(null);
        System.out.println("<=========================>"+test1.getMtdAllControll1());
    }
    @Test
    void yuzhitest(){ //阈值测试
        Double first ;
        Double end;
        Double mid = 0.1;
        Double number;
        first = 1.0;
        end = 10.0;
        number = first;
        List<Double> List;
        for (int i = 0;number <= end; i++){

        }
    }
    @Test
    void juzhen1(){  //矩阵测试 阈值计算已经完成了
        List<mtd> select = MtDService1.select(1, 1000, mtd1);
        //矩阵 java自带
        double[][] array=new double[1][select.size()];  //array为温度数组
        mtd mtd = null;
        for (int i = 0; i < select.size(); i++) {
            mtd = select.get(i);
            array[0][i] = mtd.getStable();
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

         System.out.println(MinA + "和"+MaxA);
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
        double [][] sl = new double[num][1000];
        int k = 0;
        double [][] all = new double[num][1000];
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

        for (int i = 0; i <num ; i++) {
            int nu = (int) all[i][5];
            int n =1000;
            double E = all[i][2];
            double B = all[i][3];
            u = dist[i];
            double p=0.045;
            double p2 =0.005;
            double xm = 0.75*u + (B/E)*((n/nu*p)-1);
            double xm2 = 0.75*u + (B/E)*((n/nu*p2)-1);
            double xm3 = xm2-xm;
            double xm4 = xm2-xm-fangcha;
            System.out.println(xm + "  " + xm2+ "  " + xm3+ "  " + xm4);
        }
        System.out.println(arr01[select.size()-1]);
    }

    @Test
    void AHP(){ //单个AHP的计算方法 后续还需要写入数据库中
        int[] z = {1,3,2};
        double[] x = new double[3];
        for (int i = 0; i < z.length; i++) {
            int u = z[i];
            x[i] = (double)1/(double)u;
            System.out.println(x[i]);
        }

        double y =0;
        for (int i = 0; i < 3; i++) {
            y=y+ x[i];
            System.out.println(y);
        }
        for (int i = 0; i < 3; i++){
            x[i] = x[i]/y;
            System.out.println(x[i]);
        }
    }
    @Test
    void AHP2(){
        int[] z = {1,6,3};
        System.out.println(MtDService1.AHP(z).get("info"));
    }

    @Autowired
    private yuzhi_interface yuzhi_interface;
    @Test
    void yuzhi_test(){ //储存阈值的内容
        yuzhi a = new yuzhi();
        a.setName("SR1");
        a.setA(1.0);
        a.setB(1.0);
        a.setC(1.0);
        a.setD(1.0);
        a.setE(1.0);
        HashMap<String,Object> map = new HashMap<>();
        map.put("name","SR1");
        List<yuzhi> yuzhis1 = yuzhi_interface.selectByMap(map);

        if (!yuzhis1.isEmpty()){ //判断数据库中是否有相关关键词的内容，如果有就修改，没有就写入
            UpdateWrapper<yuzhi> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name","SR1");//确定要修改的对象的名称
            yuzhi_interface.update(a, updateWrapper); //第一个参数为修改内容，后面为修改对面名称
        }else {
            yuzhi_interface.insert(a);
        }
    }
    @Test
    void yuzhi_test2(){
        Map<String, Object> ss =MtDService1.yuzhi_cal("SR1","stable");
        System.out.println(ss);
    }
    @Test
    void yuzhi_test3(){
        MtDService1.score_cal("SR1","stable",0.75);
    }

    @Test
    void panbie(){
        //可以用于关键词判断
        String gender = "男";
        switch (gender){
            case "time":
                break;
            case "temperature":
                break;
            case "waterlevel":
                break;
            case "displacement":
                break;
            case "settling":
                break;
            case "stable":
                break;
            case "flow":
                break;
        }
    }
    @Test
    void tesss(){
        System.out.println(Nd4j.getBackend());
        INDArray tensor1 = Nd4j.create(new double[]{1,2,3});
        INDArray tensor2 = Nd4j.create(new double[]{10.0,20.0,30.0});
        System.out.println(tensor1.add(tensor2));



        //mlp.setListeners(new StatsListener(statsStorage));
    }

    @Test
    void tett(){
        final int batchSize = 106;
        final long SEED = 1234L;
        final int trainSize = 400;
        String file = "C:\\Users\\XJH\\Desktop\\house_price1.csv";
        //List<DataSet> housePriceList =MtDService1.loadHousePrice(new File(file));
       // DataSet allData = DataSet.merge(housePriceList);
        //System.out.println("这是还没打乱顺序前的"+housePriceList.size());
        double[][] featureArray2 = new double[507][13];
        double[][] labelArray2 = new double[507][1];
        try {
            //读取文本数据
            BufferedReader br = new BufferedReader(new FileReader(file));
            System.out.println(br.readLine());
            //System.out.println("这是br中的内容"+br);
            String line = null;
            List<DataSet> totalDataSetList = new LinkedList<DataSet>();
            //读取每一行的数据内容
            int k = 0;
            while( (line = br.readLine()) != null ){
                String[] token = line.split(",");
                //这个是输入的标签
                double[] featureArray = new double[token.length - 1];
                //这是输出的标签
                double[] labelArray = new double[1];
                for( int i = 0; i < token.length - 1; ++i ){
                    featureArray[i] = Double.parseDouble(token[i]);
                    featureArray2[k][i] = Double.parseDouble(token[i]);
                }
                labelArray[0] = Double.parseDouble(token[token.length - 1]);
                labelArray2[k][0] = Double.parseDouble(token[token.length - 1]);
                //分别输入 输入数据 和 输出数据/标签
                INDArray featureNDArray = Nd4j.create(featureArray);
                //System.out.println("这是输入流"+featureNDArray);
                INDArray labelNDArray = Nd4j.create(labelArray);
                //System.out.println("这是输出流"+labelNDArray);
                totalDataSetList.add(new DataSet(featureNDArray, labelNDArray));
                k = k+1;
            }
            INDArray featureNDArray = Nd4j.create(featureArray2);
            INDArray labelNDArray = Nd4j.create(labelArray2);
            DataSet allData = new DataSet(featureNDArray,labelNDArray);
            br.close();
            List<DataSet> housePriceList =totalDataSetList;

            allData.shuffle(SEED);
            //划分训练集和验证集
            SplitTestAndTrain split = allData.splitTestAndTrain(trainSize);        //自带的随机划分api
            DataSet dsTrain = split.getTrain();//训练
            DataSet dsTest = split.getTest();//测试
            //训练集
            DataSetIterator trainIter = new ListDataSetIterator(dsTrain.asList() , batchSize);
            System.out.println("这是训练集"+trainIter);
            //测试集
            DataSetIterator testIter = new ListDataSetIterator(dsTest.asList() , batchSize);
            DataNormalization scaler = new NormalizerMinMaxScaler(0,1);
            scaler.fit(trainIter);
            scaler.fit(testIter);

            trainIter.setPreProcessor(scaler);
            System.out.println("zheshi训练集合===="+scaler.getClass());
            testIter.setPreProcessor(scaler);
            MultiLayerNetwork mlp =MtDService1.model(13,10,1);
            mlp.setListeners(new ScoreIterationListener(1));
            for( int i = 0; i < 200; ++i ){
                mlp.fit(trainIter);
                trainIter.reset();
            }
            //利用 Deeplearning4j 内置的回归模型分析器进行模型评估
            RegressionEvaluation eval = mlp.evaluateRegression(testIter);
            System.out.println(eval.stats());
            String stats = eval.stats();
            System.out.println(stats);
            testIter.reset();
            //输出验证集的真实值和预测值
            int s = 0;
            while( testIter.hasNext() ){
                s = s+1;
                System.out.println("这是第"+s+"个"+testIter.next().getLabels());
            }
            testIter.reset();
            System.out.println("==============="+mlp.output(testIter));
            System.out.println("====================="+allData);
            //UIServer uiServer = UIServer.getInstance();
            //StatsStorage statsStorage = new FileStatsStorage(new File("D:\\javacode\\MTBIM", "ui-stats.dl4j"));
           // uiServer.attach(statsStorage);
            //mlp.setListeners(new StatsListener(statsStorage));

            /*System.out.println(1);
            Object o = new Object();
            synchronized (o) {
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
            System.out.println(2);
            /*try {  //暂停程序
                long start = System.currentTimeMillis();
                Thread.sleep(100000);
                long end = System.currentTimeMillis();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void ssssdas()   {
        //从后台获取数据
        HashMap<String, Object> map = new HashMap<>();
        map.put("bl","SR1");
        List<mtd> select = bookBao.selectByMap(map);
        System.out.println(select.size());
        //时间计算测试
        /*System.out.println(select.get(0).getTime());
        System.out.println(select.get(1).getTime());
        int a = MtDService1.timeCALC(select.get(2).getTime(),select.get(1).getTime());
        System.out.println(a);
*/
        double[][] dataList = new double[select.size()][6];


        for (int i = 0; i < select.size(); i++) {
            dataList[i][0] = select.get(i).getTemperature(); //温度
            dataList[i][1] = Math.pow(select.get(i).getTemperature(),2) ; //温度的平方
            int time1 = MtDService1.timeCALC(select.get(i).getTime(),select.get(0).getTime()); //时间长度，现在时间-最初时间
            dataList[i][2] = time1;             //时间
            dataList[i][3] = Math.log(time1+1) / Math.log(Math.E);     //ln（t+1）
            dataList[i][4] = select.get(i).getWaterlevel();     //水位计算
            dataList[i][5] = select.get(i).getStable();        //要计算的内容
           /* System.out.println("第"+i+"个");
            for (int j = 0; j < 6; j++) {
                System.out.print(dataList[i][j] + " ");
            }*/
        }

        //神经网络的部分参数设置参数
        final int batchSize = 100;
        final long SEED = 1234L;
        final int trainSize = 900;

        //要训练的数据和对数据分类设置
        //这个是输入的标签
        double[][] featureArray2 = new double[1000][5];
        //这是输出的标签
        double[][] labelArray2 = new double[1000][1];

        for (int i = 0; i <1000 ; i++) {
            //这个是输入的标签
            double[] featureArray = new double[5];
            //这是输出的标签
            double[] labelArray = new double[1];
            for (int j = 0; j < 5; j++) {
                featureArray2[i][j] = dataList[i][j];
            }
            labelArray2[i][0] = dataList[i][5];
        }
        INDArray featureNDArray = Nd4j.create(featureArray2);
        INDArray labelNDArray = Nd4j.create(labelArray2);
        DataSet allData = new DataSet(featureNDArray,labelNDArray);
        //System.out.println(allData);

        allData.shuffle(SEED);

        SplitTestAndTrain split = allData.splitTestAndTrain(trainSize);        //自带的随机划分api
        DataSet dsTrain = split.getTrain();//训练
        DataSet dsTest = split.getTest();//测试

        //训练集
        DataSetIterator trainIter = new ListDataSetIterator(dsTrain.asList() , batchSize);
        System.out.println("这是训练集"+trainIter);
        //测试集
        DataSetIterator testIter = new ListDataSetIterator(dsTest.asList() , batchSize);
        System.out.println("这是训练集"+trainIter.getClass());

        //归一化处理
        DataNormalization scaler = new NormalizerMinMaxScaler(0,1);
        scaler.fit(trainIter);
        scaler.fit(testIter);

        trainIter.setPreProcessor(scaler);

        testIter.setPreProcessor(scaler);

        MultiLayerNetwork mlp =MtDService1.model(5,6,1);    //输入口，隐藏层，输出层

        mlp.setListeners(new ScoreIterationListener(1));
        for( int i = 0; i < 200; ++i ){
            mlp.fit(trainIter);
            trainIter.reset();
        }
        RegressionEvaluation eval = mlp.evaluateRegression(testIter);
        System.out.println(eval.stats());
        testIter.reset();
        //输出验证集的真实值和预测值
        int s = 0;
        while( testIter.hasNext() ){
            s = s+1;
            System.out.println("这是测试"+s+"个"+testIter.next().getLabels());
        }
        testIter.reset();

        System.out.println("==============="+mlp.output(testIter).getDouble(1,1));
        /*for (int i = 0; i < output.length(); i++) {
            output.getDouble(i,1);
        }*/
        //输入新的测试集合
        double[][] inList = new double[1][5];
        double[][] outList = new double[1][1];
        for (int i = 0; i < 5; i++) {
            inList[0][i] = dataList[1][i];
        }
        outList[0][0] = dataList[0][5];

        INDArray featureNDArray3 = Nd4j.create(inList);
        INDArray labelNDArray3 = Nd4j.create(outList);
        DataSet testData = new DataSet(featureNDArray3,labelNDArray3);
        DataSetIterator testIter2 = new ListDataSetIterator(testData.asList() , batchSize);

        scaler.fit(testIter2);
        testIter2.setPreProcessor(scaler);

        System.out.println(mlp.output(testIter2));
        System.out.println(outList[0][0]);
    }
    @Test
    void sdadwq(){
        List<yuzhi> allData;
        allData = yuzhi_interface.selectList(null);
        Map<String, Object> map = new HashMap<>();
        map.put("allData",allData);
        System.out.println(map.get("allData"));

        List<yuzhi> oneData = MtDService1.yuzhi_getOneData("SR1","null");
    }
    @Test
    void asdasddw(){
        String currentPage = "SR1";
        String pageSize = "stable";
        Map<String, Object> map2 = MtDService1.yuzhi_cal(currentPage, pageSize);
        yuzhi yuzhi_insert = new yuzhi();
        yuzhi_insert.setName(currentPage);
        yuzhi_insert.setA((Double) map2.get("zero_five"));
        yuzhi_insert.setB((Double) map2.get("four_five"));
        yuzhi_insert.setC((Double) map2.get("ninety_per"));
        yuzhi_insert.setD((Double) map2.get("seventy_per"));
        yuzhi_insert.setE((Double) map2.get("fifty_per"));

        Map<String, Object> map3 =new HashMap<>();
        map3.put("name",currentPage);
        if (yuzhi_interface.selectByMap(map3) != null){
            UpdateWrapper<yuzhi> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name",currentPage);
            yuzhi_interface.update(yuzhi_insert, updateWrapper);
        }
    }
    @Test
    void aawwqeq(){
        Map<String,double[]> map1 = MtDService1.safe_all("DP", "displacement", java.sql.Date.valueOf("2021-12-29"));
        Map<String,double[]> map2 = MtDService1.safe_all("SR", "stable", java.sql.Date.valueOf("2021-12-29"));
        Map<String,double[]> map3 = MtDService1.safe_all("ST", "settling", java.sql.Date.valueOf("2021-12-29"));
        Map<String,double[]> map4 = MtDService1.safe_all("FL", "flow", java.sql.Date.valueOf("2021-12-29"));
        double[] s1 =  map1.get("data");
        double[] s2 =  map2.get("data");
        double[] s3 =  map3.get("data");
        double[] s4 =  map4.get("data");
        for (int i = 0; i < 5; i++) {
            System.out.println("这是s1  "+s1[i]);
            System.out.println("这是s2  "+s2[i]);
            System.out.println("这是s3  "+s3[i]);
            System.out.println("这是s4  "+s4[i]);
        }

    }

}

