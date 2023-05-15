package com.lon.controller;

import com.lon.common.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import java.util.UUID;

/**
 * 通用控制器
 *
 * @author ctl
 * @date 2022/09/12
 */
@Slf4j
@RestController
@RequestMapping("/common")
@Api(tags = "通用接口")
public class CommonController {

    @Value("${prevalence.path}")
    private String basePath;

    /**
     * 上传图片
     *
     * @param file 文件
     * @return {@link ResultMsg}<{@link String}>
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传")
    public ResultMsg<String> upload(MultipartFile file) {
        // file是一个临时文件,需要转存到指定位置,否则本次请求完成后；临时文件会删除
        log.info(file.toString());
        //原始文件名

        String originalFilename = file.getOriginalFilename();
        //截取后缀名
        assert originalFilename != null;
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        //使用UUID重新生成文件名,防止文件名重复造成文件覆盖
        String filename = UUID.randomUUID().toString() + suffix;
        try {
            //判断文件夹是否存在,如果不存在则创建文件夹
            File dir = new File(basePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            file.transferTo(new File(basePath + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultMsg.success(filename);
    }

    @GetMapping("/download")
    @ApiOperation(value = "下载")
    public void download(String name, HttpServletResponse response) {
        try {
            FileInputStream fs = new FileInputStream(basePath + name);
            ServletOutputStream os = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fs.read(bytes)) != -1) {
                os.write(bytes, 0, len);
                os.flush();
            }
            fs.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
