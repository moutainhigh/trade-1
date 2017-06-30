package com.pzj.trade.book.model;

import java.io.Serializable;

public class BookEditModel extends BookInModel implements Serializable {

	/**
	* @apiDefine BookEditModel  BookEditModel 预订单编辑入参
	* 
	* @apiParam (BookEditModel) {String} bookId 预定单id
	* @apiParam (BookEditModel) {int} bookType  单据类型  必填
	* @apiParam (BookEditModel) {long} resellerId  预定/免票特价票分销商Id  必填 
	* @apiParam (BookEditModel) {long} operatorId 操作人id  必填
	* @apiParam (BookEditModel) {long} supplierId saas供应商id  必填
	* @apiParam (BookEditModel) {Date} travelDate 游玩时间  必填
	* @apiParam (BookEditModel) {long} srcBookId 预约单id：如是前置订单信息，需传入它对应的预约单id；如是预约单，传入0
	* @apiParam (BookEditModel) {String} travel  旅行社
	* @apiParam (BookEditModel) {String} travelDepartment  旅行社部门
	* @apiParam (BookEditModel) {String} guideId 导游
	* @apiParam (BookEditModel) {List} contactees 联系人信息  ContacteeModel 
	* @apiParam (BookEditModel) {List} filledModelList 填写项  FilledModel 
	* @apiParam (BookEditModel) {long} spuId 产品spuId 必填
	* @apiParam (BookEditModel) {List} products 产品信息  BookProductModel 必填
	* @apiParam (BookEditModel) {List} tourists 游客信息 TouristModel
	* @apiParam (BookEditModel) {String} remark 备注
	* @apiParam (BookEditModel) {String} touristSourceCountry 客源地国家
	* @apiParam (BookEditModel) {String} touristSourceProvince 客源地省
	* @apiParam (BookEditModel) {String} touristSourceCity 客源地城市
	* @apiParam (BookEditModel) {String} deliveryCode 提货码
	* 
	*/

	/**  */
	private static final long serialVersionUID = 3954319640747399838L;

	/**预订单id*/
	private String bookId;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

}
