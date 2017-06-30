package com.pzj.apidoc;

/**
 * apiDoc通用定义
 *
 * Created by fanggang on 2016/11/8.
 */
public class CommonDefine {

    /**
     * @apiDefine Result_Success Result 查询成功结果对象
     *
     * @apiSuccess (返回-成功) {int} errorCode 错误码
     * @apiSuccess (返回-成功) {String} errorMsg 错误描述信息
     * @apiSuccess (返回-成功) {Object} data 返回数据
     */

    /**
     * @apiDefine Result_Success_Boolean Result 查询成功结果对象
     *
     * @apiSuccess (返回-成功) {int} errorCode 错误码
     * @apiSuccess (返回-成功) {String} errorMsg 错误描述信息
     * @apiSuccess (返回-成功) {Boolean} data 成功返回true；失败返回false。
     */

    /**
     * @apiDefine QueryResult_Success QueryResult 分页查询成功结果对象
     *
     * @apiSuccess (返回-成功) {int} errorCode 错误码
     * @apiSuccess (返回-成功) {String} errorMsg 错误描述信息
     * @apiSuccess (返回-成功) {QueryResult} data 返回数据
     * @apiSuccess (返回-成功) {int} data.total 数据总数
     * @apiSuccess (返回-成功) {int} data.current_page 当前页
     * @apiSuccess (返回-成功) {int} data.total_page 总页数
     * @apiSuccess (返回-成功) {int} data.page_size 每页最大记录数
     * @apiSuccess (返回-成功) {List[Object]} data.records 结果数据对象列表
     */

    /**
     * @apiDefine ServiceContext ServiceContext 调用上下文对象
     *
     * @apiParam (ServiceContext) {String} reference 调用方名称. 标识服务调用方名称, 由调用方自行维护.
     * @apiParam (ServiceContext) {long} [timestamp=System.currentTimeMillis()] timestamp 调用方的系统时间
     * @apiParam (ServiceContext) {String} logId 本次调用日志ID，用于日志跟踪统计.
     * @apiParam (ServiceContext) {Environment} env 调用方环境信息
     * @apiParam (ServiceContext) {String} env.clientIp 调用方IP地址
     * @apiParam (ServiceContext) {String} env.clientMac 调用方MAC地址
     * @apiParam (ServiceContext) {String} env.pid 调用方端口
     */

}
