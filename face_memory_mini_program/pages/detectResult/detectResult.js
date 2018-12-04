// pages/uncheck/uncheck.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    prompt: '数据加载中...',

    abbr_url: [],
    pictures: [],
    faceinfo: [],
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var app = getApp();
    var that = this;
    that.getPic();
  },

  
  getPic: function() {
    var that = this;
    // 请求获取历史照片的路径和识别结果信息
    wx.request({
      url: app.globalData.url + '/faceInfo/getPastPhoto',
      data: {
        openid: app.globalData.openid,
        status: 0
      },
      header: {
        'content-type': 'json',
        'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
      },
      success: function(res) {
        console.log(app.globalData.url + '/faceInfo/getPastPhoto');
        that.setData({
          abbr_url: res.data.abbr_urls,
          pictures: res.data.urls,
          faceinfo: res.data.faceinfo
        })
        console.log(that.data.abbr_url.length)
        if (res.data.abbr_urls.length == 0) {
          that.setData({
            prompt: '您还没有拍过照片!'
          })
        }
      }
    })
  },


// 不可用，暂找不到原因
  // onPullDownRefresh: function () {
  //   console.log('下拉刷新')
  //   // 上拉刷新
  //   if (!this.loading) {
  //     this.getPic();
  //     // 处理完成后，终止下拉刷新
  //     wx.stopPullDownRefresh()
  //   }
  // },

  /**
   * 预览照片监听
   */
  previewImage: function(e) {
    // 小图预览，进入全屏模式
    var that = this,
      index = e.currentTarget.dataset.index,
      pictures = this.data.pictures;
    wx.previewImage({
      current: pictures[index],
      urls: pictures
    })
  },

  check:function(data){
    var that = this;
    // console.log("index: " + data.currentTarget.dataset.index);
    var index = parseInt(data.currentTarget.dataset.index);
    console.log("that.faceinfo[index].id" + that.data.faceinfo[0].id);
    wx.request({
      url: app.globalData.url + '/faceInfo/check',
      data: {
        id: that.data.faceinfo[index].id
      },
      header: {
        'content-type': 'json',
        'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
      },
      success: function (res) {
        if (res.data.result == 'succeed') {
          console.log("id为 "+ that.data.faceinfo[index].id + " 的识别结果记录已更新为已查看状态")
        }
      }
    })
    wx.navigateTo({
      url: "../detail/detail?id=" + that.data.faceinfo[index].id + "&faceValue=" + that.data.faceinfo[index].faceValue + "&age=" + that.data.faceinfo[index].age + "&src=" + that.data.pictures[index], 
      success: function(res) {},
      fail: function(res) {},
      complete: function(res) {},
    })
  },

  deletePic: function (event) {
    var index = parseInt(event.currentTarget.dataset.index);
    var that = this;
    wx.showModal({
      title: '确认删除吗',
      // content: fin.res,
      confirmText: "是",
      cancelText: "否",
      success: function (res) {
        console.log(res);
        if (res.confirm) {
          // 请求删除对应照片的文件和识别结果
          wx.request({
            url: app.globalData.url + '/faceInfo/deletePic',
            data: {
              id: that.data.faceinfo[index].id
            },
            header: {
              'content-type': 'json',
              'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
            },
            success: function (res) {
              console.log(app.globalData.url + '/faceInfo/deletePic');
              if (res.data.result == 'succeed') {
                that.getPic();
                wx.showToast({
                  title: '删除成功',
                  icon: '',
                  image: '',
                  duration: 1000,
                  mask: true,
                  success: function(res) {},
                  fail: function(res) {},
                  complete: function(res) {},
                })
              }
            }
          })
        }
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