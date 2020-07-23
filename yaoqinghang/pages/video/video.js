Page({
  data:{
    src:'http://699pic.com/video-6459.html',
    danmuList:[
      {text:'第一秒出现的弹幕',color:'#ff0000',time:1},
      {text:'第三秒出现的弹幕',color:'#ff0000',time:3}
    ],
    movieList:[{
      create_time:'2018-7-5 19:21:50',title:'海边随拍',src:'http://699pic.com/video-27564.html?bindPhone=1'
    },{
      create_time:'018-7-5 19:31:50',tutle:'勿忘心安',src:'http://699pic.com/video-27560.html'
    },{
      create_time:'018-7-5 19:41:50',tutle:'点滴回忆',src:'http://699pic.com/video-6078.html'
    }]
  },
  videoContext:null,
  inputValue:'',
  onReady:function(){
    this.videoContext = wx.createVideoContext('myVideo')
  },
  bindInputBlur:function(e){
    this.inputValue=e.detail.value
  },
  bindSendDanmu:function(){
    this.videoContext.sendDanmu({
      text:this.inputValue,
      color:"#f90"
    })
  },
  bindButtonTap:function(){
    wx.chooseVideo({
      sourceType: 'camera',
      maxDuration:60,
      camera:'back',
      success:res=>{
        this.setData({
          src:res.tempFilePath
        })
      }
    })
  }
})