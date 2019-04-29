package org.jeecgframework.core.common.service;

import org.jeecgframework.AbstractUnitTest;
import org.jeecgframework.core.common.model.common.DbTable;
import org.jeecgframework.web.system.pojo.base.UserEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author DELL
 * @version V1.0
 * @date 2019/4/29
 */
public class CommonServiceTest extends AbstractUnitTest {

    @Autowired
    private CommonService commonService;

    @Test
    public void testGetAllDbTableName() {
        List<DbTable> dbTableList = commonService.findDbTableList();
        System.out.println("=======");
    }

    @Test
    public void testGetAllDbTableSize() {
        Integer i = commonService.getDbTableSize();
        System.out.println("========");
    }

    @Test
    public void testGetById() {
        UserEntity u1 = commonService.getById(UserEntity.class, "402880e74d75c4dd014d75d44af30005");
        UserEntity u2 = commonService.getById(UserEntity.class, "402880e74d75c4dd014d75d44af30005");
        System.out.println("===");
    }
}
