package com.pzj.voucher.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.pzj.framework.context.Result;
import com.pzj.voucher.common.ExecuteResult;
import com.pzj.voucher.entity.ExtendVoucher;
import com.pzj.voucher.entity.VoucherEntity;
import com.pzj.voucher.entity.VoucherResponseModel;
import com.pzj.voucher.vo.InitVoucherVo;

public interface VoucherService {

	@Deprecated
	Result<VoucherEntity> createVoucher(InitVoucherVo initVoucherVo);

	/**
	 * @api {dubbo} com.pzj.voucher.service.VoucherService#queryVoucherInfo 查询有效voucher
	 * @apiGroup 核销凭据voucher
	 * @apiName 查询有效voucher
	 * @apiDescription <p>根据供应商ID和凭证介质(检票根据凭证码(二维码、身份证号)查询当天的有效Voucher</p>
	 * @apiVersion 1.1.0
	 * @apiParam {Long} supplierId 供应商ID
	 * @apiParam {String} voucherContent 凭证介质(身份证号、二维码)
	 *
	 * @apiSuccess (返回-成功) {List[VoucherEntity]} VoucherEntity voucher对象
	 * @apiSuccessExample [{ExecuteResult}] [ExecuteResult]
	*{
	  "stateCode": 10000,
	  "data": [
	    {
	      "voucherId": 1464864106249,
	      "voucherContent": "18011520296",
	      "voucherContentType": 0,
	      "contactMobile": "18011520296",
	      "startTime": "Sep 6, 2016 9:47:07 PM",
	      "expireTime": "Sep 6, 2017 9:47:07 PM",
	      "voucherState": 0,
	      "voucherCategory": 5000,
	      "supplierId": 2216619736763722,
	      "transactionId": "MF256233721",
	      "createTime": "Sep 6, 2016 9:47:07 PM",
	      "productNum": 0,
	      "voucherConfirmList": [],
	      "extendVoucherList": [
	        {
	          "id": 4290,
	          "voucherId": 1464864106249,
	          "supplierId": 2216619736763722,
	          "voucherAttr": "productInfo",
	          "voucherAttrContent": "2216619741564525,海边马场环球旅拍,2,null,0"
	        },
	        {
	          "id": 4291,
	          "voucherId": 1464864106249,
	          "supplierId": 2216619736763722,
	          "voucherAttr": "stockInfo",
	          "voucherAttrContent": "{\"productNum\":2,\"stockId\":3032,\"productId\":2216619741564525}"
	        }
	      ],
	      "specialityProductVoucherVo": {},
	      "isOverdue": 0
	    }
	    ]
	   }
	 * @apiUse ExecuteResult
	 * @apiUse VoucherEntity
	 * @apiErrorExample [{error}] [error]
	 *  {
	    "stateCode": 100001,
	    "message": "没有发现凭证"
	    }
	 */
	public ExecuteResult<List<VoucherEntity>> queryVoucherInfo(Long supplierId, String voucherContent);

	/**
	 * 根据对象参数查询核销数据
	 * @param baseVoucher 参数对象
	 * @return 返回voucher通用接口
	 */
	public ExecuteResult<List<VoucherEntity>> queryVoucherByParam(VoucherEntity baseVoucher);

	/***
	 * 修改核销凭据信息（核销信息上游客的信息）【如果为空不做修改】
	 * @param voucherId  voucherId
	 * @param name  名称
	 * @param mobile    手机号
	 * @param cardId    身份证
	 * @return 返回voucher通用接口
	 */
	@Deprecated
	public ExecuteResult<VoucherEntity> updateVoucher(Long voucherId, String name, String mobile, String cardId);

	//注释者: YRJ
	//	/**
	//	 * 获取一个voucher的产品信息
	//	 *
	//	 * @param voucherId 核销凭据ID
	//	 * @return   返回voucher通用接口
	//	 */
	public ExecuteResult<List<ExtendVoucher>> queryVoucherProductInfo(Long voucherId);

	/**
	 * 【批量】检查当前身份证是否购买了指定的一证一票的产品
	 * @param productId 产品ID
	 * @param voucherContent 身份证号
	 * @param date  日期
	 * @return 返回voucher通用接口
	 */
	@Deprecated
	public ExecuteResult<VoucherEntity> checkOneCardVoucherBatch(Long productId, Long supplierId, List<String> voucherContentList, Date date);

