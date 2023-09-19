package com.xyl.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @author xyl
 * date 2023-09-19
 * desc 配置文档：https://baomidou.com/pages/981406/
 */
public class CodeGen {

    public static final String DB_IP = "121.37.8.197";
    public static final String AUTHOR = "xyl";
    public static final String DBNAME = "drive";

    public static void main(String[] args) throws Exception {

        String tableName = scanner("表名");

        String url = "jdbc:mysql://" + DB_IP + ":3306/" + DBNAME + "?useUnicode=true&autoReconnect=true&allowMultiQueries=true&useSSL=false";

        String projectPath = System.getProperty("user.dir");
        String outputDir = projectPath + "/src/main/java";
        FastAutoGenerator.create(url, "root", "zmpq1597389_+").globalConfig(builder -> {
                    builder.author(AUTHOR) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .disableOpenDir()//生成后是否打开资源管理器
                            .dateType(DateType.ONLY_DATE)//DateType.ONLY_DATE 默认值: DateType.TIME_PACK
                            .outputDir(outputDir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.xyl") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .mapper("entity.pojo")
                            .xml("com/xyl/entity/dao");
                    //.pathInfo(Collections.singletonMap(OutputFile.xml, "D://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tableName) // 设置需要生成的表名
                            .entityBuilder().enableLombok().enableTableFieldAnnotation()
                            .logicDeleteColumnName("is_deleted") // 开启 lombok 模型
                            .versionColumnName("version")////乐观锁列
                            .mapperBuilder().formatMapperFileName("%sMapper")
                            .controllerBuilder().enableRestStyle()//开启生成@RestController 控制器
                    ;
                })
                .templateConfig(builder -> {
                    builder.controller("");//不生成模板
                    //builder.service("");//将service模板设置为空，则不生成模板
                    builder.xml("/codeTemplates/mapper.xml");//更改为自定义模板
                    builder.mapper("/codeTemplates/mapper.java");
                })
                .execute();

    }

    public static String scanner(String tip) throws Exception {
        return scanner(tip, false);
    }

    public static String scanner(String tip, boolean isSkipEmpty) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (isSkipEmpty) {
            if (scanner.hasNextLine()) {
                String ipt = scanner.nextLine();
                ipt = ipt.trim();
                if (StringUtils.isNotEmpty(ipt)) {
                    return ipt;
                }
                return ipt;
            }
        }
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new Exception("请输入正确的" + tip + "！");
    }

}
