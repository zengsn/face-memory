// pages/historyPhotos/historyPhotos.js
var app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    abbr_url: [],
    pictures: [],
    faceinfo: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    // 请求获取历史照片的路径和识别结果信息
    wx.request({
      url: app.globalData.url + '/faceInfo/getPastPhoto',
      data: {
        openid: app.globalData.openid
      },
      header: {
        'content-type': 'json',
        'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
      },
      success: function(res) {
        that.setData({
          abbr_url: res.data.abbr_urls,
          pictures: res.data.urls,
          faceinfo: res.data.faceinfo
        })
      }
    })
  },
  
  /**
   * 预览照片监听
   */
  previewImage: function (e) {
    // 小图预览，进入全屏模式
    var that = this,
      index = e.currentTarget.dataset.index,
      pictures = this.data.pictures;
    wx.previewImage({
      current: pictures[index],
      urls: pictures
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
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

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

  }
})