package com.wuqisan.ruijiwaimai.common.Controller;

import com.wuqisan.ruijiwaimai.common.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    //获取文件保存路径 配置yml文件
    @Value("${reggie.file}")
    private String basePath;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //1.file是一个临时文件需要转存,否则请求结束就没了
        log.info("文件上传{}",file.getOriginalFilename());
        //file.transferTo(new File(basePath+file.getOriginalFilename()));
        //2.创建目录 防止空指针
        File filedir=new File(basePath);
        if (!filedir.exists()){
            filedir.mkdir();
        }
        //3 文件转存  uuid生成随机文件名
        String fileName = UUID.randomUUID().toString()+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        file.transferTo(new File(basePath+fileName));
        //4 返回文件名称给页面捏
        return R.success(fileName);
    }
    @GetMapping("download")
    public void download(String name, HttpServletResponse httpServletResponse)   {
        try {
            //1.获取文件位置
            File file=new File(basePath+name);
            //1.1使用输入流获取文件
            FileInputStream in=new FileInputStream(file);
            //2.使用输出流输出文 件
            //2.1设置输出格式和获取输出流
            httpServletResponse.setContentType("image/jpeg");
            ServletOutputStream out = httpServletResponse.getOutputStream();
            int len=0;//变量： 数组长度
            byte[] bytes=new byte[1024];// 变量：数组
            //2.2读文件 然后写入
            while ((len=in.read(bytes)) !=-1){
                out.write(bytes,0,len);
                out.flush();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
