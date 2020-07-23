const app = getApp()
Page({
  data:{
    isPlayingMusic:true
  },
  bgm:null,
  music_url:'http://localhost/2.mp3',
  music_coverImgurl:'http://localhost/sing.jpg',
  onReady:function(){
    this.bgm=wx.getBackgroundAudioManager()
      this.bgm.title="marry me"
      this.bgm.epname="wedding"
      this.bgm.singer="singer"
      this.bgm.coverImgurl = this.music_coverImgurl
      this.bgm.src = this.music_url
  },
  paly:function(e){
    if(this,this.data.isPlayingMusic){
      this.bgm.pause()
    }else{
      this.bgm.paly()
    }
    this.setData({
      isPlayingMusic:!this.data.isPlayingMusic
    })
  },
  callBrige:function(){
      wx.makePhoneCall({
        phoneNumber : '13435031149',
      })
  }
})