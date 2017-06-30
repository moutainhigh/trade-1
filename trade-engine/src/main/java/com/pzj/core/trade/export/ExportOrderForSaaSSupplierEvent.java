/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.core.product.common.model.response.ProductTypeOutModel;
import com.pzj.core.product.common.service.IProductTypeService;
import com.pzj.core.trade.export.convert.SaaSOrderExcelModelConverter;
import com.pzj.core.trade.export.entity.OrderExportEntity;
import com.pzj.core.trade.export.entity.SaaSOrderExportEntity;
import com.pzj.core.trade.export.model.SaaSMerchExcelModel;
import com.pzj.core.trade.export.model.SaaSOrderExcelModel;
import com.pzj.core.trade.export.query.SaaSExportQuery;
import com.pzj.core.trade.export.read.OrderExportReadMapper;
import com.pzj.core.trade.export.util.UploadClient;
import com.pzj.core.trade.export.write.OrderExportWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.common.SalePortEnum;

/**
 * 自营生成导出文件并上传FTP
 * @author Administrator
 * @version $Id: OrderExportEngine.java, v 0.1 2017年2月7日 下午3:17:27 Administrator Exp $
 */
@Component
public class ExportOrderForSaaSSupplierEvent {

	private final static Logger logger = LoggerFactory.getLogger(ExportOrderForSaaSSupplierEvent.class);

	@Autowired
	private OrderExportReadMapper orderExportReadMapper;

	@Autowired
	private OrderExportWriteMapper orderExportWriteMapper;

	@Autowired
	private ExportOrderQueryEvent exportOrderQueryEvent;

	@Autowired
	private IProductTypeService iProductTypeService;

	@Autowired
	private UploadClient uploadClient;

	@Autowired
	private SaaSOrderExcelModelConverter saasOrderExcelModelConverter;

	@Autowired
	private SaaSExportQuery saaSExportQuery;

	public Boolean export(int export_id, int type) {
		logger.info("启动导出export_id:" + export_id);
		OrderExportEntity orderExportEntity = orderExportReadMapper.getExportById(export_id);
		if (Check.NuNObject(orderExportEntity)) {
			logger.error("不存在该导出ID:" + export_id);
			return false;
		}
		try {
			//查询记录
			ArrayList<SaaSOrderExportEntity> exports = saaSExportQuery.queryOrdersForSaaS(orderExportEntity.getParam(), type);

			if (Check.NuNCollections(exports)) {
				return true;
			}
			logger.info("导出数量为" + exports.size());

			//组装
			List<SaaSOrderExcelModel> orderExcels = saasOrderExcelModelConverter.convert(exports);

			//拼接excel
			generateExcel(orderExcels, orderExportEntity.getFile_name(), type);

			//上传
			uploadfFile(orderExportEntity);

			exportSuccessCallBack(orderExportEntity);
		} catch (Exception e) {
			logger.error("导出文件失败export_id" + export_id, e);
			orderExportEntity.setExport_state(2);
			String message = "导出失败";
			if (!Check.NuNObject(e.getMessage())) {
				message = e.getMessage().substring(0, 128);
			}
			orderExportEntity.setErr_msg(message);
			exportFailCallBack(orderExportEntity);
		}

		return true;
	}

	/**
	 * 
	 * @param orderExportEntity
	 * @throws FileNotFoundException
	 */
	private void uploadfFile(OrderExportEntity orderExportEntity) throws FileNotFoundException {
		FileInputStream in = new FileInputStream(orderExportEntity.getFile_name());
		uploadClient.uploadFile(orderExportEntity.getFile_name(), in);
		logger.info("上传成功!");

		File file = new File(orderExportEntity.getFile_name());
		file.delete();
	}

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public void exportSuccessCallBack(OrderExportEntity orderExportEntity) {
		orderExportEntity.setExport_state(1);
		orderExportWriteMapper.updateOrderExportStatus(orderExportEntity);
	}

	@Transactional(value = "trade.transactionManager", propagation = Propagation.REQUIRED, timeout = 3)
	public void exportFailCallBack(OrderExportEntity orderExportEntity) {
		orderExportEntity.setExport_state(2);
		orderExportWriteMapper.updateOrderExportStatus(orderExportEntity);
	}

