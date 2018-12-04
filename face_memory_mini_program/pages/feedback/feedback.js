// pages/feedback/feedback.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    email: '',
    content: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {

  },

  inputEmail: function(e) {
    console.log(e.detail.value);
    this.data.email = e.detail.value;
  },
  inputContent: function(e) {
  console.log(e.detail.value);      
    this.data.content = e.detail.value;
  },
  commitTips: function() {
    var commenturl = app.globalData.url + "/feedback/save";
    var that = this;
    if (that.data.email == '' || that.data.content == '') {
      wx.showToast({
        title: '请输入要反馈的内容',
        icon:'none',
        duration: 1000
      })
    } else {
      wx.showToast({
        title: '反馈成功！',
        duration: 2000
      });
      wx.request({
        url: commenturl,
        data: {
          email: that.data.email,
          content: that.data.content,
          wxid: app.globalData.openid
        },
        header: {
          'content-type': 'json',
          'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
        },
        success: function(res) {
          if (res.data.result == 'succeed') {
            console.log("反馈成功！");
            
          }
        }
      })
    }

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