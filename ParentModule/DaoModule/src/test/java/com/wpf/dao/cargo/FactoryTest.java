package com.wpf.dao.cargo;

import com.wpf.domain.cargo.Factory;
import com.wpf.domain.cargo.FactoryExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * 创建时间：2020/11/13
 * Mybatis逆向工程测试
 * @author wpf
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class FactoryTest {

    @Autowired
    FactoryDao factoryDao;

    @Test
    public void selectTest() {
        FactoryExample example = new FactoryExample();
        //按某一个属性升序或降序排序(降序就加上desc)
        example.setOrderByClause("id desc");
        FactoryExample.Criteria criteria = example.createCriteria();
        criteria.andFullNameLike("%玻璃%");
        List<Factory> factories = factoryDao.selectByExample(example);
        factories.forEach(System.out::println);
    }

}
