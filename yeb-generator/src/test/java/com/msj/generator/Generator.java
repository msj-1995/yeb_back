package com.msj.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 执行main方法，方法控制台输入模块表名回车自动生成对应项目目录中
 */
public class Generator {
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + ": ");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的:" + tip + "!");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        // 自动生成的代码存放的位置
        gc.setOutputDir(projectPath + "/yeb-generator/src/main/java");
        // 作者
        gc.setAuthor("msj");
        // 打开输出目录
        gc.setOpen(false);
        // xml开启BaseResultMap
        gc.setBaseResultMap(true);
        // xml 开启BaseColumnList
        gc.setBaseColumnList(true);
        // 实体属性Swagger2注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/yeb?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dsc.setUsername("root");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setPassword("1234");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.msj")
                .setEntity("pojo")
                .setMapper("mapper")
                .setService("service")
                .setServiceImpl("service.impl")
                .setController("controller");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是freemaker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板隐情史velocity
        // String templatePath = "/templates/mapper.xml.vm"

        // 自定义配置输出
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名，如果你Entity设置了前缀，此处注意xml的名称会跟着发生变化
                return projectPath + "yeb-generator/src/main/resources/mapper" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        // 数据库表名字映射到实体的命名策略
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略
        strategyConfig.setColumnNaming(NamingStrategy.no_change);
        // lombok模型
        strategyConfig.setEntityLombokModel(true);
        // 生成@RestControler控制器
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setInclude(scanner("表明，多个英文逗号分割").split(","));
        strategyConfig.setControllerMappingHyphenStyle(true);
        // 表前缀
        strategyConfig.setTablePrefix("t_");
        mpg.setStrategy(strategyConfig);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}