package org.jeecgframework.core.common.service;

import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 导出Excel
 * @author Administrator
 * @date 2019-05-08
 * @version V1.0
 */
public interface CommonExcelService extends CommonService{

	/**
	 * excel报表导出
	 * @param title 标题
	 * @param titleSet 报表头
	 * @param dataSet 报表内容
	 * @return
	 */
	HSSFWorkbook exportExcel(String title, Collection<?> titleSet, Collection<?> dataSet);
}
