page:({
  data:{
    latitude:23.577233,
    longitude:113.516233,
    markers:[{
      iconPath:'../images/navi.png',
      id:0,
      latitude:23.577233,
      longitude:113.516233,
        width:50,
        height:50
    }]
  },
  markerTap:function(){
    wx.openLocation({
      latitude: this.data.latitude,
      longitude: this.data.longitude,
      name:'广州华夏职业学院',
      address:"广东省广州市城熬打到"
    })
  }
})