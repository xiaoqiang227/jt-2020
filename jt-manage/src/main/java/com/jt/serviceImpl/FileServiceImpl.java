package com.jt.serviceImpl;

import com.jt.vo.ImageVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@PropertySource("classpath:/properties/image.properties") //加载Pro配置文件
public class FileServiceImpl implements FileService {
    /**
     *
     * 实现思路:
     *   1.校验图片类型 jpg/jpeg/png/gif....
     *   2.校验是否为恶意程序.
     *   3.分文件存储   yyyy/MM/dd
     *   4.防止文件重名  自定义文件名称 uuid.类型
     */

    //定义本地磁盘路径
    @Value("${image.localDirPath}")
    private String localDirPath;  //= "D:/project_demo1/jt-images/";

    //定义虚拟访问路径
    @Value("${image.urlDirPath}")
    private String urlDirPath;  //="http://image.jt.com/;"

    @Override
    public ImageVO upload(MultipartFile uploadFile) {
        //1.获取图片名称      abc.jpg
        String fileName = uploadFile.getOriginalFilename();
        fileName = fileName.toLowerCase();  //转小写
        //2.校验 正则表达式
        if(!fileName.matches("^.+\\.(jpg|png|gif)$")) {

            return ImageVO.fail();
        }
        //System.out.println("校验成功!!!!");

        //3.校验恶意程序 图片:高度/宽度/px
        try {
            BufferedImage bImage = ImageIO.read(uploadFile.getInputStream());  //工具API
            int width = bImage.getWidth();
            int height = bImage.getHeight();
            if(width == 0 || height ==0) {
                return ImageVO.fail();
            }
            //System.out.println("表示是图片!!");

            //4.实现分文件存储.  yyyy/MM/dd/
            //将当前时间转换为文件存储目录
            String dateDir = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
            //D:/1-jt-software/jt-images/2019/10/01/
            String dirFilePath =  localDirPath+dateDir;
            System.out.println("本地目录："+dirFilePath);

            File dirFile = new File(dirFilePath);

            if(!dirFile.exists()) {
                //如果文件不存在,需要创建目录
                dirFile.mkdirs();
            }

            //5.动态生成文件名称 uuid+文件后缀
            String uuid = UUID.randomUUID().toString();
            //截取文件名后缀 abc.jpg
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            //uuid + 文件名后缀
            String realFileName = uuid + fileType;
            //文件全路径  D:xxxxx/yyyy/MM/dd/uuid.jpg
            String realFilePath = dirFilePath + realFileName;

            System.out.println("本地全路径："+realFilePath);

            //实现上传
            uploadFile.transferTo(new File(realFilePath));

            //6.实现数据的回显
            //http://image.jt.com/2019/12/21/e98b9120-08d6-404c-871c-5a27f9fc54c9.jpg   也可存入数据库
            String url = urlDirPath + dateDir + realFileName;
            ImageVO imageVO = new ImageVO(0, url, width, height);
            return imageVO;
        } catch (Exception e) {
            e.printStackTrace();
            return ImageVO.fail();
        }
    }
}

