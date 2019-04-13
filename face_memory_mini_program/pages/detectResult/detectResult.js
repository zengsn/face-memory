// pages/uncheck/uncheck.js
var app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        prompt: '数据加载中...',

        abbr_url: [],
        pictures: [],
        faceinfo: [],

        waiting: false,
        firstFaceInfo: '',

        delInfo: [],
        delUrls: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function(options) {
        this.getPic();
    },


    getPic: function() {
        var that = this;
        // 请求获取历史照片的路径和识别结果信息
        wx.request({
            url: app.globalData.url + '/faceInfo/listResult',
            data: {},
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function(res) {
                // 只有第一张人脸
                if (res.data.result == "waiting") {
                    that.setData({
                        waiting: true,
                        firstFaceInfo: res.data.faceinfo
                    });
                    wx.showModal({
                        title: '提示',
                        content: '一个微信号只能识别一张人脸, 误拍到非本人人脸的图片请及时删除',
                    })
                } else if (res.data.result == "succeed") {
                    console.log(app.globalData.url + '/faceInfo/listResult');
                    that.setData({
                        waiting: false,
                        // abbr_url: res.data.abbr_urls,
                        pictures: res.data.urls,
                        faceinfo: res.data.faceinfo,
                        delInfo: res.data.delFaceInfo,
                        delUrls: res.data.delUrls
                    })
                    if (res.data.urls.length === 0 && res.data.delUrls.length === 0) {
                        that.setData({
                            prompt: '您还没有拍过照片!'
                        })
                    }

                }
            }
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
    preview: function(e) {
        // 小图预览，进入全屏模式
        var url = this.data.firstFaceInfo.url;
        wx.previewImage({
            urls: [url]
        })
    },
    previewDEL: function(e) {
        // 小图预览，进入全屏模式
        var that = this,
            index = e.currentTarget.dataset.index,
            pictures = this.data.delUrls;
        wx.previewImage({
            current: pictures[index],
            urls: pictures
        })
    },

    check: function(data) {
        var that = this;
        // console.log("index: " + data.currentTarget.dataset.index);
        var index = parseInt(data.currentTarget.dataset.index);
        // console.log("that.faceinfo[index].id" + that.data.faceinfo[0].id);
        wx.navigateTo({
            url: "../detail/detail?id=" + that.data.faceinfo[index].id + "&faceValue=" + that.data.faceinfo[index].faceValue + "&age=" + that.data.faceinfo[index].age + "&src=" + that.data.pictures[index],
            success: function(res) {},
            fail: function(res) {},
            complete: function(res) {},
        })
    },

    deletePic: function(event) {
        var index = parseInt(event.currentTarget.dataset.index);
        var that = this;
        wx.showModal({
            title: '确认删除吗',
            // content: fin.res,
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
                                });
                            }
                        }
                    });
                }
            }
        })
    },

    sure: function(e) {
        var _this = this;
        var id = parseInt(e.currentTarget.dataset.index);
        console.log("sure id: " + id);
        var decideUrl = app.globalData.url + '/faceInfo/decide';
        wx.request({
            url: decideUrl,
            data: {
                id: id
            },
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function() {
                wx.showToast({
                    title: '操作成功',
                    duration: 1000,
                    mask: true,
                });
                _this.getPic();
            }
        })
    },
    deleteFirst: function(e) {
        var _this = this;
        var id = parseInt(e.currentTarget.dataset.index);
        console.log("sure id: " + id);
        wx.request({
            url: app.globalData.url + '/faceInfo/deletePic',
            data: {
                id: id,
                first: 1
            },
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function(res) {
                console.log(app.globalData.url + '/faceInfo/deletePic');
                if (res.data.result == 'succeed') {
                    _this.getPic();
                    wx.showToast({
                        title: '删除成功',
                        duration: 1000,
                        mask: true,
                    });
                }
            }
        });
    },

    deleteDEL: function(event) {
        var index = parseInt(event.currentTarget.dataset.index);
        var that = this;
        // 请求删除对应照片的文件和识别结果
        wx.request({
            url: app.globalData.url + '/faceInfo/deletePic',
            data: {
                id: that.data.delInfo[index].id
            },
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function(res) {
                console.log(app.globalData.url + '/faceInfo/deletePic', 'deleteDEL');
                if (res.data.result == 'succeed') {
                    that.getPic();
                    wx.showToast({
                        title: '删除成功',
                        duration: 1000,
                        mask: true,
                    });
                }
            }
        });
    },
})