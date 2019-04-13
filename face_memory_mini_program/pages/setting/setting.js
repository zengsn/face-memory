// pages/setting/setting.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
      canIUse: wx.canIUse('button.open-type.getUserInfo'),
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
      wx.setNavigationBarTitle({
          title:"记脸知己"
      })
  },
    // 跳转到“小留言板”小程序
    openMinimsger: function () {
        var me = this;
        wx.getSystemInfo({
            success(sys) { // 兼容旧版本
                // console.dir(sys.SDKVersion)
                if (me.compareVersion(sys.SDKVersion, '1.3.0')) {
                    wx.navigateToMiniProgram({
                        appId: 'wxab55adbdaabf555c',
                        path: 'pages/index/index',
                        // extraData: {
                        //   from: 'minimsger'
                        // },
                        envVersion: 'release',
                        success(res) {
                            console.log('openConference')
                        }
                    })
                } else {
                    // 如果希望用户在最新版本的客户端上体验您的小程序，可以这样子提示
                    wx.showModal({
                        title: '提示',
                        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
                    })
                }
            }
        });
    },
    // 版本检测
    compareVersion: function (v1, v2) {
        v1 = v1.split('.')
        v2 = v2.split('.')
        var len = Math.max(v1.length, v2.length)

        while (v1.length < len) {
            v1.push('0')
        }
        while (v2.length < len) {
            v2.push('0')
        }

        for (var i = 0; i < len; i++) {
            var num1 = parseInt(v1[i])
            var num2 = parseInt(v2[i])

            if (num1 > num2) {
                return 1
            } else if (num1 < num2) {
                return -1
            }
        }
        return 0
    },
  feedback(){
    wx.navigateTo({
      url: '../feedback/feedback',
      success: function(res) {},
      fail: function(res) {},
      complete: function(res) {},
    })
  },
  announce(){
    wx.navigateTo({
      url: '../announcement/announcement',
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