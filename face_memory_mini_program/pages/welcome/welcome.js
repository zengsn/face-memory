// pages/welcome/welcome.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    imgs: [
      "../../image/welcome_1.png",
      "../../image/welcome_1.png",
      "../../image/welcome_1.png",
      "../../image/welcome_1.png",
    ]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var isFirstLaunch = wx.getStorageSync('isFirstLaunch');
    // isFirstLaunch = false;
    console.log(isFirstLaunch)
    if (!isFirstLaunch) {
      wx.setStorageSync('isFirstLaunch', true);
    } else {
      wx.switchTab({ url: '../index/index' });
    }
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },

  start: function () {
    wx.switchTab({ url: '../index/index' })
  },

  swiperchange: function (event) {
    if (event.detail.source == "touch") {
      //防止swiper控件卡死  
      if (this.data.current == 0 && this.data.preIndex > 1) {// 卡死时，重置current为正确索引
        this.setData({ current: this.data.preIndex });
      }
      else {//正常轮转时，记录正确页码索引  
        this.setData({ preIndex: this.data.current });
      }
    }
  }
})