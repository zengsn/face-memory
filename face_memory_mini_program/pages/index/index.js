//index.js
//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    prompt: '数据加载中...',

    abbr_url: [],
    pictures: [],
    faceinfo: [],
  },

  onLoad: function() {
    var app = getApp();
    wx.showLoading({
      title: "获取账户信息中...",
      // mask: true
    })
    setTimeout(function () {
      wx.hideLoading();
      if (app.globalData.openid == null)
        wx.showModal({
          title: '提示',
          content: '若长时间未响应,请反馈给我们',
          success: function (res) {
            if (res.confirm) {
              // app.globalData.openid = "";
            } else if (res.cancel) {
              // app.globalData.openid = "";
            }
          }
        })
    }, 5000) //循环间隔 单位ms


    var that = this;

    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        console.log('code: ' + res.code)
        // 获取 openId
        var url = app.globalData.url + '/wx/getOpenId'
        var that = this;
        var f = false;
        wx.request({
          url: url,
          data: {
            'code': res.code
          },
          header: {
            'content-type': 'json'
          },
          success: function (res) {
            // 保存openid到全局变量 openid
            app.globalData.openid = res.data.openid
            console.log('openid:' + app.globalData.openid);
            console.log("sessionID:" + res.data.sessionID);
            wx.setStorageSync("sessionID", res.data.sessionID);
            f = true;
            if(f){
              console.log('开始正是请求')
              that.getPic();
              // 请求检查是否是第一次使用小程序拍照
              that.checkFirst();
              that.refresh();
            }
          }
        })
      }
    })

 
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }

  },



  onPullDownRefresh() {
    // 上拉刷新
    if (!this.loading) {
      this.getPic();
      // 处理完成后，终止下拉刷新
      wx.stopPullDownRefresh()
    }
  },

  getPic: function() {
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

  getUserInfo: function(e) {
    // console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },

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
  deleteImage: function (event) {
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
                  success: function (res) { },
                  fail: function (res) { },
                  complete: function (res) { },
                })
              }
            }
          })
        }
      }
    })

  },


  // 获取 sessionID 
  getSession: function () {
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后台换取 openId, sessionKey, unionId
        console.log('code: ' + res.code)
        // 获取 openId
        var url = app.globalData.url + '/wx/getOpenId'
        var that = this;
        wx.request({
          url: url,
          data: {
            'code': res.code
          },
          header: {
            'content-type': 'json'
          },
          success: function (res) {
            // 保存openid到全局变量 openid
            app.globalData.openid = res.data.openid
            console.log('openid:' + app.globalData.openid);
            console.log("sessionID:" + res.data.sessionID);
            wx.setStorageSync("sessionID", res.data.sessionID);
          }
        })
      }
    })
  },
  // 获取sessionID :end

  // 定时任务, 每个二十分钟执行一次
  refresh: function () {
    var that = this;
    setInterval(that.getSession, 3 * 60 * 1000);
  },

  // 请求检查是否是第一次使用小程序拍照:start
  checkFirst: function (){
    wx.request({
      url: app.globalData.url + '/faceInfo/checkFirst',
      data: {
        'openid': app.globalData.openid
      },
      header: {
        'content-type': 'json',
        'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
      },
      success: function (res) {
        // 保存openid到全局变量 openid
        app.globalData.first = res.data.result;
        console.log("是否是第一次使用小程序：" + res.data.result);
      }
    })
  }
  // 请求检查是否是第一次使用小程序拍照:end

  //事件处理函数
  // bindViewTap: function() {
  //   wx.navigateTo({
  //     url: '../logs/logs'
  //   })
  // },

})