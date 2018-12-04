// pages/announcement/announcement.js
const app = getApp();
var util = require('../../utils/util.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    announcements: []
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    wx.request({
      url: app.globalData.url + '/announcement/listAll',
      data: {},
      header: {
        'content-type': 'json',
        'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
      },
      success: function (res) {
        that.setData({
          announcements: res.data
        });
        var announces = that.data.announcements;
        for (var i = 0; i < announces.length; i++){
          announces[i].createtime = util.formatTime(new Date(announces[i].createtime));
        }
        that.setData({
          announcements:announces
        })
        console.log(res.data);
      }
    })

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

  }
})