/**
 * 版权声明：厦门中图壹购信息技术有限公司 版权所有 违者必究 2012 
 * 日    期：12-12-12
 */
package com.willow.platform.module.basic.menu;

import com.willow.platform.core.Page;
import com.willow.platform.core.PageParam;
import com.willow.platform.core.criteria.Criteria;
import com.willow.platform.module.basic.menu.dao.SysMenuDao;
import com.willow.platform.module.basic.menu.domain.SysMenu;
import com.willow.unitilsext.base.BaseWillowUnitilsTestNG;
import org.apache.ibatis.logging.LogFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.annotation.ExpectedDataSet;

import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

/**
 * <pre>
 * 系统菜单持久层单元测试
 * </pre>
 *
 * @author 朱贤俊
 * @version 1.00
 */
public class SysMenuDaoTest extends BaseWillowUnitilsTestNG {
    SysMenuDao sysMenuDao = new SysMenuDao();

    @BeforeClass
    public void setUp() {
        sysMenuDao.setSqlSessionTemplate(getSqlSessionTemplate());
    }

    @Test
    @DataSet("SysMenuDaoTest.saveTest.xls")
    @ExpectedDataSet
    public void saveTest() {
        SysMenu sysMenu = spy(new SysMenu());
        sysMenu.setObjId("1");
        sysMenu.setCreateTime("20121212111111");
        sysMenu.setUpdateTime("20121212111111");
        sysMenu.setUserId("1");
        sysMenu.setMenuName("菜单管理");
        sysMenu.setMenuCode("cdgl");
        sysMenu.setParentMenuId("ROOT");
        sysMenu.setOrderNo(1);
        sysMenu.setMenuType("1");
        sysMenu.setNodeType("1");
        when(sysMenu.getObjId()).thenReturn("1");
        when(sysMenu.getCreateTime()).thenReturn("20121212111111");
        when(sysMenu.getUpdateTime()).thenReturn("20121212111111");
        when(sysMenu.getUserId()).thenReturn("1");
        sysMenuDao.save(sysMenu);
    }

    @Test
    @DataSet("SysMenuDaoTest.xls")
    public void selectByObjIdTest() {
        SysMenu sysMenu = sysMenuDao.selectByObjId("1");
        assertNotNull(sysMenu);
        assertEquals("cdgl", sysMenu.getMenuCode());
    }


    @Test(dependsOnMethods = "selectByObjIdTest")
    @DataSet()
    public void deleteByObjIdTest() {
        sysMenuDao.deleteByObjId("1");
        SysMenu sysMenu = sysMenuDao.selectByObjId("1");
        assertNull(sysMenu);
    }

