package eu.ydp.empiria.player.client.module.simple.object.impl;

import com.google.gwt.dom.client.Document;


public class FlashVideoImpl implements VideoImpl{

  public String getHTML(String src){
    String  id = Document.get().createUniqueId();
    
    return  "<div id='" + id + "' class='qp-video'></div>" + 
            "<script>" + 
            " vp=new FAVideo('" + 
            id + "', '" + src + "',0,0,{autoLoad:true, autoPlay:false});" +
            "</script>";
  }
}
