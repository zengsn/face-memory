// pages/features/features.js
Page({

    /**
     * 页面的初始数据
     */
    data: {
        text: '第一次拍摄识别比较慢，请耐心等待! 请勿拍摄不含人脸照片, 系统不识别保存不含人脸的照片',
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        var app = getApp();
        var that = this;

        // 获取公告内容
        wx.request({
            url: app.globalData.url + '/announcement/get',
            data: {
                priority: 0
            },
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function(res) {
                that.setData({
                    // 设置公告内容
                    text: res.data.announcement
                })
                console.log("设置公告内容完成")
            }
        })
    },

    /** 
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady: function() {

    },

    /**
     * 生命周期函数--监听页面显示
     */
    onShow: function() {     // 页面显示
    
    },

    run1: function() {    

    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide: function() {
        // clearInterval(run1);
    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {
        // clearInterval(run1);
    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh: function() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function() {

    },





})