    @Test
    @DataSet
    public void deleteByParamTest() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentMenuId("ROOT");
        sysMenuDao.deleteByParam(sysMenu);
        SysMenu sysMenu1 = sysMenuDao.selectByObjId("1");
        assertNull(sysMenu1);
        SysMenu sysMenu2 = sysMenuDao.selectByObjId("1");
        assertNull(sysMenu2);
    }

    @Test
    @DataSet
    @ExpectedDataSet
    public void updateTest() {
        SysMenu sysMenu = spy(new SysMenu());
        sysMenu.setObjId("1");
        sysMenu.setCreateTime("20121212111111");
        sysMenu.setUpdateTime("20121212111111");
        sysMenu.setUserId("1");
        sysMenu.setMenuName("菜单管理");
        sysMenu.setMenuCode("a");
        sysMenu.setParentMenuId("a");
        sysMenu.setOrderNo(1);
        sysMenu.setMenuType("1");
        sysMenu.setNodeType("1");
        when(sysMenu.getObjId()).thenReturn("1");
        when(sysMenu.getCreateTime()).thenReturn("20121212111111");
        when(sysMenu.getUpdateTime()).thenReturn("20121212111111");
        when(sysMenu.getUserId()).thenReturn("1");
        sysMenuDao.update(sysMenu);
    }

    @Test
    @DataSet
    @ExpectedDataSet
    public void updateByIdSelectiveTest() {
        SysMenu sysMenu = spy(new SysMenu());
        sysMenu.setObjId("1");
        sysMenu.setMenuCode("a");
        sysMenu.setParentMenuId("a");
        when(sysMenu.getUpdateTime()).thenReturn("20121212111111");
        sysMenuDao.updateByIdSelective(sysMenu);
    }

    @Test
    @DataSet
    public void queryListTest() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentMenuId("ROOT");
        List<SysMenu> sysMenus = sysMenuDao.queryList(sysMenu);
        assertNotNull(sysMenus);
        assertEquals(2, sysMenus.size());
    }

    @Test
    @DataSet
    public void countListTest() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentMenuId("ROOT");
        int count = sysMenuDao.countList(sysMenu);
        assertEquals(2, count);
    }

    @Test
    @DataSet
    public void queryPageListTest() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentMenuId("ROOT");
        PageParam pageParam = new PageParam();
        pageParam.setPageSize(1);
        Page<SysMenu> sysMenuPage = sysMenuDao.queryPageList(sysMenu, pageParam);
        assertNotNull(sysMenuPage);
        assertNotNull(sysMenuPage.getResult());
        assertEquals(sysMenuPage.getTotalCount(), 2);
        assertEquals(sysMenuPage.getTotalCount(), 2);
    }

    @Test
    @DataSet
    public void queryPageListTest1() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setParentMenuId("ROOT");
        PageParam pageParam = new PageParam();
        pageParam.setSortFieldName("obj_id");
        pageParam.setSortType("desc");
        Page<SysMenu> sysMenuPage = sysMenuDao.queryPageList(sysMenu, pageParam);
        assertNotNull(sysMenuPage);
        assertNotNull(sysMenuPage.getResult());

    }

    @Test
    @DataSet
    public void selectByConditionTest() {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setMenuCode("cdgl");
        SysMenu sysMenu1 = sysMenuDao.selectByCondition(sysMenu);
        assertNotNull(sysMenu1);
        assertNotNull(sysMenu1.getMenuName(), "菜单管理");
    }

    @Test
    @DataSet
    public void queryListByCriteriaTest() {
        Criteria criteria = new Criteria();
        criteria.andFieldEqualTo("parent_menu_id", "ROOT");
        List<SysMenu> sysMenus = sysMenuDao.queryListByCriteria(criteria, null);
        assertNotNull(sysMenus);
        assertEquals(2, sysMenus.size());
    }

    @Test
    @DataSet
    public void queryListByCriteriaTest1() {
        Criteria criteria = new Criteria();
        criteria.andFieldEqualTo("obj_id", "1").andFieldEqualTo("parent_menu_id", "a");
        List<SysMenu> sysMenus = sysMenuDao.queryListByCriteria(criteria, null);
        assertNotNull(sysMenus);
        assertEquals(0, sysMenus.size());
    }

    @Test
    @DataSet
    public void queryListByCriteriaTest2() {
        Criteria criteria = new Criteria();
        criteria.andFieldEqualTo("obj_id", "1");
        List<SysMenu> sysMenus = sysMenuDao.queryListByCriteria(criteria, null);
        assertNotNull(sysMenus);
        assertEquals(1, sysMenus.size());
    }


    @Test
    @DataSet
    public void countListByCriteriaTest() {
        Criteria criteria = new Criteria();
        criteria.andFieldEqualTo("parent_menu_id", "ROOT");
        int count = sysMenuDao.countListByCriteria(criteria);
        assertEquals(count, 2);
    }


    @Test
    @DataSet
    public void querySysMenusBuParentMenuIdTest() {
        List<SysMenu> sysMenuList = sysMenuDao.querySysMenusBuParentMenuId("ROOT");
        assertNotNull(sysMenuList);
        assertEquals(2, sysMenuList.size());
    }

}