	/**
	 * 检测身份证是否可购买-废弃.
	 */
	@Deprecated
	Result<ArrayList<String>> checkIdCardBuyable(long prodId, long supplierId, List<String> idCards, Date date);

	/**
	 * 根据voucherId查询voucher基本信息：身份证号、游客姓名、凭证类型等
	 *
	 * @param voucherId
	 * @return 通用返回对象
	 */
	public ExecuteResult<VoucherResponseModel> queryVoucherBasicById(Long voucherId);

	/**
	 * @apiDefine VoucherProductInfoVO VoucherProductInfoVO
	 * 创建核销时产品信息
	*  @apiParam (VoucherProductInfoVO) {Long} productId  产品ID
	*  @apiParam (VoucherProductInfoVO) {String} productName    产品名称
	*  @apiParam (VoucherProductInfoVO) {Integer} productNum 产品数量
	*  @apiParam (VoucherProductInfoVO) {Date} startTime  产品的开始时间
	*  @apiParam (VoucherProductInfoVO) {Date} endTime    产品的结束时间
	*  @apiParam (VoucherProductInfoVO) {Map} [childProductMap]    此字段主要是针对门票和演艺使用,主要是存储检票点信息:包含检票点ID，和最大检票次数，最大检票次数为-99为无限次进入
	*  @apiParam (VoucherProductInfoVO) {String} [screening]  场次
	*  @apiParam (VoucherProductInfoVO) {String} [region]     区域
	*  @apiParam (VoucherProductInfoVO) {String} [seatNumbers]    座位号
	*  @apiParam (VoucherProductInfoVO) {Long} [stockId]  库存ID
	*  @apiParam (VoucherProductInfoVO) {Long} productGroupId   产品组Id
	*/

