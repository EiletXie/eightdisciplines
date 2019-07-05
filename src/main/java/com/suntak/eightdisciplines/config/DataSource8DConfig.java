package com.suntak.eightdisciplines.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.servlet.MultipartConfigElement;
import javax.sql.DataSource;

/**
 * 8D数据源配置
 */
@Configuration
@MapperScan(basePackages = "com.suntak.eightdisciplines.db8d.dao", sqlSessionTemplateRef = "db8dSqlSessionTemplate")
public class DataSource8DConfig {

    /**
     * 生成数据源， @Primary 注解声明为默认数据源
     *
     * @return
     */
    @Bean(name = "db8dDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.8d")
    @Primary
    public DataSource db8dDataSource() {

        return DataSourceBuilder.create().build();
    }

    /**
     * SqlSessionTemplate是MyBatis-Spring的核心。
     * 这个类负责管理MyBatis的SqlSession,调用MyBatis的SQL方法，翻译异常。
     * SqlSessionTemplate是线程安全的，可以被多个DAO所共享使用
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "db8dSqlSessionFactory")
    @Primary
    public SqlSessionFactory db8dSqlSessionFactory(@Qualifier("db8dDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/db8d/*.xml"));
        return bean.getObject();
    }

    /**
     * 配置事务管理
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "db8dTransactionManager")
    @Primary
    public DataSourceTransactionManager db8dTransactionManager(@Qualifier("db8dDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "db8dSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate db8dSqlSessionTemplate(@Qualifier("db8dSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
