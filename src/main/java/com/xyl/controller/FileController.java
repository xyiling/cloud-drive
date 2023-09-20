package com.xyl.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xyl.entity.pojo.File;
import com.xyl.entity.pojo.TreeNode;
import com.xyl.entity.pojo.UserDir;
import com.xyl.service.FileService;
import com.xyl.service.UserDirService;
import com.xyl.entity.dto.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xyl
 * @since 2021-06-06
 */
@RestController
@RequestMapping("/drive/admin/file")
@CrossOrigin
@Api("文件表-文件操作")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserDirService userDirService;

    //根据名字模糊查询文件
    @ApiOperation(value = "根据名字模糊查询文件")
    @PostMapping("getFile/{memid}/{name}")
    public Result getFile(@PathVariable String name, @PathVariable String memid) {
        List<File> fileList = fileService.getFindFile(memid, name);
        UserDir userDir = userDirService.getUserDir(memid);
        TreeNode treeNode = JSON.parseObject(userDir.getMemDir(), new TypeReference<TreeNode>() {
        });
        List list = new ArrayList();
        findTreeNode(treeNode, name, list);
        return Result.ok().data("fileList", fileList).data("list", list);
    }

    private static void findTreeNode(TreeNode treeNode, String name, List list1) {
        List<TreeNode> list = treeNode.getChildrenList();
        if (list != null || !list.isEmpty()) {
            for (TreeNode node : list) {
                if (node.getName().contains(name)) {
                    list1.add(node);
                    List<TreeNode> list2 = node.getChildrenList();
                    System.out.println(list2);
                    if (list2.size() >= 1) {
                        findTreeNode(node, name, list1);
                    }
                } else {
                    findTreeNode(node, name, list1);
                }
            }
        }

    }

    //添加文件的接口方法
    @ApiOperation(value = "添加文件的信息到数据库")
    @PostMapping("addFile")
    public Result addFile(@RequestBody File file) {
        boolean save = service.save(file);
        if (save) {
            return Result.ok().data("file", file);
        } else {
            return Result.error();
        }

    }


    //查询当前用户下的所有文件
    @ApiOperation(value = "查询当前用户下的所有文件")
    @GetMapping("getFileList/{memId}")
    public Result getFileList(@PathVariable String memId) {
        List<File> fileList = service.getAllFileInfo(memId);
        return Result.ok().data("fileList", fileList);
    }

    //根据文件id查询文件具体信息
    @ApiOperation(value = "根据文件id查询文件具体信息")
    @GetMapping("getFileInfo/{id}")
    public Result getfileInfo(@PathVariable String id) {
        //System.out.println(memId);
        List<File> files = service.getFileInfo(id);
        //System.out.println(files);
        return Result.ok().data("file", files);
    }

    //文件重命名
    @ApiOperation(value = "文件重命名")
    @PostMapping("updateFile/{id}/{name}")
    public Result updateFile(@PathVariable String id, @PathVariable String name) {
        LambdaQueryWrapper<File> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq("id", id);
        File one = service.getOne(wrapper);
        File file = new File();
        file.setId(id);
        file.setName(name);
        file.setSize(one.getSize());
        boolean update = service.updateById(file);
        if (update) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //文件收藏
    @ApiOperation(value = "文件收藏")
    @PostMapping("collectFile")
    public Result collectionFile(@RequestParam("id") String[] id) {
        boolean flag = false;
        for (int i = 0; i < id.length; i++) {
            System.out.println(id[i]);
            File file = new File();
            file.setId(id[i]);
            file.setCollection(1);
            boolean update = service.updateById(file);
            if (update) {
                flag = true;
            }
        }
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //文件收藏
    @ApiOperation(value = "取消文件收藏")
    @PostMapping("cancelCollection")
    public Result cancelCollection(@RequestParam("id") String[] id) {
        boolean flag = false;
        for (int i = 0; i < id.length; i++) {
            System.out.println(id[i]);
            File file = new File();
            file.setId(id[i]);
            file.setCollection(0);
            boolean update = service.updateById(file);
            if (update) {
                flag = true;
            }
        }
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }

    //根据当前路径查询所有文件
    @ApiOperation(value = "根据当前路径查询所有文件")
    @PostMapping("getCurDirFiles/{id}")
    public Result setDirStruct(@RequestBody String userDir, @PathVariable String id) {
        //System.out.println(userDir);
        List<File> files = service.getCurFiles(userDir, id);
        System.out.println(files);
        return Result.ok().data("files", files);
    }

    //根据当前路径查询所有文件
    @ApiOperation(value = "多选文件移动")
    @PostMapping("fileMove")
    public Result fileMove(@RequestBody String movingPath, @RequestParam("id") String[] id) {
        boolean flag = false;
        for (int i = 0; i < id.length; i++) {
            System.out.println(id[i]);
            File file = new File();
            file.setId(id[i]);
            file.setFDir(movingPath);
            boolean update = service.updateById(file);
            if (update) {
                flag = true;
            }
        }
        if (flag) {
            return Result.ok();
        } else {
            return Result.error();
        }
    }
}
