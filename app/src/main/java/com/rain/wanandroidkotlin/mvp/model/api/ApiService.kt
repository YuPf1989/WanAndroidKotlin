package com.rain.wanandroidkotlin.mvp.model.api

import com.rain.wanandroidkotlin.mvp.model.BaseResp
import com.rain.wanandroidkotlin.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*

/**
 * Author:rain
 * Date:2018/11/16 16:37
 * Description:
 */
interface ApiService {
    /**
     * 主页
     */
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") num: Int): Observable<HomePageArticleBean>

    /**
     * 登录
     */
    @POST("user/login")
    @FormUrlEncoded
    fun login(@Field("username") username: String, @Field("password") password: String): Observable<UserInfo>

    /**
     * 注册
     */
    @POST("user/register")
    @FormUrlEncoded
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("repassword") repassword: String): Observable<BaseResp<UserInfo>>

    /**
     * banner
     */
    @GET("banner/json")
    fun getBannerList(): Observable<List<BenarBean>>

    /**
     * 收藏文章
     * @param id
     */
    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<BaseResp<Any>>

    /**
     * 取消收藏文章
     * @param id id
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun cancelCollectArticle(@Path("id") id: Int): Observable<BaseResp<Any>>

    /**
     * 体系数据
     */
    @GET("tree/json")
    fun getSystemList(): Observable<List<SystemBean>>

    /**
     * 单个知识体系列表
     */
    @GET("article/list/{page}/json")
    fun getSystemDetailList(@Path("page") page: Int, @Query("cid") id: Int): Observable<SystemDetailListBean>

    /**
     * 获取项目 列表
     */
    @GET("project/tree/json")
    fun getDemoTitleList(): Observable<List<DemoTitleBean>>

    /**
     * 获取 项目详细信息列表数据
     */
    @GET("project/list/{page}/json")
    fun getDemoDetailList(@Path("page") page: Int, @Query("cid") id: Int): Observable<DemoDetailListBean>

    /**
     * 获取 我的收藏列表
     */
    @GET("lg/collect/list/{page}/json")
    fun getCollectionList(@Path("page") page: Int): Observable<BaseResp<CollectBean>>

    /**
     * 获取 热门词
     */
    @GET("/friend/json")
    fun getHotList(): Observable<BaseResp<List<HotBean>>>

    /**
     * 获取 搜索热词
     * @return
     */
    @GET("/hotkey/json")
    fun getHitKeyBean(): Observable<BaseResp<List<HotKeyBean>>>


    /**
     *
     * 查询搜索结果
     * @param page
     * @param key
     * @return
     */
    @POST("/article/query/{page}/json")
    fun getSearechResult(@Path("page") page: Int, @Query("k") key: String): Observable<BaseResp<HomePageArticleBean>>

    /**
     * 获取 微信公众号列表
     * @return
     */
    @GET("/wxarticle/chapters/json")
    fun getWXList(): Observable<List<WxListBean>>

    /**
     * 获取 微信公众号详细信息列表数据
     */
    @GET("wxarticle/list/{id}/{page}/json")
    fun getWXDetailList(@Path("page") page: Int, @Path("id") id: Int): Observable<WxPublicListBean>
}