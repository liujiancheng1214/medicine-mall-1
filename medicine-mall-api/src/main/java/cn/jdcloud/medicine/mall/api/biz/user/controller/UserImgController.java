package cn.jdcloud.medicine.mall.api.biz.user.controller;

import cn.jdcloud.framework.core.common.BaseController;
import cn.jdcloud.framework.core.vo.ApiResult;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserImgService;
import cn.jdcloud.medicine.mall.api.biz.user.service.UserService;
import cn.jdcloud.medicine.mall.domain.user.UserImg;
import cn.jdcloud.medicine.mall.domain.user.UserImgVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author chenQF
 * @desc 用户证件照片相关
 * @date 2020/8/27 0027 10:44
 */
@RestController
@RequestMapping("/cms/user/img")
@Api(tags = "用户证件照片接口")
public class UserImgController extends BaseController {

    @Resource
    UserService userService;

    @ApiOperation(value = "下载用户证件照片(zip文件)")
    @GetMapping(value = "/downLoadImg")
    public void download(Integer userId, HttpServletRequest request, HttpServletResponse response) {
        try {
            String downloadFilename = "用户证件照片.zip";//文件的名称
            downloadFilename = URLEncoder.encode(downloadFilename, "UTF-8");//转换中文否则可能会产生乱码
            response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
            response.setHeader("Content-Disposition", "attachment;filename=" + downloadFilename);// 设置在下载框默认显示的文件名
            ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
            Set<UserImgVO> fileSet = userService.getUserImg(userId);
            for (UserImgVO img:fileSet) {
                URL url = new URL(img.getImgUrl());
                zos.putNextEntry(new ZipEntry(img.getImgRemark()));
                InputStream fis = url.openConnection().getInputStream();
                byte[] buffer = new byte[1024];
                int r = 0;
                while ((r = fis.read(buffer)) != -1) {
                    zos.write(buffer, 0, r);
                }
                fis.close();
            }
            zos.flush();
            zos.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
