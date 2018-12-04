// pages/charts/charts.js
var wxCharts = require('../../utils/wxcharts.js');
var app = getApp();
var lineChart = null;
var healthLine = null;
var startPos = null;
Page({

  /**
   * 页面的初始数据
   */
  data: {
    // faceValue: ,
    // createtime: 
  },

  touchHandler: function(e) {
    lineChart.scrollStart(e);
    healthLine.scrollStart(e);
  },
  moveHandler: function(e) {
    lineChart.scroll(e);
    healthLine.scroll(e);
  },
  touchEndHandler: function(e) {
    lineChart.scrollEnd(e);
    lineChart.showToolTip(e, {
      format: function(item, category) {
        return category + ' ' + item.name + ':' + item.data
      }
    });
    healthLine.scrollEnd(e);
    healthLine.showToolTip(e, {
      format: function (item, category) {
        return category + ' ' + item.name + ':' + item.data
      }
    });
  },
  // createSimulationData: function() {
  //   var categories = [];
  //   var data = [];

  //   // for (var i = 0; i < 10; i++) {
  //   //   categories.push('201620162-' + (i + 1));
  //   //   data.push(Math.random() * (20 - 10) + 10);
  //   // }
  //   categories.push('2018-08-12')
  //   categories.push('2018-08-13')
  //   categories.push('2018-08-19');
  //   data.push(47.14)
  //   data.push(24.72289085)
  //   data.push(54.49170303);
  //   console.log("准备return categories and data");
  //   return {
  //     categories: categories,
  //     data: data
  //   }
  // },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(e) {
    var windowWidth = 320;
    try {
      var res = wx.getSystemInfoSync();
      windowWidth = res.windowWidth;
    } catch (e) {
      console.error('getSystemInfoSync failed!');
    }

    var that = this;
    var title = [];
    var faceValues = [];
    var createTime = '';
    var faceValue = 0;
    var age = [];
    var stain = [];
    var acne = [];
    var dark_circle = [];
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
        console.log(res.data.faceinfo.length);
        var g = res.data.faceinfo;
        for (var i = 0; i < res.data.faceinfo.length; i++) {
          createTime = g[i].createtime;
          faceValue = parseFloat(g[i].faceValue);
          // console.log(createTime);
          // console.log(faceValue);
          title.push(createTime);
          faceValues.push(faceValue); // 设置颜值
          age.push(parseFloat(g[i].age)); // 设置年龄
          stain.push(g[i].skinStatus.stain);
          acne.push(g[i].skinStatus.acne);
          dark_circle.push(g[i].skinStatus.dark_circle);
        }
        console.log("信息设置完毕！")

        lineChart = new wxCharts({
          canvasId: 'lineCanvas',
          type: 'line',
          categories: title,
          animation: false,
          series: [{
            name: '颜值',
            data: faceValues,
            format: function(val, name) {
              return val.toFixed(3);
            }
          }, 
          {
            name: '年龄',
            data: age,
            format: function(val, name) {
              return val.toFixed(3);
            }
          },      
          ],
          xAxis: {
            disableGrid: false
          },
          yAxis: {
            title: '数值',
            format: function(val) {
              return val.toFixed(2);
            },
            min: 0
          },
          width: windowWidth,
          height: 200,
          dataLabel: true,
          dataPointShape: true,
          enableScroll: true,
          extra: {
            lineStyle: 'curve'
          }
        });

        healthLine = new wxCharts({
          canvasId: 'healthLine',
          type: 'line',
          categories: title,
          animation: false,
          series: [
            {
              name: '色斑系数',
              data: stain,
              format: function (val, name) {
                return val.toFixed(3);
              }
            },
            {
              name: '青春痘系数',
              data: acne,
              format: function (val, name) {
                return val.toFixed(3);
              }
            },
            {
              name: '黑眼圈系数',
              data: dark_circle,
              format: function (val, name) {
                return val.toFixed(3);
              }
            }          
          ],
          xAxis: {
            disableGrid: false
          },
          yAxis: {
            title: '系数',
            format: function (val) {
              return val.toFixed(2);
            },
            min: 0
          },
          width: windowWidth,
          height: 200,
          dataLabel: true,
          dataPointShape: true,
          enableScroll: true,
          extra: {
            lineStyle: 'curve'
          }
        });

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