	/**
	 * @apiDefine ExecuteResult ExecuteResult
	*  @apiParam (ExecuteResult) {Integer} stateCode    执行结果状态
	*  @apiParam (ExecuteResult) {String} message   错误信息
	*  @apiParam (ExecuteResult) {T} data   执行成功返回值
	*  @apiParam (ExecuteResult) {Exception} exception  异常信息
	*/
	/**
	 * @apiDefine VoucherEntity VoucherEntity
	*  @apiParam (VoucherEntity) {Long} voucherId 凭证ID
	*  @apiParam (VoucherEntity) {String} voucherContent    身份证号或二维码
	*  @apiParam (VoucherEntity) {List[String]} voucherContentList    身份证号或二维码集合
	*  @apiParam (VoucherEntity) {String} voucherContentType    凭证介质类型：（ 1 身份证号   2 二维码或条码及其辅助码  3 手机号)
	*  @apiParam (VoucherEntity) {String} voucherContentImageUrl    凭证图片地址
	*  @apiParam (VoucherEntity) {String} contactMobile     凭证人手机号
	*  @apiParam (VoucherEntity) {String} contactName   凭证人姓名
	*  @apiParam (VoucherEntity) {String} checkStartTime    检票时间段：开始时间
	*  @apiParam (VoucherEntity) {String} checkEndTime  检票时间结束
	*  @apiParam (VoucherEntity) {Date} startTime     凭证有效开始时间
	*  @apiParam (VoucherEntity) {Date} expireTime    凭证有效过期时间
	*  @apiParam (VoucherEntity) {Date} showStartTime   演艺开始时间
	*  @apiParam (VoucherEntity) {Date} showEndTime     演艺结束时间
	*  @apiParam (VoucherEntity) {Integer} voucherState  凭证状态(-1:不可用；0：可以使用；1核销完毕；2：退单)
	*  @apiParam (VoucherEntity) {Integer} voucherCategory   产品线
	*  @apiParam (VoucherEntity) {String} extendVoucherContent  拓展属性（身份证、指纹码信息
	*  @apiParam (VoucherEntity) {Integer} extendVoucherContentType 拓展类型
	*  @apiParam (VoucherEntity) {String} extendVoucherImageUrl 拓展存取图片
	*  @apiParam (VoucherEntity) {Long} supplierId   供应商id
	*  @apiParam (VoucherEntity) {String} transactionId    交易号
	*  @apiParam (VoucherEntity) {Date} createTime   创建时间
	*  @apiParam (VoucherEntity) {Date} upateTime    更新时间
	*  @apiParam (VoucherEntity) {Long} productId    产品ID
	*  @apiParam (VoucherEntity) {String} productName 产品名称
	*  @apiParam (VoucherEntity) {Integer} productNum  产品数量
	*  @apiParam (VoucherEntity) {List[VoucherConfirm]} voucherConfirmList  记录核销的产品和核销数量
	*  @apiParam (VoucherEntity) {String} accurateStartTime   精确开始日期（仅限供订查询有效期开始日期的数据使用
	*  @apiParam (VoucherEntity) {String} accurateEndTime 精确关闭日期（仅限供订查询有效期开始日期的数据使用
	*  @apiParam (VoucherEntity) {List[Long]} voucherIdList   根据voucherId批量查询使用
	*  @apiParam (VoucherEntity) {List[ExtendVoucher]} extendVoucherList   凭证拓展熟悉集合
	*  @apiParam (VoucherEntity) {Date} createStartTime   创建开始时间
	*  @apiParam (VoucherEntity) {Date} createEndTime 创建结束时间
	*  @apiParam (VoucherEntity) {ScenicPruductVoucherVO} scenicPruductVoucherVO    景区产品信息
	*  @apiParam (VoucherEntity) {List[TheaterProductVoucherVO]} theaterProductVoucherVOList   针对非一证一票的voucher存储是一个voucher对应多个产品信息，所以要存储为多个
	*  @apiParam (VoucherEntity) {SpecialityProductVoucherVo} specialityProductVoucherVo    特产产品信息
	*  @apiParam (VoucherEntity) {String}   voucherType 核销类型1: 自动 2: 手动; 3: 固定时间
	*  @apiParam (VoucherEntity) {String}   expVoucherTime  预计核销时间
	*  @apiParam (VoucherEntity) {String}   voucherTime 实际核销时间(首次核销时间)
	*  @apiParam (VoucherEntity) {String}   isOverdue   是否逾期核销(暂时针对门票设置， 默认0：不是逾期，1是逾期 )
	*/
	/**
	 * @apiDefine VoucherConfirm
	*  @apiParam (VoucherConfirm) {Long} voucherId   凭证ID
	*  @apiParam (VoucherConfirm) {List[Long]} voucherIdVoucher 凭证ID集合，查询使用
	*  @apiParam (VoucherConfirm) {Long} childProductId 子产品ID(检票点ID)
	*  @apiParam (VoucherConfirm) {Long} productId 产品ID
	*   @apiParam (VoucherConfirm) {String} supplierId 供应商ID
	*   @apiParam (VoucherConfirm) {Integer} usedTimes 使用次数
	*   @apiParam (VoucherConfirm) {Integer} maxUseTimes 最多使用次数
	*/
	/**
	 * @apiDefine ExtendVoucher
	*  @apiParam (ExtendVoucher) {String} id 主键
	*  @apiParam (ExtendVoucher) {String} voucherId 核销凭证ID
	*  @apiParam (ExtendVoucher) {String} supplierId 供应商ID
	*  @apiParam (ExtendVoucher) {String} voucherAttr 凭证属性名称
	*  @apiParam (ExtendVoucher) {String} voucherAttrContent    凭证属性值
	*/
	/**
	 * @apiDefine ScenicPruductVoucherVO
	*  @apiParam (ScenicPruductVoucherVO) {Integer} ticketVarie 散票 0 ，团票  1
	*  @apiParam (ScenicPruductVoucherVO) {String} scenic 景区名称
	*  @apiParam (ScenicPruductVoucherVO) {String} scenicId 景区ID
	*  @apiParam (ScenicPruductVoucherVO) {Date} startTime 游玩开始日期
	*  @apiParam (ScenicPruductVoucherVO) {Date} endTime 游玩结束日期
	*
	*/
	/**
	 * @apiDefine SpecialityProductVoucherVo
	*  @apiParam (SpecialityProductVoucherVo) {String} address 收货人信息
	*/

}
