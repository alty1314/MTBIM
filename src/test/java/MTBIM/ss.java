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
        System.out.println("=========???"+select);
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
          System.out.println("????????????"+a[i][0]+"+"+a[i][1]);
          System.out.println(select.get(i).getTime());

            List1.add(select.get(i).getTime());
            List2.add(select.get(i).getTemperature());
            List4.add(Arrays.deepToString(k));
            echart e = new echart();
            e.setTime(select.get(i).getTime());
            e.setData(select.get(i).getTemperature());
            List3.add(e);
          System.out.println(select.get(i).getTime().getClass().getTypeName());
      } System.out.println("===============???"+List1);
        Map<String, Object> map = new HashMap<>();
        map.put("time",List1);
        map.put("data",List2);
        map.put("all",List3);
        map.put("shuzu",Arrays.deepToString(a));

        System.out.println("******"+List4);
        System.out.println("????????????"+Arrays.deepToString(a));
        System.out.println("map?????????========"+map);


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
        wrapper.orderByDesc(true,"time"); //Desc????????????????????????Asc??????
        IPage page = new Page(1,30);
        IPage iPage = bookBao.selectPage(page, wrapper);

        System.out.println("??????---"+iPage);
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
            data.setString("?????????" + i);
            data.setDate("1111"+i);
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
    @Test
    public void simpleWrite() {
        String fileName =  "D:\\game\\"+System.currentTimeMillis()+"new?????????.xls";
        // ?????? ????????????????????????class??????????????????????????????sheet?????????????????? ??????????????????????????????
        // ?????????????????????03 ??? ??????excelType????????????
        // ?????? ????????????????????????class??????????????????????????????sheet?????????????????? ??????????????????????????????
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
        // ?????????????????????03 ??? ??????excelType????????????
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
                    .sheet("??????").doWrite(ss);
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
        System.out.println("sst?????????"+sst);
    }

    @Autowired
    private bookBaospare bookBaospare;
    private allMtdSet tesata;
    @Test
    void ssss(){
        /*String fileName = "1655901244782new?????????.xls";*/

        String fileName = "D:\\javacode\\springdemo\\upFiles\\SR1??????xls.xls";
        // ?????? ????????????????????????class??????????????????????????????sheet ????????????????????????
        // ?????????????????????100????????? ?????????????????? ??????????????????????????????
        allMtdControll test1 = new allMtdControll();
        //????????????????????????????????????
        test1.setMtdAllControll1(null);
        System.out.println("<=========================>"+test1.getMtdAllControll1());
        //????????????????????????????????????????????????
        EasyExcel.read(fileName, mtd1.class, new DemoDataListener()).sheet().doRead();
        List<mtd> XieRu =  MtDService1.mtd1ToMtd(test1.getMtdAllControll1());
        MtDService1.MysqlInsertList(XieRu);
        System.out.println("<???????????????????????????=========================>"+XieRu);
        System.out.println("<=========================>"+test1.getMtdAllControll1());
        //?????????????????????????????????
        test1.setMtdAllControll1(null);
        System.out.println("<=========================>"+test1.getMtdAllControll1());
    }
    @Test
    void yuzhitest(){ //????????????
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
    void juzhen1(){  //???????????? ???????????????????????????
        List<mtd> select = MtDService1.select(1, 1000, mtd1);
        //?????? java??????
        double[][] array=new double[1][select.size()];  //array???????????????
        mtd mtd = null;
        for (int i = 0; i < select.size(); i++) {
            mtd = select.get(i);
            array[0][i] = mtd.getStable();
        }
        //?????? jama??????
        System.out.println(array);
        Matrix A = new Matrix(array);

        double[] arr01=new double[select.size()];
        for (int i = 0; i < select.size(); i++){
            arr01[i] = A.get(0, i);
        }
        //???????????? ???????????????
        Arrays.sort(arr01);

        double MinA = arr01[(int) Math.floor(select.size()*0.7)];//floor????????????
        double MaxA = arr01[(int) Math.ceil (select.size()*0.9)];//ceil????????????

         System.out.println(MinA + "???"+MaxA);
         //u???????????????   num??????????????? u????????? dist?????????
        double u = 0.01;
        int num = (int) Math.ceil((MaxA-MinA)/u);// num???????????????
        System.out.println("??????num"+num);
        double  distp = MinA;
        double[] dist=new double[num];
            dist[num-1] = MaxA;
        int z =0;
        for (int i = 0; distp <MaxA ; i++) {
            dist[i] = distp;
            distp = distp + u;
            z = i;
        }
        //u?????????????????????

        //????????????????????????
        double [][] sl = new double[num][1000];
        int k = 0;
        double [][] all = new double[num][1000];
        for (int i = 0; i <num ; i++) {
            k = 0;
            double sum = 0; //?????????????????????
            double sum2  = 0;  //???????????????????????????
            //System.out.println("?????????" + i + "????????????");
            for (int j = 0; j <select.size() ; j++) {
                if (dist[i] <=  arr01[j] ){
                   sl[i][k] = arr01[j];
                   //  ?????????
                    sum = sum+arr01[j];
                    k = k+1;
                }
            }
            //??????
            double k11 = 0;
            k11 = sum/k;
            for (int j = 0; j <select.size() ; j++){
                if (dist[i] <=  arr01[j] ){
                    sum2 =   sum2 + ( arr01[j]-k11 )*( arr01[j]-k11 );
                }
            }
            //?????????
            double k22 = 0;
            k22 =  sum2/(k-1);

            //??????1
            double k3 =0.5*(1-k11*k11/k22);
            //??????2
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
    void AHP(){ //??????AHP??????????????? ?????????????????????????????????
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
    void yuzhi_test(){ //?????????????????????
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

        if (!yuzhis1.isEmpty()){ //??????????????????????????????????????????????????????????????????????????????????????????
            UpdateWrapper<yuzhi> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("name","SR1");//?????????????????????????????????
            yuzhi_interface.update(a, updateWrapper); //????????????????????????????????????????????????????????????
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
        //???????????????????????????
        String gender = "???";
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
        //System.out.println("??????????????????????????????"+housePriceList.size());
        double[][] featureArray2 = new double[507][13];
        double[][] labelArray2 = new double[507][1];
        try {
            //??????????????????
            BufferedReader br = new BufferedReader(new FileReader(file));
            System.out.println(br.readLine());
            //System.out.println("??????br????????????"+br);
            String line = null;
            List<DataSet> totalDataSetList = new LinkedList<DataSet>();
            //??????????????????????????????
            int k = 0;
            while( (line = br.readLine()) != null ){
                String[] token = line.split(",");
                //????????????????????????
                double[] featureArray = new double[token.length - 1];
                //?????????????????????
                double[] labelArray = new double[1];
                for( int i = 0; i < token.length - 1; ++i ){
                    featureArray[i] = Double.parseDouble(token[i]);
                    featureArray2[k][i] = Double.parseDouble(token[i]);
                }
                labelArray[0] = Double.parseDouble(token[token.length - 1]);
                labelArray2[k][0] = Double.parseDouble(token[token.length - 1]);
                //???????????? ???????????? ??? ????????????/??????
                INDArray featureNDArray = Nd4j.create(featureArray);
                //System.out.println("???????????????"+featureNDArray);
                INDArray labelNDArray = Nd4j.create(labelArray);
                //System.out.println("???????????????"+labelNDArray);
                totalDataSetList.add(new DataSet(featureNDArray, labelNDArray));
                k = k+1;
            }
            INDArray featureNDArray = Nd4j.create(featureArray2);
            INDArray labelNDArray = Nd4j.create(labelArray2);
            DataSet allData = new DataSet(featureNDArray,labelNDArray);
            br.close();
            List<DataSet> housePriceList =totalDataSetList;

            allData.shuffle(SEED);
            //???????????????????????????
            SplitTestAndTrain split = allData.splitTestAndTrain(trainSize);        //?????????????????????api
            DataSet dsTrain = split.getTrain();//??????
            DataSet dsTest = split.getTest();//??????
            //?????????
            DataSetIterator trainIter = new ListDataSetIterator(dsTrain.asList() , batchSize);
            System.out.println("???????????????"+trainIter);
            //?????????
            DataSetIterator testIter = new ListDataSetIterator(dsTest.asList() , batchSize);
            DataNormalization scaler = new NormalizerMinMaxScaler(0,1);
            scaler.fit(trainIter);
            scaler.fit(testIter);

            trainIter.setPreProcessor(scaler);
            System.out.println("zheshi????????????===="+scaler.getClass());
            testIter.setPreProcessor(scaler);
            MultiLayerNetwork mlp =MtDService1.model(13,10,1);
            mlp.setListeners(new ScoreIterationListener(1));
            for( int i = 0; i < 200; ++i ){
                mlp.fit(trainIter);
                trainIter.reset();
            }
            //?????? Deeplearning4j ????????????????????????????????????????????????
            RegressionEvaluation eval = mlp.evaluateRegression(testIter);
            System.out.println(eval.stats());
            String stats = eval.stats();
            System.out.println(stats);
            testIter.reset();
            //???????????????????????????????????????
            int s = 0;
            while( testIter.hasNext() ){
                s = s+1;
                System.out.println("?????????"+s+"???"+testIter.next().getLabels());
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
            /*try {  //????????????
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
        //?????????????????????
        HashMap<String, Object> map = new HashMap<>();
        map.put("bl","SR1");
        List<mtd> select = bookBao.selectByMap(map);
        System.out.println(select.size());
        //??????????????????
        /*System.out.println(select.get(0).getTime());
        System.out.println(select.get(1).getTime());
        int a = MtDService1.timeCALC(select.get(2).getTime(),select.get(1).getTime());
        System.out.println(a);
*/
        double[][] dataList = new double[select.size()][6];


        for (int i = 0; i < select.size(); i++) {
            dataList[i][0] = select.get(i).getTemperature(); //??????
            dataList[i][1] = Math.pow(select.get(i).getTemperature(),2) ; //???????????????
            int time1 = MtDService1.timeCALC(select.get(i).getTime(),select.get(0).getTime()); //???????????????????????????-????????????
            dataList[i][2] = time1;             //??????
            dataList[i][3] = Math.log(time1+1) / Math.log(Math.E);     //ln???t+1???
            dataList[i][4] = select.get(i).getWaterlevel();     //????????????
            dataList[i][5] = select.get(i).getStable();        //??????????????????
           /* System.out.println("???"+i+"???");
            for (int j = 0; j < 6; j++) {
                System.out.print(dataList[i][j] + " ");
            }*/
        }

        //???????????????????????????????????????
        final int batchSize = 100;
        final long SEED = 1234L;
        final int trainSize = 900;

        //??????????????????????????????????????????
        //????????????????????????
        double[][] featureArray2 = new double[1000][5];
        //?????????????????????
        double[][] labelArray2 = new double[1000][1];

        for (int i = 0; i <1000 ; i++) {
            //????????????????????????
            double[] featureArray = new double[5];
            //?????????????????????
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

        SplitTestAndTrain split = allData.splitTestAndTrain(trainSize);        //?????????????????????api
        DataSet dsTrain = split.getTrain();//??????
        DataSet dsTest = split.getTest();//??????

        //?????????
        DataSetIterator trainIter = new ListDataSetIterator(dsTrain.asList() , batchSize);
        System.out.println("???????????????"+trainIter);
        //?????????
        DataSetIterator testIter = new ListDataSetIterator(dsTest.asList() , batchSize);
        System.out.println("???????????????"+trainIter.getClass());

        //???????????????
        DataNormalization scaler = new NormalizerMinMaxScaler(0,1);
        scaler.fit(trainIter);
        scaler.fit(testIter);

        trainIter.setPreProcessor(scaler);

        testIter.setPreProcessor(scaler);

        MultiLayerNetwork mlp =MtDService1.model(5,6,1);    //?????????????????????????????????

        mlp.setListeners(new ScoreIterationListener(1));
        for( int i = 0; i < 200; ++i ){
            mlp.fit(trainIter);
            trainIter.reset();
        }
        RegressionEvaluation eval = mlp.evaluateRegression(testIter);
        System.out.println(eval.stats());
        testIter.reset();
        //???????????????????????????????????????
        int s = 0;
        while( testIter.hasNext() ){
            s = s+1;
            System.out.println("????????????"+s+"???"+testIter.next().getLabels());
        }
        testIter.reset();

        System.out.println("==============="+mlp.output(testIter).getDouble(1,1));
        /*for (int i = 0; i < output.length(); i++) {
            output.getDouble(i,1);
        }*/
        //????????????????????????
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
            System.out.println("??????s1  "+s1[i]);
            System.out.println("??????s2  "+s2[i]);
            System.out.println("??????s3  "+s3[i]);
            System.out.println("??????s4  "+s4[i]);
        }

    }

}

