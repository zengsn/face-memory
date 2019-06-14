//获取应用实例
let app = getApp();
let isHere = true; // 是否还在当前页面
Page({
    data: {
        device: true,
        tempImagePath: "", // 拍照的临时图片地址
        camera: true,
        ctx: {},
        photoId: 0,
        current: 1,
        step1: '',
        progress: 0,
        masked: false,
        info: ''
    },

    onLoad() {
        this.setData({
            ctx: wx.createCameraContext()
        });
        isHere = true;
    },

    // 切换相机前后置摄像头
    devicePosition() {
        this.setData({
            device: !this.data.device,
        })
        console.log("当前相机摄像头为:", this.data.device ? "前置" : "后置");
    },
    camera() {
        let {
            ctx
        } = this.data;
        // 拍照
        console.log("拍照");
        ctx.takePhoto({
            quality: "normal",
            success: (res) => {
                this.setData({
                    tempImagePath: res.tempImagePath,
                    camera: false,
                });

                wx.showLoading({
                    title: "拍照成功, 请稍后...",
                })
                var _this = this;
                // 1. 服务器请求创建记录, 显示预览图和步骤条
                var createUrl = app.globalData.url + '/faceInfo/create';
                wx.request({
                    url: createUrl,
                    header: {
                        'content-type': 'json',
                        'token': wx.getStorageSync("token"),
                        'role': wx.getStorageSync("role")
                    },
                    success: function(res) {
                        wx.hideLoading();
                        if (res.data.result === 'succeed') {
                            // 显示预览图和步骤条
                            _this.setData({
                                camera: false,
                                photoId: res.data.id
                            });

                            // 2. 上传图片, 更新步骤条“正在上传”
                            _this.uploadPicture(res);
                        } else {
                            // 创建记录失败, 提示用户系统存在待处理记录
                            // 步骤条:上传图片更改 status 为error
                            wx.showModal({
                                content: res.data.resultMsg,
                                showCancel: false,
                                success: function(res) {
                                    if (res.confirm) {
                                        console.log('用户点击确定, 前往识别结果列表页')
                                        // 用户点击确定, 跳转到识别结果页面
                                        wx.navigateTo({
                                            url: "../detectResult/detectResult"
                                        });
                                    }
                                }
                            });
                        }
                    }
                })
            },
            fail: (e) => {
                console.log(e);
            }
        })
    },

    // 关闭模拟的相机界面
    close() {
        console.log("关闭相机");
        this.setData({
            camera: false
        })
    },
    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload: function() {
        isHere = false;
    },


    uploadPicture: function(res) {
        var _this = this;
        // 打开透明层
        _this.setData({
            masked: true
        });
        var uploadUrl = app.globalData.url + '/faceInfo/upload';
        const uploadTask = wx.uploadFile({
            url: uploadUrl,
            filePath: _this.data.tempImagePath,
            name: 'photo',
            header: {
                'content-type': 'multipart/form-data',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            formData: {
                id: _this.data.photoId
            },
            success: function(r) {
                // 把返回的 json 数据解析出来
                var fin = JSON.parse(r.data)
                console.log("r.data: " + fin.result)
                if (fin.result === 'succeed') {
                    _this.setData({
                        masked: false
                    });
                    // 上传成功, 更新步骤条
                    _this.setData({
                        current: (_this.data.current + 1) // 2
                    });

                    // 开始识别
                    var detectUrl = app.globalData.url + '/faceInfo/detect';
                    wx.request({
                        url: detectUrl,
                        data: {
                            id: _this.data.photoId
                        },
                        header: {
                            'content-type': 'json',
                            'token': wx.getStorageSync("token"),
                            'role': wx.getStorageSync("role")
                        },
                        success: function(res) {
                            // 用户还在当前页面
                            // 识别完成返回结果, 更新步骤条“识别完成”显示结果
                            if (isHere) {
                                _this.setData({
                                    current: (_this.data.current + 1) // 3
                                });
                                if (res.data.result === "succeed") {
                                    // 更新步骤条
                                    _this.setData({
                                        current: (_this.data.current + 1),
                                        info: res.data.info
                                    });
                                    if (res.data.first) {
                                        wx.showModal({
                                            content: "该图片中人脸确定是本人吗?",
                                            showCancel: true,
                                            confirmColor: "#1aad19",
                                            success: function(res) {
                                                if (res.confirm) {
                                                    // 用户确定是本人
                                                    var decideUrl = app.globalData.url + '/faceInfo/decide';
                                                    wx.request({
                                                        url: decideUrl,
                                                        data: {
                                                            id: _this.data.photoId
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
                                                            wx.navigateTo({
                                                                url: "../detailVisual/detailVisual?id=" + _this.data.photoId + "&faceValue=" + _this.data.info.faceValue + "&age=" + _this.data.info.age + "&src=" + _this.data.tempImagePath
                                                            })
                                                        }
                                                    });
                                                } else {
                                                    // 删除不是本人人脸的图片
                                                    _this.deletePic();
                                                }
                                            }
                                        });
                                    } else {
                                        // 用户点击确定, 跳转到识别结果页面
                                        wx.navigateTo({
                                            url: "../detailVisual/detailVisual?id=" + _this.data.photoId + "&faceValue=" + _this.data.info.faceValue + "&age=" + _this.data.info.age + "&src=" + _this.data.tempImagePath
                                        });
                                    }
                                } else if (res.data.result === "succeed_but_no_save") {
                                    // 仅提示
                                    wx.showModal({
                                        content: res.data.resultMsg,
                                        showCancel: false,
                                        success: function(res) {
                                            if (res.confirm) {
                                                console.log('succeed_but_no_save, 用户点击确定')
                                            }
                                        }
                                    });
                                } else {
                                    wx.showModal({
                                        // 显示删除按钮
                                        content: res.data.resultMsg,
                                        showCancel: true,
                                        confirmText: "删除",
                                        confirmColor: "#e64340",
                                        success: function(res) {
                                            if (res.confirm) {
                                                // 用户点击确定, 删除图片
                                                _this.deletePic();
                                                console.log('用户点击删除')
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    // 图片上传失败

                }
            }
        });
        // 监听上传进度变化事件, 后续做进度条
        uploadTask.onProgressUpdate((res) => {
            _this.setData({
                progress: res.progress
            })
            console.log('上传进度', res.progress)
            console.log('已经上传的数据长度', res.totalBytesSent)
            console.log('预期需要上传的数据总长度', res.totalBytesExpectedToSend)
        })
    },

    deletePic: function() {
        var _this = this;
        wx.request({
            url: app.globalData.url + '/faceInfo/deletePic',
            data: {
                id: _this.data.photoId
            },
            header: {
                'content-type': 'json',
                'token': wx.getStorageSync("token"),
                'role': wx.getStorageSync("role")
            },
            success: function(res) {
                if (res.data.result == 'succeed') {
                    wx.showToast({
                        title: '删除成功',
                        duration: 1000,
                        mask: true
                    });
                }
                _this.setData({
                    camera: true,
                    current: 1,
                    progress: 0
                })
            }
        });
    }
})