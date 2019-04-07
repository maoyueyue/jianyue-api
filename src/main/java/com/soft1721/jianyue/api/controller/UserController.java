package com.soft1721.jianyue.api.controller;

import com.aliyun.oss.OSSClient;
import com.soft1721.jianyue.api.config.RedisConfig;
import com.soft1721.jianyue.api.entity.User;
import com.soft1721.jianyue.api.entity.dto.UserDTO;
import com.soft1721.jianyue.api.service.RedisService;
import com.soft1721.jianyue.api.service.UserService;
import com.soft1721.jianyue.api.util.*;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private RedisService redisService;

    @PostMapping(value = "/sign_in")
    public ResponseResult signIn(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO);
        User user = userService.getUserByMobile(userDTO.getMobile());
        if (user == null) {
            return ResponseResult.error(StatusConst.USER_MOBILE_NOT_FOUND, MsgConst.USER_MOBILE_NO_FOUND);
        } else {
            //手机号存在，将明文密码转成Base64密文后进行登录
            userDTO.setPassword(StringUtil.getBase64Encoder(userDTO.getPassword()));
            int status = userService.signIn(userDTO);
            if (status == StatusConst.SUCCESS) {
                return ResponseResult.success(user);
            } else if (status == StatusConst.PASSWORD_ERROR) {
                return ResponseResult.error(status, MsgConst.PASSWORD_ERROR);
            } else {
                return ResponseResult.error(status, MsgConst.USER_STATUS_ERROR);
            }
        }
    }

    @PostMapping("/avatar")
    public String ossUpload(@RequestParam("file") MultipartFile sourceFile, @RequestParam("userId") int userId) {
        System.out.println(userId);
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAIBFVnODRHEjCL";
        String accessKeySecret = "Bk8yEz8F9UnPqAk5SbFQMRkif5X6fv";
        String bucketName = "niit-soft1721-25";
        String filedir = "avatar/";
        // 获取文件名
        String fileName = sourceFile.getOriginalFilename();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        //uuid生成主文件名
        String prefix = UUID.randomUUID().toString();
        String newFileName = prefix + suffix;
        File tempFile = null;
        try {
            //创建临时文件
            tempFile = File.createTempFile(prefix, prefix);
            // MultipartFile to File
            sourceFile.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, filedir + newFileName, tempFile);
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, filedir + newFileName, expiration);
        ossClient.shutdown();
        User user = userService.getUserById(userId);
        user.setAvatar(url.toString());
        userService.updateUser(user);
        return url.toString();
    }

    @GetMapping(value = "/{id}")
    public ResponseResult getUserById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        return ResponseResult.success(user);
    }

    @PostMapping("/nickname")
    public String nameUpload(@RequestParam("nickname") String nickname, @RequestParam("userId") int userId) {
        System.out.println(userId);
        User user=userService.getUserById(userId);
        user.setNickname(nickname);
        userService.updateUser(user);
        return nickname;
    }

    @PostMapping("/changepsd")
    public String changePsd(@RequestParam("password") String password, @RequestParam("mobile") String mobile) {
        System.out.println(mobile);
        User user=userService.getUserByMobile(mobile);
        user.setPassword(StringUtil.getBase64Encoder(password));
        userService.updateUser(user);
        return mobile;
    }

    @PostMapping(value = "/fgtverify")
    public ResponseResult forgetVerifyCode(@RequestParam("mobile") String mobile) {
        User user = userService.getUserByMobile(mobile);
        if (user == null) {
            return ResponseResult.error(StatusConst.USER_MOBILE_UNEXIST, MsgConst.USER_MOBILE_UNEXIST);
        } else {
            String verifyCode = SMSUtil.send(mobile);
            System.out.println(verifyCode);
            redisService.set(mobile, verifyCode);
            return ResponseResult.success();
        }
    }

    @PostMapping(value = "/verify")
    public ResponseResult getVerifyCode(@RequestParam("mobile") String mobile) {
        User user = userService.getUserByMobile(mobile);
        if (user != null) {
            return ResponseResult.error(StatusConst.MOBILE_EXIST, MsgConst.MOBILE_EXIST);
        } else {
            String verifyCode = SMSUtil.send(mobile);
//            String verifyCode = StringUtil.getVerifyCode();
            System.out.println(verifyCode);
            redisService.set(mobile, verifyCode);
            return ResponseResult.success();
        }
    }

    @PostMapping(value = "/check")
    public ResponseResult checkVerifyCode(@RequestParam("mobile") String mobile, @RequestParam("verifyCode") String verifyCode) {

            String code = null;
            try {
                code = redisService.get(mobile).toString();
        }catch (NullPointerException e){
                return ResponseResult.error(StatusConst.CODE_USELESS, MsgConst.CODE_USELESS);
        }
            System.out.println(code + "---");
            System.out.println(verifyCode);
            if (code.equals(verifyCode)) {
                return ResponseResult.success();
            } else {
                return ResponseResult.error(StatusConst.VERIFYCODE_ERROR, MsgConst.VERIFYCODE_ERROR);
            }
    }


    @PostMapping(value = "/sign_up")
    public ResponseResult signUp(@RequestBody UserDTO userDTO) {
        userService.signUp(userDTO);
        return ResponseResult.success();
    }
}