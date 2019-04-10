// pages/mine/aboutus.js
var app = getApp();
//请求数据的主域名
var reqUrl = app.globalData.reqUrl;

Page({
    data: {
        author: []
    },
    onLoad: function(options) {
        // 页面初始化 options为页面跳转所带来的参数
        wx.setNavigationBarTitle({
            title: "记脸知己"
        });
    },
    onReady: function() {
        // 页面渲染完成
    },
    onShow: function() {
        // 页面显示

    },
    onHide: function() {
        // 页面隐藏
    },
    onUnload: function() {
        // 页面关闭
    },

    // 跳转到“小留言板”小程序
    openMinimsger: function() {
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
    compareVersion: function(v1, v2) {
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
    }
})