// pages/camera/camera.js
var windowHeight = 450;
var app = getApp();
var flag = true;
Page({
  /**
   * 页面的初始数据
   */
  data: {
    src: '',
    
    isShowCamera: 'block',
    isShowPic: 'none',
    cameraHeight: '',
    pageWidth: '',
    face_value: '',
    age: '',
    emotion:'',
    stain:'',
    acne:'',
    dark_circle:'',
    health_value: '',
    // 照片识别返回值
    result: '',
    recondId: '',   // 识别结果记录在数据库表中的id

    great: "../../image/great.png",
    bad: "../../image/bad.png",
    disabled: true, // 让点赞按钮只响应一次

    isShow:false,
    show: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this;
    // 获取设备信息
    wx.getSystemInfo({
      success: function (res) {
        // 获取可使用窗口宽度并取四分之一作为显示照片的缩略图的宽度
        that.setData({
          cameraHeight: res.windowHeight,
          pageWidth: res.windowWidth,
        })
      }
    })
    flag = true;
  },


  // 拍照按钮相应事件
  takePhoto() {
    const ctx = wx.createCameraContext();

    if (flag) {
    ctx.takePhoto({
      quality: 'high',
      success: (res) => {
        this.setData({
          src: res.tempImagePath,
          isShowCamera: 'none',
          isShowPic: 'block'
        })
      
        wx.showLoading({
          title: "请稍后...",
        })
        setTimeout(function () {
          wx.hideLoading();
        }, 1000) // 延时调用 单位ms
        
        setTimeout(function () {
          if(that.data.show != '0')
            that.setData({
              show:'1'
            })
        }, 1000) // 延时调用 单位ms
        setTimeout(function () {
          if (that.data.show != '0')
          that.setData({
            show: '2'
          })
        }, 2000) // 延时调用 单位ms
        setTimeout(function () {
          if (that.data.show != '0')
          that.setData({
            show: '3'
          })
        }, 3000) // 延时调用 单位ms

        flag = false;
        var that = this;
        var nickName = app.globalData.userInfo.nickName;
        if (nickName == null) {
          nickName = "";
        }
        console.log("--> camera.js >> nickName:" + nickName);
        var id = app.globalData.openid;
        var url = app.globalData.url + '/face/detect';
        
        wx.uploadFile({
          url: url,
          filePath: this.data.src,
          name: 'pic',
          header: {
            'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
          },
          // 请求中需要的其他参数
          formData: {
            'openid': id,
          },
          success: function (res) {
            wx.hideLoading();

            // 把返回的 json 数据解析出来
            var fin = JSON.parse(res.data)
            that.setData({
              result: fin.result,
            });
            console.log(fin.result)
            if (fin.result == 'pic_not_clear' || fin.result == 'not_the_same_human' || fin.result == 'upload_at_fault') {
              // 照片中不包含人脸 或 人脸不是本人
              wx.showModal({
                content: fin.res,
                showCancel: false,
                success: function (res) {
                  if (res.confirm) {
                    console.log('用户点击确定')
                  }
                }
              });
            } else {
              // 照片中包含人脸
              if (that.data.result == 'first') {
                that.setData({
                  show:'0'
                })
                // 该微信号第一次使用小程序拍照
                wx.showModal({
                  title: '确定是微信本人吗?',
                  content: fin.res,
                  confirmText: "是",
                  cancelText: "否",
                  success: function (res) {
                    console.log(res);
                    if (res.confirm) {
                      // console.log("fin.info" + fin.info);
                      // 识别的是微信使用者本人
      //--->          // 显示结果
                      that.setData({
                        face_value: fin.info.faceValue,
                        age: fin.info.age,
                        emotion:fin.emotion,
                        stain: fin.skinstatus.stain,
                        acne:fin.skinstatus.acne,
                        dark_circle:fin.skinstatus.dark_circle,
                        // health_value:
                        isShowCamera: 'none',
                        isShowPic: 'block',
                        isShow: true,
                      });
                      var saveurl = app.globalData.url + '/faceInfo/save';
                      // 保存记录进服务器数据库
                      wx.request({
                        url: saveurl,
                        data:{
                          json: fin.info,
                          nickName:nickName
                        },
                        header: {
                          'content-type': 'json',
                          'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
                        },
                        success:function(res){
                          if (res.data.result == 'succeed') {
                            console.log('保存信息成功')
                            that.setData({
                              recondId: res.data.recondId,
                            })
                          }
                        }
                      })
                    } else {
                      // 所拍照片不是本人, 取消保存
                      console.log('用户点击辅助操作')
                      var deleteurl = app.globalData.url + '/faceInfo/deletePic';
                      wx.request({
                        url: deleteurl,
                        data: {
                          json: fin.info
                        },
                        header: {
                          'content-type': 'json',
                          'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
                        },
                        success: function (res) {
                          if (res.data.result == 'succeed') {
                            console.log('第一张不属于本人的照片成功')
                          }
                        }
                      })
                    }
                  }
                })
              } else {
                console.log("fin.face_rectangle: " + fin.face_rectangle);
                console.log("fin.emotion: " + fin.emotion);
                // 该微信号不是第一次使用小程序拍照
                console.log(fin.info.skinStatus)
                wx.request({
                  url: app.globalData.url + '/faceInfo/check',
                  data: {
                    id: fin.recondId
                  },
                  header: {
                    'content-type': 'json',
                    'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
                  },
                  success: function (res) {
                    if (res.data.result == 'succeed') {
                      console.log("id为 " + that.data.faceinfo[index].id + " 的识别结果记录已更新为已查看状态")
                    }
                  }
                })
    //--->      // 显示结果
                that.setData({
                  recondId:fin.recondId,
                  face_value: fin.info.faceValue,
                  age: fin.info.age,
                  emotion: fin.emotion,
                  stain: fin.skinstatus.stain,
                  acne: fin.skinstatus.acne,
                  dark_circle: fin.skinstatus.dark_circle,
                  // health_value:
                  isShow: true,
                });
              }
            }
          },
        })


      }
    })

    }
  },
  // -----------------takePhotos() end -----------------------//

  // 点赞响应事件
  great(e) {
    if (this.data.disabled) {
      var grade = e.currentTarget.id;
      console.log(grade)
      if (grade == 1) {
        this.setData({
          great: '../../image/great_selected.png',
          disabled: false,
        });
      } else {
        this.setData({
          bad: '../../image/bad_selected.png',
          disabled: false,
        });
      }
      var savecomment = app.globalData.url + '/comment/save';
      wx.request({
        url: savecomment,
        data: {
          photoId: this.data.recondId,
          content: '',
          satisfied: grade
        },
        header: {
          'content-type': 'json',
          'Cookie': 'JSESSIONID=' + wx.getStorageSync("sessionID")
        },
        success: function (res) {
          if (res.data.result == 'succeed') {
            console.log("点赞成功！")
          }
        }
      })
    }
  },
  // ----------------- great() end -----------------------//

  error(e) {
    console.log(e.detail)
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    this.setData({
      isShowCamera: 'block',
      isShowPic: 'none'
    })
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