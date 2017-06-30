/**
 * piaozhijia.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.pzj.core.trade.export;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pzj.base.service.sys.IUserService;
import com.pzj.core.product.common.model.response.ProductTypeOutModel;
import com.pzj.core.product.common.service.IProductTypeService;
import com.pzj.core.sku.common.constants.ProductTypeGlobal;
import com.pzj.core.trade.export.convert.OrderExcelModelConverter;
import com.pzj.core.trade.export.entity.MerchExportExcelEntity;
import com.pzj.core.trade.export.entity.OrderExportEntity;
import com.pzj.core.trade.export.entity.OrderExportExcelEntity;
import com.pzj.core.trade.export.model.MerchExcelModel;
import com.pzj.core.trade.export.model.OrderExcelModel;
import com.pzj.core.trade.export.model.SkuExcelModel;
import com.pzj.core.trade.export.read.OrderExportReadMapper;
import com.pzj.core.trade.export.util.UploadClient;
import com.pzj.core.trade.export.write.OrderExportWriteMapper;
import com.pzj.framework.context.Result;
import com.pzj.framework.toolkit.Check;
import com.pzj.trade.order.common.MerchStateEnum;
import com.pzj.trade.order.common.OrderStatusEnum;
import com.pzj.trade.order.common.SalePortEnum;

/**
 * 生成导出文件并上传FTP
 * @author Administrator
 * @version $Id: OrderExportEngine.java, v 0.1 2017年2月7日 下午3:17:27 Administrator Exp $
 */
@Service
public class ExportOrderAndUploadEnvnt {

	private final static Logger logger = LoggerFactory.getLogger(ExportOrderAndUploadEnvnt.class);

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
	private IUserService userService;

	@Autowired
	private OrderExcelModelConverter orderExcelModelConverter;

	public static void main(String[] args) {
		ArrayList<Long> a = new ArrayList<Long>();
		a.add(1L);
		a.add(2L);
		for (int i = 0; i < a.size(); i++) {
			a.remove(i);
			System.out.println(a.size());
		}

	}

