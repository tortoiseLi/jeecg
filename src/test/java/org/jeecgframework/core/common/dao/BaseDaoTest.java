package org.jeecgframework.core.common.dao;

import org.jeecgframework.AbstractUnitTest;
import org.jeecgframework.core.util.IpUtil;
import org.jeecgframework.web.system.pojo.base.TimeTaskEntity;
import org.jeecgframework.web.system.user.entity.TestEntity;
import org.jeecgframework.web.system.user.entity.TestTable;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author DELL
 * @version V1.0
 * @date 2019/4/30
 */
public class BaseDaoTest extends AbstractUnitTest {

    @Autowired
    private BaseDao baseDao;

    private TestEntity testEntity;
    private TestEntity testEntity1;
    private TestEntity testEntity2;
    private List<TestEntity> testEntityList;

    @Before
    public void initData() {
        testEntityList = new ArrayList<>();

        testEntity = new TestEntity();
        testEntity.setId("1");
        testEntity.setStrStr1("第一");
        testEntity.setStrStr2("第二");
        testEntity.setStrStr3("第三");
        testEntity.setStrStr4("第四");
        testEntity.setStrStr5("第五");
        testEntityList.add(testEntity);

        testEntity1 = new TestEntity();
        testEntity1.setId("2");
        testEntity1.setStrStr1("第一");
        testEntity1.setStrStr2("第二");
        testEntity1.setStrStr3("第三");
        testEntity1.setStrStr4("第四");
        testEntity1.setStrStr5("第五");
        testEntityList.add(testEntity1);

        testEntity2 = new TestEntity();
        testEntity2.setId("3");
        testEntity2.setStrStr1("第一");
        testEntity2.setStrStr2("第二");
        testEntity2.setStrStr3("第三");
        testEntity2.setStrStr4("第四");
        testEntity2.setStrStr5("第五");
        testEntityList.add(testEntity2);
    }

    @Test
    public void testInsert() {
        /*<T> void testInsert(T entity);*/

        Serializable id = baseDao.insert(testEntity);
        System.out.println(id.toString());
    }

    @Test
    public void batchInsert() {
        /*<T> void batchInsert(List<T> entityList);*/

        baseDao.batchInsert(testEntityList);
    }

    @Test
    public void delete() {
        /*<T> void delete(T entity);*/

        baseDao.delete(testEntity);
    }

    @Test
    public void deleteById() {
        /*void deleteById(Class entityName, Serializable id);*/

        baseDao.deleteById(TestEntity.class, "1");
    }

    @Test
    public void deleteByCollection() {
        /*<T> void deleteByCollection(Collection<T> collection);*/

        baseDao.deleteByCollection(testEntityList);
    }

    @Test
    public void update() {
        /*<T> void update(T entity);*/

        baseDao.update(testEntity);
    }

    @Test
    public void updateBySql() {
        /*int updateBySql(String sql);*/

        String sql = "update test set str_str1 = '张三' where id = '1'";
        int updateResult = baseDao.updateBySql(sql);
        System.out.println(updateResult);
    }

    @Test
    public void saveOrUpdate() {
        /*<T> void saveOrUpdate(T entity);*/

        baseDao.saveOrUpdate(testEntity);
    }

    @Test
    public void getById() {
        /*<T> T getById(Class<T> entityClass, Serializable id);*/

        TestEntity testEntity = baseDao.getById(TestEntity.class, "1");
        System.out.println(testEntity.toString());
    }

    @Test
    public void getByProperty() {
        /*<T> T getByProperty(Class<T> entityClass, String propertyName, Object value);*/

        TestEntity testEntity = baseDao.getByProperty(TestEntity.class, "strStr1", "张一");
        System.out.println(testEntity.toString());
    }

    @Test
    public void getBySql() {
        /*<T> T getBySql(String sql);*/

        /*Map<String, Object> getBySql(String sql, Object... params);*/
    }

    @Test
    public void getByHql() {
        /*<T> T getByHql(String hql);*/
        /*<T> List<T> findListByHql(String hql);*/


    }

    @Test
    public void findList() {
        /*<T> List<T> findList(Class<T> entityClass);*/
    }

    @Test
    public void findListBySql() {
        /*<T> List<T> findListBySql(String sql);*/
        /*<T> List<T> findListBySql(String sql, Object... params);*/
    }

