package com.xyl.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyl.entity.dto.Result;
import com.xyl.entity.pojo.File;
import com.xyl.entity.pojo.User;
import com.xyl.service.FileService;
import com.xyl.service.OssService;
import com.xyl.service.UserService;
import com.xyl.util.ResultUtil;
import com.xyl.util.handler.SpaceException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xyl.util.InitVodClient.initVodClient;


@RestController
@RequestMapping("/cloud-drive/oss")
@CrossOrigin
public class OssController {
    
    @Autowired
    private OssService ossService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    //上传头像
    @ApiOperation(value = "云存储-头像上传")
    @PostMapping("updateAvatar")
    public Result uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadAvatar(file);
        return Result.ok(url);
    }

    //判断上传的文件类型
    @ApiOperation(value = "上传文件")
    @PostMapping("upload")
    public Result upload(MultipartFile file, @RequestParam String catalogue, @PathVariable String userId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, userId);
        User one = userService.getUserById(wrapper);
        long neicun = one.getMemory();
        long size = file.getSize();
        long result = neicun + size;
        if (result < 1073741824) {
            User user = new User();
            user.setMemory(result);
            user.setId(userId);
            boolean b = userService.updateById(user);
            System.out.println(b);
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件类型
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String type = fileType.substring(1);
            if (type.equals("mp3") || type.equals("mpeg") || type.equals("vma") || type.equals("aac") || type.equals("ra") || type.equals("am") || type.equals("rmx") || type.equals("mp3")
                    || type.equals("avi") || type.equals("mov") || type.equals("rmvb") || type.equals("rm") || type.equals("mp4") || type.equals("3gp") || type.equals("flv") || type.equals("ape") || type.equals("flac") || type.equals("wmv")) {
                //获取文件名称
                String title = fileName.substring(0, fileName.lastIndexOf("."));
                File file1 = new File();
                file1.setSize(size);
                if (type.equals("mp3") || type.equals("mpeg") || type.equals("vma") || type.equals("aac") || type.equals("ra") || type.equals("am") || type.equals("rmx")
                        || type.equals("ape") || type.equals("flac")) {
                    file1.setFiletype("audio");
                }
                if (type.equals("avi") || type.equals("mov") || type.equals("wmv") || type.equals("rmvb") || type.equals("rm") || type.equals("mp4") || type.equals("3gp") || type.equals("flv")) {
                    file1.setFiletype("video");
                }
                file1.setName(title);
                file1.setType(type);
                file1.setFDir(catalogue);
                String videoId = ossService.uploadFile(file);
                file1.setVideoId(videoId);
                return Result.ok(file1, "操作成功");
            } else {
                File file1 = ossService.upload(file, catalogue);
                if ("".equals(file1)) {
                    return Result.fail("操作失败");
                }
                return Result.ok(file1, "操作成功");
            }
        } else {
            throw new SpaceException(20001, "内存溢出");
        }

    }

    @ApiOperation(value = "根据文件id删除文件")
    //根据文件id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{userId}")
    public Result removeAlyVideo(@RequestParam("idList") String[] idList, @PathVariable String userId) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getId, userId);
        User one = userService.getOne(wrapper);
        boolean flag = false;
        for (int i = 0; i < idList.length; i++) {
            //System.out.println(id);
            LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq("id", idList[i]);
            File file = fileService.getFiles(idList[i]);
            long result = one.getNeicun() - file.getSize();
            User user = new User();
            user.setId(userId);
            user.getStorageSize(result);
            userService.updateById(user);
            //System.out.println(file);
            if (file.getFiletype().equals("video") || file.getFiletype().equals("audio")) {
                System.out.println(file.getVideoId());
                String videoId = ossService.deleteVideo(idList[i]);
                String video = video(videoId);
                if (video.equals("success")) {
                    flag = true;
                }
            } else {
                String status = ossService.delete(idList[i]);
                flag = !status.equals("error");
            }
        }
        return ResultUtil.judgeBooleanValue(flag);
    }

    public static String video(String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = initVodClient(OssConstants.ACCESS_KEY_ID, OssConstants.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request设置视频id
            request.setVideoIds(videoId);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw new SpaceException(20001, "删除视频失败");
        }
    }

    //根据视频id获取视频凭证
    @ApiOperation(value = "根据视频videoId获取播放地址")
    @PostMapping("getPlayAuth")
    public Result getPlayAuth(@RequestParam("isList") List<String> isList) {

        ArrayList urlList = new ArrayList();
        File file = new File();
        // 创建SubmitMediaInfoJob实例并初始化
        DefaultProfile profile = DefaultProfile.getProfile(
                "cn-Shanghai",                // // 点播服务所在的地域ID，中国大陆地域请填cn-shanghai
                "LTAI5tNE97urNKXTDZaXCL48",        // 您的AccessKey ID
                "qfkZet3UlBHc3l1VnUFXVIJ9PmjXPn");    // 您的AccessKey Secret
        IAcsClient client = new DefaultAcsClient(profile);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        // 视频ID。
        for (int i = 0; i < isList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            file.setVideoId(isList.get(i));
            map.put("videoId", isList.get(i));
            request.setVideoId(isList.get(i));
            try {
                GetPlayInfoResponse response = client.getAcsResponse(request);
                for (GetPlayInfoResponse.PlayInfo playInfo : response.getPlayInfoList()) {
                    // 播放地址
                    System.out.println("PlayInfo.PlayURL = " + playInfo.getPlayURL());
                    file.setUrl(playInfo.getPlayURL());
                    map.put("url", playInfo.getPlayURL());
                    urlList.add(map);
                    request.setVideoId(null);
                }
            } catch (ServerException e) {
                e.printStackTrace();
            } catch (ClientException e) {
                System.out.println("ErrCode:" + e.getErrCode());
                System.out.println("ErrMsg:" + e.getErrMsg());
                System.out.println("RequestId:" + e.getRequestId());
            }
        }
        return ResultUtil.ok(urlList);
    }


}