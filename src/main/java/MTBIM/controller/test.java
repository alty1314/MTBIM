package MTBIM.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
@Slf4j
public class test {

    @PostMapping("/uploadtest")
    public String  uploadtest(MultipartFile file){
        if(file == null){
            return "success";
        }
        return "error";
    }
}