	public Boolean export(int export_id) {
		logger.info("启动导出export_id:" + export_id);
		OrderExportEntity orderExportEntity = orderExportReadMapper.getExportById(export_id);
		if (Check.NuNObject(orderExportEntity)) {
			logger.error("不存在该导出ID:" + export_id);
			return false;
		}
		try {
			ArrayList<OrderExportExcelEntity> exports = exportOrderQueryEvent.queryOrdersByCondition(orderExportEntity
					.getParam());//查询订单
			if (Check.NuNCollections(exports)) {
				return true;
			}
			logger.info("导出数量为" + exports.size());

			List<OrderExcelModel> orderExcels = orderExcelModelConverter.convert(exports);

			//上传
			generateExcel(orderExcels, orderExportEntity.getFile_name());//拼装excel
			logger.info("导出成功!");

			FileInputStream in = new FileInputStream(orderExportEntity.getFile_name());
			uploadClient.uploadFile(orderExportEntity.getFile_name(), in);
			logger.info("上传成功!");

			File file = new File(orderExportEntity.getFile_name());
			file.delete();
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

	/**
	 * 
	 * @param exports
	 * @return
	 */
	private ArrayList<OrderExportExcelEntity> asseblyMerch(ArrayList<OrderExportExcelEntity> exports) {
		HashMap<String, OrderExportExcelEntity> maps = new HashMap<String, OrderExportExcelEntity>();
		for (OrderExportExcelEntity export : exports) {
			MerchExportExcelEntity merch = new MerchExportExcelEntity();
			merch.setSku_name(export.getMerch_name());
			merch.setNumber(export.getTotal_num());

			if (maps.containsKey(export.getOrder_id())) {
				OrderExportExcelEntity item = maps.get(export.getOrder_id());
				//item.getMerchs().add(merch);
				maps.put(export.getOrder_id(), item);

			} else {
				//export.getMerchs().add(merch);
				maps.put(export.getOrder_id(), export);
			}
		}
		return new ArrayList<OrderExportExcelEntity>(maps.values());

	}

	//生成excel文件
	private void generateExcel(List<OrderExcelModel> orderExcels, String fileName) {
		XSSFWorkbook workBook = new XSSFWorkbook();
		XSSFSheet sheet = workBook.createSheet();

		XSSFRow firstRow = sheet.createRow(0);
		XSSFCellStyle style = workBook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		firstRow.setRowStyle(style);
		int index = 0;
		firstRow.createCell(index++).setCellValue("序号");
		firstRow.createCell(index++).setCellValue("销售订单号");
		firstRow.createCell(index++).setCellValue("订单状态");
		firstRow.createCell(index++).setCellValue("订单来源");
		firstRow.createCell(index++).setCellValue("取票人");
		firstRow.createCell(index++).setCellValue("出游/入住时间");
		firstRow.createCell(index++).setCellValue("下单时间");
		firstRow.createCell(index++).setCellValue("销售订单金额");
		firstRow.createCell(index++).setCellValue("团散");
		firstRow.createCell(index++).setCellValue("直签");
		firstRow.createCell(index++).setCellValue("分销商");
		firstRow.createCell(index++).setCellValue("供应商");
		firstRow.createCell(index++).setCellValue("商品状态");
		firstRow.createCell(index++).setCellValue("商品名称");
		firstRow.createCell(index++).setCellValue("商品类型");
		firstRow.createCell(index++).setCellValue("规格");
		firstRow.createCell(index++).setCellValue("数量");
		firstRow.createCell(index++).setCellValue("核销时间");
		firstRow.createCell(index++).setCellValue("逾期数量");
		firstRow.createCell(index++).setCellValue("销售单价");
		firstRow.createCell(index++).setCellValue("销售小计");
		firstRow.createCell(index++).setCellValue("销售后返");
		firstRow.createCell(index++).setCellValue("采购单价");
		firstRow.createCell(index++).setCellValue("采购小计");
		firstRow.createCell(index++).setCellValue("采购后返");

		Iterator<OrderExcelModel> iterator = orderExcels.iterator();
		HashMap<Integer, String> productTypes = new HashMap<Integer, String>();
		XSSFRow row = null;

		for (int i = 1, orderIndex = 1; iterator.hasNext();) {
			OrderExcelModel order = iterator.next();
			List<MerchExcelModel> merchs = order.getMerchs();
			int allNums = 0;
			for (MerchExcelModel merch : merchs) {
				allNums += merch.getSkus().size();
			}
			//
			int lastRow = i + allNums - 1;
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 0, 0));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 1, 1));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 2, 2));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 3, 3));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 4, 4));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 5, 5));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 6, 6));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 7, 7));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 8, 8));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 9, 9));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 10, 10));
			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 11, 11));

			sheet.addMergedRegion(new CellRangeAddress(i, lastRow, 14, 14));

			row = sheet.createRow(i++);
			{
				row.createCell(0).setCellValue(orderIndex++);
				row.createCell(1).setCellValue(order.getOrder_id());
				row.createCell(2).setCellValue(OrderStatusEnum.getOrderStatus(order.getOrder_status()).getMsg());
				row.createCell(3).setCellValue(SalePortEnum.getSalePort(order.getSale_port()).getName());
				row.createCell(4).setCellValue(order.getContactee());
				row.createCell(5).setCellValue(
						Check.NuNObject(order.getStart_time()) ? "" : order.getStart_time().toLocaleString());
				row.createCell(6).setCellValue(order.getCreate_time().toLocaleString());
				row.createCell(7).setCellValue(order.getTotal_amount());
				row.createCell(8).setCellValue(order.getProduct_varie() == 0 ? "散票" : "团票");
				row.createCell(9).setCellValue(order.getIs_direct() == 0 ? "否" : "是");
				row.createCell(10).setCellValue(order.getReseller_name());
				row.createCell(11).setCellValue(order.getSupplier_name());
				MerchExcelModel merch = order.getMerchs().get(0);

				row.createCell(14).setCellValue(getProductType(merch.getMerch_type(), productTypes));
			}
			{
				for (int flag = 0; flag < merchs.size(); flag++) {
					MerchExcelModel merch = merchs.get(flag);
					if (flag != 0) {
						row = sheet.createRow(i++);
					}
					int currentRow = row.getRowNum();
					int merchLastRow = currentRow + merch.getSkus().size() - 1;
					sheet.addMergedRegion(new CellRangeAddress(currentRow, merchLastRow, 13, 13));
					sheet.addMergedRegion(new CellRangeAddress(currentRow, merchLastRow, 15, 15));
					sheet.addMergedRegion(new CellRangeAddress(currentRow, merchLastRow, 17, 17));
					sheet.addMergedRegion(new CellRangeAddress(currentRow, merchLastRow, 18, 18));
					sheet.addMergedRegion(new CellRangeAddress(currentRow, merchLastRow, 19, 19));
					sheet.addMergedRegion(new CellRangeAddress(currentRow, merchLastRow, 20, 20));
					row.createCell(13).setCellValue(merch.getMerch_name());
					row.createCell(15).setCellValue(merch.getSku_name());

					row.createCell(17).setCellValue(
							((merch.getCheck_time() == null) ? "" : merch.getCheck_time().toLocaleString()));
					row.createCell(18).setCellValue(merch.getOverdue_num());
					for (int j = 0; j < merch.getSkus().size(); j++) {
						SkuExcelModel sku = merch.getSkus().get(j);
						if (j != 0) {
							row = sheet.createRow(i++);
						}
						try {
							row.createCell(12).setCellValue(MerchStateEnum.getMerchState(sku.getMerch_state()).getMsg());
						} catch (Exception e) {
							logger.error(String.valueOf(sku.getMerch_state()), e);
						}
						row.createCell(16).setCellValue(sku.getNum());
						row.createCell(19).setCellValue(merch.getSale_price());
						row.createCell(20).setCellValue(merch.getSale_price() * sku.getNum());
						row.createCell(21).setCellValue(merch.getSale_after_rebate() * sku.getNum());
						row.createCell(22).setCellValue(merch.getPurch_price());
						row.createCell(23).setCellValue(merch.getPurch_price() * sku.getNum());
						row.createCell(24).setCellValue(merch.getPurch_after_rebate() * sku.getNum());
					}
				}
			}

		}
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			workBook.write(fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			logger.error("拼接EXCEL错误" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param order
	 * @return
	 */
	private String getStartDate(OrderExportExcelEntity order) {
		return (order.getMerch_type() == ProductTypeGlobal.NATIVE_PRODUCT || order.getStart_time() == null) ? "-"
				: new SimpleDateFormat("yyyy-MM-dd").format(order.getStart_time());
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
