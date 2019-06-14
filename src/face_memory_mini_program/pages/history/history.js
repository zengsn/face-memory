var wxCharts = require('../../utils/wxcharts.js');
var ringChart = null;
//获取应用实例
const app = getApp()
var f = true;

Page({
    data: {
        prompt: '数据加载中...',

        abbr_url: [],
        pictures: [],
        faceinfo: [],
        index: 1
    },

    onLoad: function() {
        wx.showLoading({
            title: "登录中...",
            // mask: true
        });

        var that = this;
        // 登录
        wx.login({
            success: res => {
                if (res.code) {
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
                        success: function(res) {
                            wx.hideLoading();
                            wx.setStorageSync("role", res.data.role);
                            wx.setStorageSync("token", res.data.token);
                            if (f) {
                                f = false;
                                console.log('开始获取数据请求')
                                that.getPic(1);
                            }
                        }
                    })
                } else {
                    wx.hideLoading();
                    wx.showModal({
                        title: '提示',
                        content: '登陆失败',
                        confirmText: '重试',
                        success: function(res) {
                            if (res.confirm) {
                                that.getSession();
                            } else if (res.cancel) {
                                // app.globalData.openid = "";
                            }
                        }
                    });
                }
            }
        });
    },

    /** 
     * 下拉刷新
     */
    onPullDownRefresh() {
        this.setData({
            index: 1
        });
        if (!this.loading) {
            this.getPic(1);
            // 处理完成后，终止下拉刷新
            wx.stopPullDownRefresh();
        }
    },
    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function() {
        let index = this.data.index;
        this.setData({
            index: (index + 1)
        });
        this.getPic(this.data.index);
    },

    toCamera: function() {
        wx.navigateTo({
            url: '../came/came',
            success: function(res) {},
            fail: function(res) {},
            complete: function(res) {},
        })
    },

    getPic: function(index) {
        var that = this;
        // 请求获取历史照片的路径和识别结果信息
        wx.request({
            url: app.globalData.url + '/faceInfo/getPastPhoto',
            data: {
                pageNum: index
            },
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function(res) {
                console.log("获取历史图片, index: " + index);
                if (res.data.result === "succeed") {
                    if (index === 1) {
                        that.setData({
                            abbr_url: res.data.abbr_urls,
                            pictures: res.data.urls,
                            faceinfo: res.data.faceinfo
                        });
                        console.log("数据获取完成")
                        setTimeout(() => {
                            console.log("开始渲染图表了")
                            // console.log(that.data.faceinfo);
                            that.randerChart(that.data.faceinfo);
                        }, 100)
                    } else {
                        var records = that.data.faceinfo.length;
                        that.setData({
                            abbr_url: that.data.abbr_url.concat(res.data.abbr_urls),
                            pictures: that.data.pictures.concat(res.data.urls),
                            faceinfo: that.data.faceinfo.concat(res.data.faceinfo)
                        });
                        var new_records = that.data.faceinfo.length;
                        setTimeout(() => {
                            for (var i = records; i < new_records; i++) {
                                // 颜值
                                var fv = that.data.faceinfo[i].faceValue;
                                that.createRingChart(i, fv);
                            }
                        }, 100)
                    }
                } else {
                    if (index === 1) {
                        that.setData({
                            abbr_url: "",
                            pictures: "",
                            faceinfo: ""
                        });
                    }
                    if (that.data.pictures.length === 0) {
                        that.setData({
                            prompt: '您还没有拍过照片!'
                        })
                    } else {
                        wx.showToast({
                            title: '没有更多记录了',
                            icon: 'none'
                        })
                        let index = that.data.index;
                        that.setData({
                            index: (index - 1)
                        });
                    }
                }
            }
        });
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

    // 长按删除图片
    deleteImage: function(event) {
        var index = parseInt(event.currentTarget.dataset.index);
        var that = this;
        wx.showModal({
            title: '确认删除吗',
            confirmText: "是",
            cancelText: "否",
            success: function(res) {
                if (res.confirm) {
                    // 请求删除对应照片的文件和识别结果
                    wx.request({
                        url: app.globalData.url + '/faceInfo/deletePic',
                        data: {
                            id: that.data.faceinfo[index].id
                        },
                        header: {
                            'content-type': 'json',
                            'token': wx.getStorageSync("token"),
                            'role': wx.getStorageSync("role")
                        },
                        success: function(res) {
                            console.log(app.globalData.url + '/faceInfo/deletePic');
                            if (res.data.result == 'succeed') {
                                that.getPic(1);
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


    // 获取 sessionID :start
    getSession: function() {
        // 登录
        wx.login({
            success: res => {
                if (res.code) {
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
                        success: function(res) {
                            wx.setStorageSync("role", res.data.role);
                            wx.setStorageSync("token", res.data.token);
                            if (f) {
                                f = false;
                                console.log('开始获取数据请求')
                                that.getPic(1);
                            }
                        }
                    })
                }
            }
        });
    },
    // 获取sessionID :end

    touchHandler: function(e) {
        console.log(ringChart.getCurrentDataIndex(e));
    },

    randerChart: function(faceinfo) {
        // 每次 setData 都触发
        var length = this.data.faceinfo.length;
        console.log("渲染页面完成", length)
        for (var i = 0; i < length; i++) {
            // 颜值
            var fv = this.data.faceinfo[i].faceValue;
            this.createRingChart(i, fv);
        }
    },
    // onReady:function(e){
    // this.createRingChart("");
    // },
    createRingChart: function(index, fv) {
        ringChart = new wxCharts({
            animation: true,
            canvasId: 'ringCanvas' + index,
            type: 'ring',
            extra: {
                ringWidth: 5,
                pie: {
                    offsetAngle: -90
                }
            },
            title: {
                name: fv,
                color: '#1aad19',
                fontSize: 9
            },
            subtitle: {
                name: '颜值',
                color: '#666666',
                fontSize: 12
            },
            series: [{
                name: '颜值',
                data: fv,
                stroke: false,
                color: "#1aad19"
            }, {
                name: '空白',
                data: 100-fv,
                stroke: false,
                color: "white"
            }],
            disablePieStroke: true,
            width: 100,
            height: 100,
            dataLabel: false,
            legend: false,
            background: '#f5f5f5',
            padding: 0
        });
        ringChart.addEventListener('renderComplete', () => {
            console.log('renderComplete');
        });
        setTimeout(() => {
            ringChart.stopAnimation();
        }, 500);
    },

})