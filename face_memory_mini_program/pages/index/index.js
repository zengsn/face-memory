// pages/welcome/welcome.js
var app = getApp();

Page({

    /**
     * 页面的初始数据
     */
    data: {
        imgs: [
            // "../../image/welcome_1.png",
            "../../image/welcome.png",
        ],
        img: "../../image/welcome.png",
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        var isFirstLaunch = wx.getStorageSync('isFirstLaunch');
        // isFirstLaunch = false;
        console.log(isFirstLaunch)
        if (isFirstLaunch) {
            wx.switchTab({
                url: '../history/history'
            });
        }
    },

    // 获取用户信息
    onGotUserInfo: function(e) {
        wx.setStorageSync('isFirstLaunch', true);
        console.log("e.detail.userInfo: " + e.detail.userInfo);
        app.globalData.userInfo = e.detail.userInfo

        var userinfo = e.detail.userInfo;
        var url = app.globalData.url + '/userInfo/save'
        // 登录
        wx.login({
            success: res => {
                if (res.code) {
                    // 发送 res.code 到后台换取 openId, sessionKey, unionId
                    console.log('code: ' + res.code)
                    console.log('nickName:' + userinfo.nickName,
                        'gender:' + userinfo.nickName,
                        'country:' + userinfo.country,
                        'province:'+ userinfo.province,
                        'city:'+ userinfo.city);
                    
                    // 获取 openId
                    var that = this;
                    wx.request({
                        url: url,
                        data: {
                            'code': res.code,
                            'nickName': userinfo.nickName,
                            'gender': userinfo.gender,
                            'country': userinfo.country,
                            'province':userinfo.province,
                            'city':userinfo.city
                        },
                        header: {
                            'content-type': 'json'
                        },
                        success: function (res) {
                            if(res.data.result === "succeed") {
                                console.log("保存用户授权的信息成功！")
                            } else {
                                console.log("userinfo 保存失败！")
                            }
                        }
                    })
                }
            }
        });
       
        // 跳转进入主页
        wx.switchTab({
            url: '../history/history'
        })
    },


    swiperchange: function(event) {
        if (event.detail.source == "touch") {
            //防止swiper控件卡死  
            if (this.data.current == 0 && this.data.preIndex > 1) { // 卡死时，重置current为正确索引
                this.setData({
                    current: this.data.preIndex
                });
            } else { //正常轮转时，记录正确页码索引  
                this.setData({
                    preIndex: this.data.current
                });
            }
        }
    }
})