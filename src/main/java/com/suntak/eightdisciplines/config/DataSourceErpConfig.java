package com.suntak.eightdisciplines.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Erp数据源配置类
 *
 */
@Configuration
@MapperScan(basePackages = "com.suntak.eightdisciplines.dbErp.dao", sqlSessionTemplateRef = "dbErpSqlSessionTemplate")
public class DataSourceErpConfig {

    /**
     * 生成数据源， @Primary 注解声明为默认数据源
     *
     * @return
     */
    @Bean(name = "dbErpDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.erp")
    public DataSource dbErpDataSource() {
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
    @Bean(name = "dbErpSqlSessionFactory")
    public SqlSessionFactory dbErpSqlSessionFactory(@Qualifier("dbErpDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/dbErp/*.xml"));
        return bean.getObject();
    }

    /**
     * 配置事务管理
     * @param dataSource
     * @return
     */
    @Bean(name = "dbErpTransactionManager")
    public DataSourceTransactionManager dbErpTransactionManager(@Qualifier("dbErpDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "dbErpSqlSessionTemplate")
    public SqlSessionTemplate dbErpSqlSessionTemplate(@Qualifier("dbErpSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws  Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