    @Test
    public void findListByHql() {
        /*<T> List<T> findListByHql(String hql, Object... param);*/
    }

    @Test
    public void findListByProperty() {
        /*<T> List<T> findListByProperty(Class<T> entityClass, String propertyName, Object value);*/
    }

    @Test
    public void findListByCriteriaQuery() {
        /*<T> List<T> findListByCriteriaQuery(final CriteriaQuery cq, Boolean isPage);*/
    }

    @Test
    public void findListForJdbc() {
        /*List<Map<String, Object>> findListForJdbc(String sql, Object... params);*/
        /*List<Map<String, Object>> findListForJdbc(String sql, int page, int rows);*/
        /*<T> List<T> findListForJdbc(String sql, Class<T> clazz);*/

        String sql = "select id,str_str1 as strStr1 from test";
        List<TestTable> testEntityList = baseDao.findListBySql(sql, TestTable.class);
        System.out.println(testEntityList.size());

        /*<T> List<T> findListForJdbc(String sql, int page, int rows, Class<T> clazz);*/
    }

    @Test
    public void findPageByCriteriaQuery() {
        /*PageList findPageByCriteriaQuery(final CriteriaQuery cq, final boolean isOffset);*/
    }

    @Test
    public void findByExample() {
        /*List findByExample(final String entityName, final Object exampleEntity);*/

        TimeTaskEntity timTask = new TimeTaskEntity();
        timTask.setIsEffect("1");
        timTask.setIsStart("1");
        List<TimeTaskEntity> tasks = (List<TimeTaskEntity>) baseDao.findListByEntity(TimeTaskEntity.class.getName(), timTask);

        TestEntity testEntity = new TestEntity();
        testEntity.setStrStr1("张一");
        testEntity.setStrStr2("张");
        List<TestEntity> testEntityList = baseDao.findListByEntity(TestEntity.class.getName(), testEntity);

        System.out.println(tasks.size());
    }

    @Test
    public void getHashMapbyQuery() {
        /*Map<Object, Object> getHashMapbyQuery(String query);*/
    }

    @Test
    public void getDataTableReturn() {
        /*DataTableReturn getDataTableReturn(final CriteriaQuery cq, final boolean isOffset);*/
    }

    @Test
    public void getDataGridReturn() {
        /*void getDataGridReturn(CriteriaQuery cq, final boolean isOffset);*/
    }

    @Test
    public void findListMapBySql() {
        /*List<Map<String, Object>> findListMapBySql(String sql, Object...params);*/

        String sql = "select * from test where id = '1'";
        List<Map<String, Object>> mapList = baseDao.findListMapBySql(sql);
        System.out.println(mapList.size());
    }

    @Test
    public void executeSql() {
        /*Integer executeSql(String sql, List<Object> paramList);*/
        /*Integer executeSql(String sql, Object... params);*/
        /*Integer executeSql(String sql, Map<String, Object> paramMap);*/
    }

    @Test
    public void executeSqlReturnKey() {
        /*Object executeSqlReturnKey(String sql, Map<String, Object> paramMap);*/
    }

    @Test
    public void executeHql() {
        /*Integer executeHql(String hql);*/
        /*Integer executeHql(String hql, Object...params);*/
    }

    @Test
    public void getCountForJdbc() {
        /*Long getCountForJdbc(String sql);*/
        /*Long getCountForJdbc(String sql, Object[] params);*/
    }

    @Test
    public void findForJdbcParam() {
        /*List<Map<String, Object>> findForJdbcParam(String sql, int page, int rows, Object... objs);*/
    }

    @Test
    public void pageList() {
        /*<T> List<T> pageList(DetachedCriteria dc, int firstResult, int maxResult);*/
    }

    @Test
    public void findByDetached() {
        /*<T> List<T> findByDetached(DetachedCriteria dc);*/
    }

    @Test
    public void executeProcedure() {
        /*<T> List<T> executeProcedure(String procedureSql, Object... params);*/
    }

    @Test
    public void findDbTableList() {
        /*List<DbTable> findDbTableList();*/
    }

    @Test
    public void getDbTableSize() {
        /*Integer getDbTableSize();*/
    }

    @Test
    public void getSession() {
        /*Session getSession();*/
    }
}