	//生成excel文件
	private void generateExcel(List<SaaSOrderExcelModel> orderExcels, String fileName, int type) {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet();

		XSSFRow firstRow = sheet.createRow(0);
		XSSFCellStyle style = workBook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		firstRow.setRowStyle(style);
		createExcelTitle(firstRow);

		Iterator<SaaSOrderExcelModel> iterator = orderExcels.iterator();
		HashMap<Integer, String> productTypes = new HashMap<Integer, String>();
		XSSFRow row = null;

		for (int i = 1, orderIndex = 1; iterator.hasNext();) {
			SaaSOrderExcelModel order = iterator.next();
			List<SaaSMerchExcelModel> merchs = order.getMerchs();

			createMergeRegion(sheet, i, merchs);

			row = sheet.createRow(i++);
			{//订单属性
				SaaSMerchExcelModel merch = merchs.get(0);
				row.createCell(0).setCellValue(orderIndex++);
				row.createCell(1).setCellValue(order.getTransaction_id());
				row.createCell(2).setCellValue(OrderStatusEnum.getOrderStatus(order.getOrder_status()).getMsg());
				row.createCell(3).setCellValue(order.getCreate_time().toLocaleString());
				row.createCell(4).setCellValue(getStartDate(order.getStart_time(), merch.getMerch_type()));

				row.createCell(6).setCellValue(SalePortEnum.getSalePort(order.getSale_port()).getGroup());
				row.createCell(7).setCellValue(merch.getOriginal_suppliler_name());
				row.createCell(8).setCellValue(order.getSupplier_name());
				row.createCell(9).setCellValue(order.getReseller_name());

				row.createCell(10).setCellValue(order.getContactee());
				row.createCell(11).setCellValue(order.getContact_mobile());
				row.createCell(12).setCellValue(order.getIdcard_no());
				row.createCell(13).setCellValue(order.getContact_spelling());
				row.createCell(14).setCellValue(order.getContact_email());
				row.createCell(15).setCellValue(merch.getMerch_name());
				row.createCell(16).setCellValue(getProductType(merch.getMerch_type(), productTypes));
				row.createCell(18).setCellValue(merch.getProduct_varie() == 0 ? "散票" : "团票");

				row.createCell(42).setCellValue(order.getDelivery_addr());
				row.createCell(43).setCellValue(order.getGet_on_addr());
				row.createCell(44).setCellValue(order.getGet_off_addr());
				row.createCell(45).setCellValue(order.getExpect_use_car_time());
				row.createCell(46).setCellValue(order.getFlight_no());
				row.createCell(47).setCellValue(order.getTrain_no());
				row.createCell(48).setCellValue(order.getExpect_to_shop_time());
				row.createCell(49).setCellValue(order.getRemark());
			}
			{//商品属性，多行处理
				for (int flag = 0; flag < merchs.size(); flag++) {
					SaaSMerchExcelModel merch = merchs.get(flag);
					if (flag != 0) {
						row = sheet.createRow(i++);
					}
					row.createCell(5).setCellValue(getDateString(merch.getCheck_time()));
					row.createCell(17).setCellValue(merch.getSku_name());
					row.createCell(19).setCellValue(merch.getTotal_num());
					row.createCell(20).setCellValue(merch.getCheck_num());
					row.createCell(21).setCellValue(merch.getAuto_check_num());
					row.createCell(22).setCellValue(merch.getOverdue_num());
					row.createCell(23).setCellValue(merch.getRefund_num());

					boolean notShowSalePrice = type == 2 && order.getTransaction_id().equals(order.getOrder_id());
					if (!notShowSalePrice) {//初始分销商一级订单不展示销售价格
						row.createCell(24).setCellValue(merch.getSale_price());
						row.createCell(25).setCellValue(merch.getTotal_num() * merch.getSale_price());
						row.createCell(26).setCellValue(merch.getCheck_num() * merch.getSale_price());
						row.createCell(27).setCellValue(merch.getAuto_check_num() * merch.getSale_price());
						row.createCell(28).setCellValue(merch.getOverdue_num() * merch.getSale_price());
						row.createCell(29).setCellValue(merch.getRefund_num() * merch.getSale_price());
						row.createCell(30).setCellValue(merch.getSale_after_rebate());
						if (merch.getSale_after_rebate() > 0) {
							row.createCell(31).setCellValue(merch.getTotal_num() - merch.getRefund_num());
						} else {
							row.createCell(31).setCellValue(0);
						}
						row.createCell(32).setCellValue(
								merch.getSale_after_rebate() * (merch.getTotal_num() - merch.getRefund_num()));
						if (order.getOrder_status() == OrderStatusEnum.Cancelled.getValue()) {
							row.createCell(31).setCellValue(0);
							row.createCell(32).setCellValue(0);
						}
					}

					if (type == 2) {//代售才显示采购价格
						row.createCell(33).setCellValue(merch.getPurch_price());
						row.createCell(34).setCellValue(merch.getTotal_num() * merch.getPurch_price());
						row.createCell(35).setCellValue(merch.getCheck_num() * merch.getPurch_price());
						row.createCell(36).setCellValue(merch.getAuto_check_num() * merch.getPurch_price());
						row.createCell(37).setCellValue(merch.getOverdue_num() * merch.getPurch_price());
						row.createCell(38).setCellValue(merch.getRefund_num() * merch.getPurch_price());
						row.createCell(39).setCellValue(merch.getPurch_after_rebate());
						if (merch.getPurch_after_rebate() > 0) {
							row.createCell(40).setCellValue(merch.getTotal_num() - merch.getRefund_num());
						} else {
							row.createCell(40).setCellValue(0);
						}
						row.createCell(41).setCellValue(
								merch.getPurch_after_rebate() * (merch.getTotal_num() - merch.getRefund_num()));
						if (order.getOrder_status() == OrderStatusEnum.Cancelled.getValue()) {
							row.createCell(40).setCellValue(0);
							row.createCell(41).setCellValue(0);
						}
					}
				}
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			workBook.write(fos);
			fos.flush();
			fos.close();
			logger.info("导出成功!");
		} catch (Exception e) {
			logger.error("拼接EXCEL错误" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sheet
	 * @param i
	 * @param merchs
	 */
	private void createMergeRegion(XSSFSheet sheet, int i, List<SaaSMerchExcelModel> merchs) {
		int allNums = merchs.size();
		int lastRow = i + allNums - 1;
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 0, 0));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 1, 1));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 2, 2));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 3, 3));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 4, 4));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 6, 6));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 7, 7));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 8, 8));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 9, 9));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 10, 10));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 11, 11));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 12, 12));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 13, 13));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 14, 14));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 15, 15));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 16, 16));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 18, 18));

		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 42, 42));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 43, 43));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 44, 44));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 45, 45));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 46, 46));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 47, 47));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 48, 48));
		sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 49, 49));
	}

	/**
	 * 
	 * @param firstRow
	 */
	private void createExcelTitle(XSSFRow firstRow) {
		int index = 0;
		firstRow.createCell(index++).setCellValue("序号");
		firstRow.createCell(index++).setCellValue("订单号");
		firstRow.createCell(index++).setCellValue("订单状态");
		firstRow.createCell(index++).setCellValue("下单时间");
		firstRow.createCell(index++).setCellValue("出游时间");
		firstRow.createCell(index++).setCellValue("核销时间");
		firstRow.createCell(index++).setCellValue("订单来源");

		firstRow.createCell(index++).setCellValue("初始供应商");
		firstRow.createCell(index++).setCellValue("上级供应商");
		firstRow.createCell(index++).setCellValue("下级分销商");
		firstRow.createCell(index++).setCellValue("联系人");
		firstRow.createCell(index++).setCellValue("联系人手机");
		firstRow.createCell(index++).setCellValue("联系人身份证");
		firstRow.createCell(index++).setCellValue("联系人拼音");
		firstRow.createCell(index++).setCellValue("联系人邮箱");
		firstRow.createCell(index++).setCellValue("商品名称");
		firstRow.createCell(index++).setCellValue("商品类型");
		firstRow.createCell(index++).setCellValue("规格");
		firstRow.createCell(index++).setCellValue("团散");
		firstRow.createCell(index++).setCellValue("数量");
		firstRow.createCell(index++).setCellValue("核销数量");
		firstRow.createCell(index++).setCellValue("自动核销数量");
		firstRow.createCell(index++).setCellValue("逾期数量");
		firstRow.createCell(index++).setCellValue("退款数量");

		firstRow.createCell(index++).setCellValue("销售单价");
		firstRow.createCell(index++).setCellValue("销售小计");
		firstRow.createCell(index++).setCellValue("核销小计-销售");
		firstRow.createCell(index++).setCellValue("自动核销小计-销售");
		firstRow.createCell(index++).setCellValue("逾期小计-销售");
		firstRow.createCell(index++).setCellValue("退款小计-销售");
		firstRow.createCell(index++).setCellValue("后返应返金额-销售");
		firstRow.createCell(index++).setCellValue("后返应返数量-销售");
		firstRow.createCell(index++).setCellValue("后返应返小计-销售");

		firstRow.createCell(index++).setCellValue("采购单价");
		firstRow.createCell(index++).setCellValue("采购小计");
		firstRow.createCell(index++).setCellValue("核销小计-采购");
		firstRow.createCell(index++).setCellValue("自动核销小计-采购");
		firstRow.createCell(index++).setCellValue("逾期小计-采购");
		firstRow.createCell(index++).setCellValue("退款小计-采购");
		firstRow.createCell(index++).setCellValue("后返应返金额-采购");
		firstRow.createCell(index++).setCellValue("后返应返数量-采购");
		firstRow.createCell(index++).setCellValue("后返应返小计-采购");

		firstRow.createCell(index++).setCellValue("收货地址");
		firstRow.createCell(index++).setCellValue("上车地址");
		firstRow.createCell(index++).setCellValue("下车地址");
		firstRow.createCell(index++).setCellValue("期望接送时间");
		firstRow.createCell(index++).setCellValue("航班号");
		firstRow.createCell(index++).setCellValue("列次号");
		firstRow.createCell(index++).setCellValue("预计到店消费时间");
		firstRow.createCell(index++).setCellValue("分销端备注");
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	private String getDateString(Date date) {
		return Check.NuNObject(date) ? "" : date.toLocaleString();
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	private String getStartDate(Date date, int merchType) {
		return (date == null) ? "-" : new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	//映射产品类型名称
	private String getProductType(int type, HashMap<Integer, String> productTypes) {
		String typeMsg = productTypes.get(type);
		if (typeMsg == null) {
			Result<ProductTypeOutModel> typeOut = iProductTypeService.getById(type);
			if (!typeOut.isOk()) {
				return String.valueOf(type);
			}
			productTypes.put(type, typeOut.getData().getName());
			return typeOut.getData().getName();
		} else {
			return typeMsg;
		}
	}
}
