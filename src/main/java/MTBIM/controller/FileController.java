package MTBIM.controller;
/**
 * 文件上传下载的控制层
 */

import MTBIM.serivce.MtDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private MtDService MtDService;

    @PostMapping("/upload")
    public Map<String,Object> upload(MultipartFile file) throws IOException {
        System.out.println("这是上传的文件名字"+file);
        System.out.println("文件上传部件被调用一次");
        Map<String, Object> upload = MtDService.upload(file);
        System.out.println("这是"+String.valueOf(upload.get("saveFilePath")+"的文件名字"));
        /*MtDService.excelWrite(String.valueOf(upload.get("saveFilePath")));*/
        return upload;
    }
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws Exception{
        //@PathVariable String fileName, HttpServletResponse response
        //告诉服务器传输的文件是二进制
        response.setContentType("application/octet-stream");
        //写入文件的通道
        final WritableByteChannel writableByteChannel = Channels.newChannel(response.getOutputStream());
        //读取文件的通道
        final FileChannel fileChannel = new FileInputStream(Paths.get("D:\\javacode\\springdemo\\upFiles\\庙堂运行数据.xls").toFile()).getChannel();
        fileChannel.transferTo(0,fileChannel.size(),writableByteChannel);
        fileChannel.close();
        writableByteChannel.close();
    }
}
